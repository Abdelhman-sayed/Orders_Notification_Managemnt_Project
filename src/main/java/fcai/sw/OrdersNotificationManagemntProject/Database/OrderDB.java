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

    public void cancelOrder(Order order) {
        orders.remove(order);
    }

    //    retrive Orders from DB
    public ArrayList<Order> retrieveOrders() {
        return orders;
    }

    //    convert state of shipping order from false to true and add Shippingfees
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
                } else {
                    ship.setShipped(false);
                    ship.setShippingFees(0);
                    ship.setShipmentDuration(0);
                    ship.setCurrentTime(0);
                    return "The order is placed.";
                }
            }
        }
        return "This order isn't exist.";
    }
}
// current time >> 1
// duration for shipmnet arrive >> 2 days
