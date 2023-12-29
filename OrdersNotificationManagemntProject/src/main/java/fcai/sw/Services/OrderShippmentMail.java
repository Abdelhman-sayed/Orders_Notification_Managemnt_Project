package fcai.sw.Services;

import fcai.sw.Models.Customer;
import fcai.sw.Models.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderShippmentMail implements MailGenerator {

    @Override
    public String generator(Customer customer, Order order) {
        String allOrders = "";
        for (int i = 1;i <= order.getOrders().size();i++)
            allOrders += ("" + i) + order.getOrders().get(i-1).getName() + " -> " + order.getOrders().get(i-1).getRequiredAmount();
        String message = "Dear "+ customer.getUsername() +" , your booking of the " + allOrders + " is confirmed. And Shipping fees is " + order.getShippingFees() + " thanks for using our store :)";
        return message;
    }
}


