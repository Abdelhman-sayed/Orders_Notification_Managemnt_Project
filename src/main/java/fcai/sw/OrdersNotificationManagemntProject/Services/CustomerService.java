package fcai.sw.OrdersNotificationManagemntProject.Services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fcai.sw.OrdersNotificationManagemntProject.Database.CustomerDB;
import fcai.sw.OrdersNotificationManagemntProject.Database.OrderDB;
import fcai.sw.OrdersNotificationManagemntProject.Database.ProductDB;
import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
import fcai.sw.OrdersNotificationManagemntProject.Models.Order;
import fcai.sw.OrdersNotificationManagemntProject.Models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
@Service
public class CustomerService {
    CustomerDB customerDB;
    OrderDB orderDB;
    ProductDB productDB;
    Notification notification;
    public CustomerService() {
        notification = new Notification();
        customerDB = new CustomerDB();
        orderDB = new OrderDB();
        productDB = new ProductDB();
    }
//    map<idProduct, quantity>
    public void makeOrder(Map<Integer, Integer> userProducts, float totalPrice, Customer customer) {
//        first --> convert map to order
        Order o = new Order();
        Product p = new Product();
        for (Map.Entry<Integer, Integer> product: userProducts.entrySet()) {
//          store serialNumber, requiredAmount in product
            p.setSerialNumber(product.getKey());
            p.setRequiredAmount(product.getValue());
//            update database of product
            productDB.update(p.getSerialNumber(),p.getRequiredAmount());
//            then store this product in arrayList of orders
            o.addProduct(p);
        }
//        add notify
        notification.addNotification(new OrderPlacementMail());
//        then notify
        notification.notify();
//       update customer balance
        customerDB.updateBalance(customer, totalPrice);
//       then ----> call add order that is exist in OrderDB
        orderDB.addOrder(o);
    }

    public void cancelOrder(Integer OrderId) {
       Order COrder = orderDB.getOrder(OrderId);
       orderDB.cancelOrder(COrder);
    }
// method to get product
    public String getProductsFromDB() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Convert the ArrayList to String JSON
        String productsJson = objectMapper.writeValueAsString(productDB.getProducts());
        return productsJson;
    }
}
