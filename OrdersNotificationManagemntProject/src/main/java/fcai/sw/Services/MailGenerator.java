package fcai.sw.Services;

import fcai.sw.Models.Customer;
import fcai.sw.Models.Order;

public interface MailGenerator {
//    mail --> order + fees + customer
    String generator(Customer customer, Order order);
}