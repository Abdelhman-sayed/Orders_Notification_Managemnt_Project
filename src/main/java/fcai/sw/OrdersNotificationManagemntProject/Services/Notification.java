package fcai.sw.OrdersNotificationManagemntProject.Services;
import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
import fcai.sw.OrdersNotificationManagemntProject.Models.Order;

import java.util.LinkedList;
import java.util.Queue;
public class Notification {
    static int placementOrder;
    static int shippingOrder;
    Queue<MailGenerator>notifications;
//   to initialize placement and shipping with zero
    public  Notification(){
        placementOrder = 5;
        shippingOrder  = 7;
        notifications = new LinkedList<>();
    }
//    get methods
    public int getPlacementOrder(){
        return placementOrder;
    }
    public int getShippingOrder(){
        return shippingOrder;
    }
//  add mail to notify it
    public  String makeNotification(MailGenerator mailGenerator, Customer customer, Order order){
        if(mailGenerator instanceof OrderPlacementMail)
            placementOrder++;
        else
            shippingOrder++;
//        notifications.add(mailGenerator);
        return mailGenerator.generator(customer, order);
    }
//    notify method
//    public String notifyThroughMail(){
////        generate mail
//
//    }
}