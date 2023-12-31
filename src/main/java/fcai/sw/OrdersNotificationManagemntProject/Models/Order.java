package fcai.sw.OrdersNotificationManagemntProject.Models;
import lombok.Getter;

import java.util.ArrayList;

public class Order {
    @Getter
    private int orderId;
    @Getter
    private String username;
    @Getter
    private ShippmentOrder shipment;
//    totalPrice for some order
    @Getter
    private float totalPrice;
//    isShipping
    private ArrayList<Product> order;
    public Order(){
        order = new ArrayList<>();
    }
//    setter methods
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTotalPrice(float totalPrice){
        this.totalPrice = totalPrice;
    }
//    getter methods
    public void setShipment(ShippmentOrder shipment){this.shipment = shipment;}


    public void addProduct(Product p){
        order.add(p);
    }
    public ArrayList<Product> getOrders(){
        return order;
    }
}
