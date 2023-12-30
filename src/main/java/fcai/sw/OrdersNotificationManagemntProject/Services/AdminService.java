package fcai.sw.OrdersNotificationManagemntProject.Services;
import fcai.sw.OrdersNotificationManagemntProject.Database.CustomerDB;
import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.plaf.PanelUI;

@Service
public class AdminService {
    private CustomerDB customerDB;
    private Notification notification;

    public AdminService() {
        customerDB = new CustomerDB();
        notification = new Notification();
    }
    //    show most notified email
    public Customer mostNotifiedCustomer() {
        return customerDB.searchMostNotifiedEmail();
    }
    //    The most sent notification template
    public int mostSentNotificationTemplate() {
        int checkHowMost;
//        shipping most --> set checkHowMost with 0
//        placement most ---> set chcekHowMost with 1
//        else -1
        checkHowMost = (notification.getPlacementOrder() > notification.getShippingOrder()) ? 1
                :(notification.getPlacementOrder() < notification.getShippingOrder())? 0: -1;
        return checkHowMost;
    }
}
