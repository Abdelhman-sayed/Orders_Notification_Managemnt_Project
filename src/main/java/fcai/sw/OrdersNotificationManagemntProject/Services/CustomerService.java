package fcai.sw.OrdersNotificationManagemntProject.Services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fcai.sw.OrdersNotificationManagemntProject.Database.CustomerDB;
import fcai.sw.OrdersNotificationManagemntProject.Database.OrderDB;
import fcai.sw.OrdersNotificationManagemntProject.Database.ProductDB;
import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
import fcai.sw.OrdersNotificationManagemntProject.Models.Order;
import fcai.sw.OrdersNotificationManagemntProject.Models.Product;
import fcai.sw.OrdersNotificationManagemntProject.Models.ShippmentOrder;
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
    public String makeOrder(Map<Integer, Integer> userProducts, Customer customer) {
//        first --> convert map to order
        Order o = new Order();
        ShippmentOrder ship = new ShippmentOrder();
        o.setShipment(ship);
        float totalPrice = 0;
        for (Map.Entry<Integer, Integer> product: userProducts.entrySet()) {
//          store serialNumber, requiredAmount in product
            Product p = new Product();
            p.setSerialNumber(product.getKey());
            p.setRequiredAmount(product.getValue());
            p.setName(productDB.getProductBySN(product.getKey()).getName());
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
        o.setOrderId(orderDB.retrieveOrders().size()+1);
//        add notify
        MailGenerator typePlacement = new OrderPlacementMail();
//        then notify
        String messageThroughEmail = notification.makeNotification(typePlacement, customer, o);
//       update customer balance
        customerDB.updateBalance(customer.getUsername(), totalPrice);
        System.out.println(totalPrice);
        System.out.println(customer.getUsername());
        //       then ----> call add order that is exist in OrderDB
        orderDB.addOrder(o);
        return messageThroughEmail;
    }
    public String cancelOrder(Integer OrderId) {
       Order CancelOrder = orderDB.getOrder(OrderId);
       customerDB.updateBalance(CancelOrder.getUsername(),-CancelOrder.getTotalPrice());
       orderDB.cancelOrder(CancelOrder);
       return "order cancelled successfully\n";
    }
// method to get product
    public String getProductsFromDB() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Convert the ArrayList to String JSON
        String productsJson = objectMapper.writeValueAsString(productDB.getProducts());
        return productsJson;
    }
//   shipping order
    public String makeShippingOrder(int orderId)
    {
        float shippingFees = 12.0F;
        Random random = new Random();
        Order o = orderDB.getOrder(orderId);
//        if it's being shipped then reduce balance
        if(orderDB.shipmentState(orderId) == 0)
            if(customerDB.getCustomerByUsername(o.getUsername()).getBalance()>= o.getShipment().getShippingFees())
                customerDB.updateBalance(o.getUsername(), o.getShipment().getShippingFees());
            else
                return "Your balance is less than the Shipping Fees";
        // cancel shipping
        else {
            // return the fees back (increase balance)
            if (orderDB.getOrder(orderId).getShipment().getCurrentTime()<((orderDB.getOrder(orderId).getShipment().getShipmentDuration())/2))
                customerDB.updateBalance(o.getUsername(), -o.getShipment().getShippingFees());
            else
                return "You can't cancel the order as you can cancel the shipment within a maximum of one day.";
        }
        return orderDB.shipOrderChangeState(orderId, shippingFees, 0.5F + random.nextFloat() * (4.5F - 0.5F));
    }
//    return state to check --> possibility of make this service
    public int showShipmentState(int orderId){
        return orderDB.shipmentState(orderId);
    }
}
