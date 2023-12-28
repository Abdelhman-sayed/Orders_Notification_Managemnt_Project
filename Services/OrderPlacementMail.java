package Services;

import Models.Customer;
import Models.Order;

public class OrderPlacementMail implements MailGenerator {
    @Override
    public String generator(Customer customer, Order order) {
        String allOrders = "";
        for (int i = 1;i <= order.getOrders().size();i++)
            allOrders += ("" + i) + order.getOrders().get(i-1).getName() + " -> " + order.getOrders().get(i-1).getRequiredAmount();
        String message = "Dear "+ customer.getUsername() +" , your booking of the " + allOrders + " is confirmed. thanks for using our store :)";
        return message;
    }
}
