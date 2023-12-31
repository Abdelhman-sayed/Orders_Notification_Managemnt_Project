package fcai.sw.OrdersNotificationManagemntProject.Services;

import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
import fcai.sw.OrdersNotificationManagemntProject.Models.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderPlacementMail implements MailGenerator {
    @Override
    public String generator(Customer customer, Order order) {
        String allOrders = "";
        for (int i = 1;i <= order.getOrders().size();i++)
            allOrders += ("\nProduct number: " + i)+ " | Product name: "+ order.getOrders().get(i-1).getName() + "  ->  " + order.getOrders().get(i-1).getRequiredAmount();
        allOrders+="\n";
        String message = "\nDear "+ customer.getUsername() +" , your booking of the " + allOrders + " is confirmed. thanks for using our store :)";
        return message;
    }
}
