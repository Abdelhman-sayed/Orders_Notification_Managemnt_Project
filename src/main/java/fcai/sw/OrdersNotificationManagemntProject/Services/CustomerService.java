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
//    ability to make order | shipment
    public boolean abilityOfMoney(Customer customer, float totalPrice){
        return (customerDB.getCustomerByUsername(customer.getUsername()).getBalance() >= totalPrice);
    }
    public Order convertJsonToOrder(Map<Integer, Integer> userProducts, String username){
        //  first --> convert map to order
        Order o = new Order();
        ShippmentOrder ship = new ShippmentOrder();
        ship.setShipped(false);
        o.setShipment(ship);
        float totalPrice = 0;
        for (Map.Entry<Integer, Integer> product: userProducts.entrySet()) {
//          store serialNumber, requiredAmount in product
            Product p = productDB.getProductBySN(product.getKey());
//            add amount of product
            p.setRequiredAmount(product.getValue());
            totalPrice+=(product.getValue() * p.getPrice());
//            then store this product in arrayList of orders
            o.addProduct(p);
        }
        o.setTotalPrice(totalPrice);
        o.setUsername(username);
        o.setOrderId(orderDB.retrieveOrders().size()+1);
        o.setShipment(new ShippmentOrder());
        return o;
    }
    public String makeOrder(Order orderR, Customer customer) {
//    map<idProduct, quantity>
        Order order = new Order();
//        this function to convert map to order object
//        make steps after take order
        order = orderR;
        //  update database of product
        for (Product product : order.getOrders())
            productDB.update(product.getSerialNumber(),product.getRequiredAmount());
//        update numNotifiedInEmail in customer DB
        customerDB.updateNumNotifiedInEmail(customer.getUsername());
//        create generator based on type
//        add notify
        notification.makeNotification(new OrderPlacementMail());
//        then notify
        String messageThroughEmail = notification.notifyThroughMail(customer, order);
//       update customer balance
        customerDB.updateBalance(customer.getUsername(), order.getTotalPrice());
//       then ----> call add order that is existed in OrderDB
        orderDB.addOrder(order);
        return messageThroughEmail;
    }
    public String cancelOrder(Integer OrderId)
    {
       Order CancelOrder = orderDB.getOrder(OrderId);
       customerDB.updateBalance(CancelOrder.getUsername(),-CancelOrder.getTotalPrice());
       for(Product product: CancelOrder.getOrders()){
            productDB.update(product.getSerialNumber(),-product.getRequiredAmount());
       }
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
    private String shipmentOrder(Order o, Customer customer){
        String messageNotifiy = "", message = "";
        customerDB.updateBalance(o.getUsername(), o.getShipment().getShippingFees());
        MailGenerator typeShipment = new OrderShipmentMail();
        notification.makeNotification(typeShipment);
    //                increment numNotifiedEmail in customer DB with one
        customerDB.updateNumNotifiedInEmail(customer.getUsername());
        messageNotifiy = notification.notifyThroughMail(customer, o);
        //0.1F + random.nextFloat() * (2F - 0.1F)
        message = orderDB.shipOrderChangeState(o.getOrderId(), o.getShipment().getShippingFees(), 0.5F);
        return message + messageNotifiy;
    }
//    this method has two cases cancel or make shipment.
    public String changeStateShippingOrder(int orderId)
    {
        float shippingFees = 12.0F;
        String messageNotifiy = "", message = "";
        Random random = new Random();
        Order o = orderDB.getOrder(orderId);
        o.getShipment().setShippingFees(shippingFees);
        Customer customer = customerDB.getCustomerByUsername(o.getUsername());
//        if it's being shipped then reduce balance
        if(orderDB.shipmentState(orderId) == 0)
        {
            o.getShipment().setShipped(true);
            if (customer.getBalance() >= o.getShipment().getShippingFees()) {
             return shipmentOrder(o, customer);
            } else
                return "Your balance is less than the Shipping Fees";
        }
        // cancel shipping
        else {
            o.getShipment().setShipped(false);
            // return the fees back (increase balance)
            if (orderDB.getOrder(orderId).getShipment().getCurrentTime() < ((orderDB.getOrder(orderId).getShipment().getShipmentDuration())/2)) {
                customerDB.updateBalance(o.getUsername(), -o.getShipment().getShippingFees());
                message = orderDB.shipOrderChangeState(orderId, shippingFees, 0.5F);//0.1F + random.nextFloat() * (2F - 0.1F)
            }
            else
                return "You can't cancel the order as you can cancel the shipment within a maximum of one day.";
        }
        return message + messageNotifiy;
    }
//    return state to check --> possibility of make this service
    public int showShipmentState(int orderId)
    {
        return orderDB.shipmentState(orderId);
    }
    public Order getLastOrder(){
        return orderDB.getLastOrder();
    }
}
