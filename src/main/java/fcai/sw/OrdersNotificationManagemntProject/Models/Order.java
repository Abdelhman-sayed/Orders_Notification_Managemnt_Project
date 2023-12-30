package fcai.sw.OrdersNotificationManagemntProject.Models;
import java.util.ArrayList;

public class Order {
    private int orderId;
    private String username;
    private ShippmentOrder shipment;
//    totalPrice for some order
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
    public ShippmentOrder getShipment(){return this.shipment;}
    public int getOrderId() {
        return orderId;
    }

    public String getUsername() {
        return username;
    }


    public void addProduct(Product p){
        order.add(p);
    }
    public ArrayList<Product> getOrders(){
        return order;
    }
    public float getTotalPrice(){
        return this.totalPrice;
    }
}
