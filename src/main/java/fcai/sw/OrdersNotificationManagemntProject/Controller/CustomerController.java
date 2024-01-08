package fcai.sw.OrdersNotificationManagemntProject.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import fcai.sw.OrdersNotificationManagemntProject.Models.Order;
import fcai.sw.OrdersNotificationManagemntProject.Services.Authentication;
import fcai.sw.OrdersNotificationManagemntProject.Services.CustomerService;
import org.springframework.web.bind.annotation.*;
import fcai.sw.OrdersNotificationManagemntProject.RequsetsAndResponses.OrderRequest;

@RestController
@RequestMapping("/API")
public class CustomerController {
    private CustomerService customerService;
    private Authentication authentication;
    public CustomerController() {
        customerService = new CustomerService();
        authentication = new Authentication();
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
        return customerService.changeStateShippingOrder(order.getOrderId());
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
        return customerService.changeStateShippingOrder(order.getOrderId());
    }
//    cancel order ---> take order id as a parameter
    @PostMapping("/CancelOrder")
    public String cancelOrder(@RequestBody Order order){
        int state = customerService.showShipmentState(order.getOrderId());
        if(state == -1)
            return "This order id not exist.";
//      shipped or no --> return shippingFees to customer
        if(state == 1)
            customerService.changeStateShippingOrder(order.getOrderId());
//        return price OF this order to customer  and remove order
        return customerService.cancelOrder(order.getOrderId());
    }
}