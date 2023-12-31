package fcai.sw.OrdersNotificationManagemntProject.Services;

import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
import fcai.sw.OrdersNotificationManagemntProject.Models.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderShipmentMail implements MailGenerator {

    @Override
    public String generator(Customer customer, Order order) {
        String allOrders = "";
        for (int i = 1;i <= order.getOrders().size();i++)
            allOrders += ("\nProduct number: " + i)+ " | Product name: "+ order.getOrders().get(i-1).getName() + "  ->  " + order.getOrders().get(i-1).getRequiredAmount();
        allOrders+="\n";
        String message = "Dear "+ customer.getUsername() +" , your booking of the " + allOrders + " is confirmed. And Shipping fees is " + order.getShipment().getShippingFees() + "\n** Thanks for using our store :)";
        return message;
    }
}


