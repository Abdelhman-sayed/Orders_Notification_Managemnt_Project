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
import java.util.Random;
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
    public void makeOrder(Map<Integer, Integer> userProducts, Customer customer) {
//        first --> convert map to order
        Order o = new Order();
        float totalPrice = 0;
        for (Map.Entry<Integer, Integer> product: userProducts.entrySet()) {
//          store serialNumber, requiredAmount in product
            Product p = new Product();
            p.setSerialNumber(product.getKey());
            p.setRequiredAmount(product.getValue());
//            calculate price
            float price = productDB.getProductBySN(product.getKey()).getPrice();
            totalPrice += (product.getValue() * price);
//            update database of product
            productDB.update(p.getSerialNumber(),p.getRequiredAmount());
//            then store this product in arrayList of orders
            o.addProduct(p);
        }
        o.setTotalPrice(totalPrice);
        o.setUsername(customer.getUsername());
        o.getShipment().setShipped(false);
        o.getShipment().setShippingFees(0);
        o.setOrderId(orderDB.retrieveOrders().size()+1);
//        add notify
        notification.addNotification(new OrderPlacementMail());
//        then notify
        notification.notifyThroughMail(customer, o);
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
//   shipping order
    public String makeShippingOrder(int orderId){
        float shippingFees = 12.0F;
        Random random = new Random();
        return orderDB.shipOrderChangeState(orderId, shippingFees, 0.5F + random.nextFloat() * (4.5F - 0.5F));
    }
//    return state to check --> possibility of make this service
    public int showShipmentState(int orderId){
        return orderDB.shipmentState(orderId);
    }
}
