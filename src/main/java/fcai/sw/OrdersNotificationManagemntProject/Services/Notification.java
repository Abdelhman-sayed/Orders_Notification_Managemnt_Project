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
    public  Notification()
    {
        placementOrder = 0;
        shippingOrder  = 0;
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
    public  void makeNotification(MailGenerator mailGenerator){
        if(mailGenerator instanceof OrderPlacementMail)
            placementOrder++;
        else
            shippingOrder++;
        notifications.add(mailGenerator);
    }
//    notify method
    public String notifyThroughMail(Customer customer, Order order){
        return notifications.poll().generator(customer, order);
    }
}