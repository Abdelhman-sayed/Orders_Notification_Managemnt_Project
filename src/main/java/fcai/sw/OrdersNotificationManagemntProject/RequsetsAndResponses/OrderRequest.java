package fcai.sw.OrdersNotificationManagemntProject.RequsetsAndResponses;

import java.util.Map;
import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
import lombok.Getter;

public class OrderRequest {
    @Getter
    private Customer customer;
    private Map<Integer, Integer> userProducts;

    public Map<Integer, Integer> getUserProducts() {
        return userProducts;
    }
}

