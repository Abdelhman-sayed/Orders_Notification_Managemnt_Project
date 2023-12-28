package Services;

import Models.Customer;
import Models.Order;

public interface MailGenerator {
//    mail --> order + fees + customer
    String generator(Customer customer, Order order);
}