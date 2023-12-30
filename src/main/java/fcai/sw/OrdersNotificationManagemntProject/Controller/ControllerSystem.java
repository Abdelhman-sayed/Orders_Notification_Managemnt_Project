package fcai.sw.OrdersNotificationManagemntProject.Controller;

import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
import fcai.sw.OrdersNotificationManagemntProject.Services.Authentication;
import fcai.sw.OrdersNotificationManagemntProject.Services.UserOptions;
import org.json.JSONException;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

@RestController
@RequestMapping("/API")
public class ControllerSystem {
    private final JsonComponentModule json;
    private UserOptions userOptions;
    private TokenGenerator tokenGenerator;
    private Authentication authentication;

    public ControllerSystem(JsonComponentModule json) {
        tokenGenerator = new TokenGenerator();
        userOptions = new UserOptions();
        authentication = new Authentication();
        this.json = json;
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
            response.setStatus(true);
            response.setMessage("Hello, " + customer.getUsername() + ". You've Logged in Successfully :)\n");
            response.setToken(token);
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
}