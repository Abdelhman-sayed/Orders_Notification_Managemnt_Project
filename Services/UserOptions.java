package Services;
import Database.CustomerDB;
import Database.OrderDB;
import Database.ProductDB;
import Models.Customer;
import Models.Order;
import Models.Product;
import java.util.Map;
public class UserOptions {
    CustomerDB customerDB;
    OrderDB orderDB;
    ProductDB productDB;
    Notification notification;
    public UserOptions() {
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
//
}
