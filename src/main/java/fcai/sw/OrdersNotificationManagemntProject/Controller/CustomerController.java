package fcai.sw.OrdersNotificationManagemntProject.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import fcai.sw.OrdersNotificationManagemntProject.Database.ProductDB;
import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
import fcai.sw.OrdersNotificationManagemntProject.Models.Order;
import fcai.sw.OrdersNotificationManagemntProject.Models.Product;
import fcai.sw.OrdersNotificationManagemntProject.RequsetsAndResponses.CompoundOrder;
import fcai.sw.OrdersNotificationManagemntProject.RequsetsAndResponses.Response;
import fcai.sw.OrdersNotificationManagemntProject.RequsetsAndResponses.TokenGenerator;
import fcai.sw.OrdersNotificationManagemntProject.Services.Authentication;
import fcai.sw.OrdersNotificationManagemntProject.Services.CustomerService;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;
import fcai.sw.OrdersNotificationManagemntProject.RequsetsAndResponses.OrderRequest;

import java.util.ArrayList;
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

        if (!authentication.isUniqueCustomer(customer.getUsername())) {
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
    @PostMapping("/makeOrder")
    public String makeOrder(@CookieValue(name = "jwtToken", required = false) String jwtToken, @RequestBody OrderRequest orderRequest){
        String username = orderRequest.getCustomer().getUsername();
        if (username == null)
          return "Unauthorized access. Please provide a valid token.";
//        System.out.println("username : " + username);
//      return true --> if customer is unique
//      return false --> if is exist
        if(authentication.isUniqueCustomer(username))
            return "This user is not Exist";
        String Message = customerService.makeOrder(orderRequest.getUserProducts(), orderRequest.getCustomer());
        return "Order Placed Successfully" + Message;
    }


//   make compound order
@PostMapping("/makeCompoundOrder")
    public String makeOrder(@CookieValue(name = "jwtToken", required = false) String jwtToken, @RequestBody CompoundOrder orderRequests)
    {
        for (OrderRequest requestBody: orderRequests.getCompoundOrder()) {
            System.out.println(requestBody.getCustomer() + "\n");
            for (Map.Entry<Integer, Integer> product: requestBody.getUserProducts().entrySet()) 
            {
                System.out.println(product.getKey() + " " + product.getValue());
            }
            System.out.println();
        }
        return null;
    }
    @GetMapping("/ShowProducts")
    public String showProducts(){
        try {
            return customerService.getProductsFromDB();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping ("/ShippingOrder")
    public String shipOrder(@RequestBody Order order){
        int state = customerService.showShipmentState(order.getOrderId());
        if(state == -1)
            return "This order id not exist.";
//        we must check if this order we can make ship or no
//        if it is shipped already so we can not make shipment again
        if(state == 1)
            return "This order is already shipped.";
        return customerService.makeShippingOrder(order.getOrderId());
    }
    @PostMapping ("/CancelShippingOrder")
    public String cancelShipOrder(@RequestBody Order order){
        int state = customerService.showShipmentState(order.getOrderId());
        if(state == -1)
            return "This order id not exist.";
//        we must check if this order we can cancel ship of it or no
//        if it is canceled or not shipped before so we can not cancel shipment
        if(state == 0)
            return "This order is canceled or not shipped before.";
        return customerService.makeShippingOrder(order.getOrderId());
    }
//    cancel order ---> take order id as a parameter
    @PostMapping("/CancelOrder")
    public String cancelOrder(@RequestBody Order order){
        int state = customerService.showShipmentState(order.getOrderId());
        if(state == -1)
            return "This order id not exist.";
//      shipped or no --> return shippingFees to customer
        if(state == 1)
            customerService.makeShippingOrder(order.getOrderId());
//        return price OF this order to customer  and remove order
        return customerService.cancelOrder(order.getOrderId());
    }
}