package fcai.sw.OrdersNotificationManagemntProject.Database;
import fcai.sw.OrdersNotificationManagemntProject.Models.Order;
import fcai.sw.OrdersNotificationManagemntProject.Models.ShippmentOrder;

import java.util.ArrayList;
public class OrderDB {

    private static ArrayList<Order> orders = new ArrayList<>();
    public OrderDB() {

    }
    //   add order to arrayList orders
    public void addOrder(Order order) {
        orders.add(order);
    }

    public Order getLastOrder(){
        return orders.get(orders.size() - 1);
    }

    //    get order with idOrder
    public Order getOrder(int orderId) {
        Order o = new Order();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId() == (orderId)) {
                o = orders.get(i);
                break;
            }
        }
        return o;
    }
//    delete order
    public void cancelOrder(Order order) {
        orders.remove(order);
    }
//   return state of shipment
    public int shipmentState(int orderId){
        int state = -1;
//        state --> -1 --> this not exist
//        state ---> 1 ---> shipped
//        state ---> 0 ----> not shipped
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId() == (orderId)) {
                Order o = orders.get(i);
                ShippmentOrder ship = o.getShipment();
                state = (ship.isShipped())?1:0;
                break;
            }
        }
        return state;
    }
    //  retrieve Orders from DB
    public ArrayList<Order> retrieveOrders() {
        return orders;
    }

    //    convert state of shipping order from false to true and add Shipping fees
    //    convert state of shipping order from true to false and add Shipping fees --> 0
    public String shipOrderChangeState(int orderId, float shippingFees, float currentTime) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId() == (orderId)) {
                Order o = orders.get(i);
                ShippmentOrder ship = o.getShipment();
                if (!ship.isShipped()) {
                    ship.setShipped(true);
                    ship.setShippingFees(shippingFees);
                    ship.setShipmentDuration(2);
                    ship.setCurrentTime(currentTime);
                    return "The order with ID " + orderId + " will be shipped in 2 days. You can cancel the shipment within a maximum of one day.";
                }
                else {
                    ship.setShipped(false);
                    ship.setShippingFees(0F);
                    ship.setShipmentDuration(0F);
                    ship.setCurrentTime(0F);
                    return "The order is placed without shipment.";
                }
            }
        }
        return "This order isn't exist.";
    }
}
// current time >> 1
// duration for shipmnet arrive >> 2 days
