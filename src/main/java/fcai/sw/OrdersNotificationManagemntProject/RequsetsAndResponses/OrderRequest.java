package fcai.sw.OrdersNotificationManagemntProject.RequsetsAndResponses;

import java.util.Map;
import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
import lombok.Getter;

@Getter
public class OrderRequest {
    private Customer customer;
    private Map<Integer, Integer> userProducts;
}

