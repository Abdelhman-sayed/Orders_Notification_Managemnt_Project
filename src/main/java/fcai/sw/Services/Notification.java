package fcai.sw.Services;
import fcai.sw.Models.Customer;
import fcai.sw.Models.Order;

import java.util.LinkedList;
import java.util.Queue;
public class Notification {
    static int placementOrder;
    static int shippingOrder;
    Queue<MailGenerator>notifications;
//   to initialize placement and shipping with zero
    public  Notification(){
        placementOrder = 0;
        shippingOrder  = 0;
        notifications = new LinkedList<>();
    }
//  add mail to notify it
    public  void addNotification(MailGenerator mailGenerator){
        if(mailGenerator instanceof OrderPlacementMail)
            placementOrder++;
        else
            shippingOrder++;
        notifications.add(mailGenerator);
    }
//    notify method
    public String notifyThroughMail(Customer customer, Order order){
//        generate mail
        return notifications.poll().generator(customer, order);
    }
}