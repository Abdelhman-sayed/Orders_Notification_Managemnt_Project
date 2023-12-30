package fcai.sw.OrdersNotificationManagemntProject.Services;

import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
import fcai.sw.OrdersNotificationManagemntProject.Models.Order;

public interface MailGenerator {
//    mail --> order + fees + customer
    String generator(Customer customer, Order order);
}