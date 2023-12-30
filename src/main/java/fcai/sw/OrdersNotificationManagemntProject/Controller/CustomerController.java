package fcai.sw.OrdersNotificationManagemntProject.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import fcai.sw.OrdersNotificationManagemntProject.Database.ProductDB;
import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
import fcai.sw.OrdersNotificationManagemntProject.Models.Product;
import fcai.sw.OrdersNotificationManagemntProject.RequsetsAndResponses.Response;
import fcai.sw.OrdersNotificationManagemntProject.RequsetsAndResponses.TokenGenerator;
import fcai.sw.OrdersNotificationManagemntProject.Services.Authentication;
import fcai.sw.OrdersNotificationManagemntProject.Services.CustomerService;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;
import fcai.sw.OrdersNotificationManagemntProject.RequsetsAndResponses.OrderRequest;

import java.util.Map;

@RestController
@RequestMapping("/API")
public class CustomerController {
    private CustomerService customerService;
    private TokenGenerator tokenGenerator;
    private Authentication authentication;

    public CustomerController() {
        tokenGenerator = new TokenGenerator();
        customerService = new CustomerService();
        authentication = new Authentication();
    }

    //  register customer
    @PostMapping("/register")
    public Response register(@RequestBody Customer customer) {
        Response response = new Response();

        if (!authentication.isUniqueCustomer(customer)) {
            response.setStatus(false);
            response.setMessage("This username is used before.");
            return response;
        }
        response.setStatus(true);
        response.setMessage(authentication.register(customer));
        return response;
    }

    @PostMapping("/login")
    public Response login(@RequestBody Customer customer) {
        Response response = new Response();
        if (authentication.login(customer)) {
            String token = tokenGenerator.generateToken(customer.getUsername());
            tokenGenerator.setTokenCookie(token);
            response.setStatus(true);
            response.setMessage("Hello, " + customer.getUsername() + ". You've logged in successfully :) (Your Token Saved in your Cookies)");
            return response;
        } else {
            response.setStatus(false);
            response.setMessage("Sorry, Invalid Cardinalities. Please Try Again.");
            return response;
        }
    }

//    @PostMapping("/makeOrder")
//    // ***DUMMY IMP***
//    public String makeOrder(@RequestBody String token) throws JSONException {
//        JSONObject jsonObject = new JSONObject(token);
//        String key = jsonObject.getString("token");
//        String username = tokenGenerator.extractUsername(key);
    //        if (username != null) {
    //            return "Order placed successfully!";
    //        } else {
    //            return "Unauthorized access. Please provide a valid token.";
    //        }
//    }

    @PostMapping("/makeOrder")
    public String makeOrder(@CookieValue(name = "jwtToken", required = false) String jwtToken, @RequestBody OrderRequest orderRequest){
        String username = tokenGenerator.extractUsername(jwtToken);

      if (username == null) {
          return "Unauthorized access. Please provide a valid token.";
      }
       ProductDB productDB = new ProductDB();
        float totalPrice = 0;

        for (Map.Entry<Integer,Integer> entry : orderRequest.getUserProducts().entrySet()){
            Product p = productDB.getProductBySN(entry.getKey());
            totalPrice += p.getPrice();
        }

        customerService.makeOrder(orderRequest.getUserProducts(), totalPrice, orderRequest.getCustomer());
        return "Order Placed Successfully";
    }

    @GetMapping("/ShowProducts")
    public String showProducts(){
        try {
            return customerService.getProductsFromDB();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}