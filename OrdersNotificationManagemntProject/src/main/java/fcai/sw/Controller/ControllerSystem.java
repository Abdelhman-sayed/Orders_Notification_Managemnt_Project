package fcai.sw.Controller;

import fcai.sw.Models.Customer;
import fcai.sw.Services.Authentication;
import fcai.sw.Services.UserOptions;
import jakarta.annotation.PostConstruct;
import org.apache.catalina.webresources.ClasspathURLStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/API")
public class ControllerSystem {
    private UserOptions userOptions;
    private Authentication authentication;
    public ControllerSystem(){
        userOptions = new UserOptions();
        authentication = new Authentication();
    }
//  register customer
    @PostMapping  ("/register")
    public String register(@RequestBody Customer customer){
        if(authentication.isUniqueCustomer(customer))
            return "This username is used before.";
        return authentication.register(customer);
    }
}
