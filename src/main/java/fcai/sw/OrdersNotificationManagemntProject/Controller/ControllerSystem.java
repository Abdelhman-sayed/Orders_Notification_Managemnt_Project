package fcai.sw.OrdersNotificationManagemntProject.Controller;

import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
import fcai.sw.OrdersNotificationManagemntProject.Services.Authentication;
import fcai.sw.OrdersNotificationManagemntProject.Services.CustomerService;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;
import java.util.Map;

@RestController
@RequestMapping("/API")
public class ControllerSystem {
    private CustomerService customerService;
    private TokenGenerator tokenGenerator;
    private Authentication authentication;

    public ControllerSystem() {
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

    @PostMapping("/makeOrder")
    // ***DUMMY IMP***
    public String makeOrder(@RequestBody String token) throws JSONException {
        JSONObject jsonObject = new JSONObject(token);
        String key = jsonObject.getString("token");
        String username = tokenGenerator.extractUsername(key);
        if (username != null) {
            return "Order placed successfully!";
        } else {
            return "Unauthorized access. Please provide a valid token.";
        }
    }

    @GetMapping("/someEndpoint")
    public String someEndpoint(@CookieValue(name = "token", required = false) String jwtToken) {
        if (jwtToken != null) {
            // Token is present in the cookie
            return "JWT Token: " + jwtToken;
        } else {
            // Token is not present in the cookie
            return "No JWT Token found in the cookie";
        }
    }
    @PostMapping("/makeOrder12")
    public void makeOrder(@RequestBody Map<Integer, Integer> userProducts, @RequestBody Customer customer) {

    }

}