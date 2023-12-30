package fcai.sw.OrdersNotificationManagemntProject.Controller;

import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
import fcai.sw.OrdersNotificationManagemntProject.Services.Authentication;
import fcai.sw.OrdersNotificationManagemntProject.Services.CustomerService;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;

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
    public HttpServletResponse login(@RequestBody Customer customer, HttpServletResponse res) {

        if (authentication.login(customer)) {
            String token = tokenGenerator.generateToken(customer.getUsername());
            tokenGenerator.setTokenCookie(res, token);
//            response.setStatus(true)
//            response.setMessage();
//            response.setToken(token);
            return res;
        } else {
//            response.setStatus(false);
//            response.setMessage("Sorry, Invalid Cardinalities. Please Try Again.");
            return res;
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
}