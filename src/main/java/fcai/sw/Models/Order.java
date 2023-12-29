package fcai.sw.Models;
import java.util.ArrayList;

public class Order {
    private int orderId;
    private String username;
    private  float shippingFees;
//    totalPrice for some order
    private float totalPrice;
//    isShipping
    private boolean isShipped;
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

    public void setShippingFees(float shippingFees) {
        this.shippingFees = shippingFees;
    }
    public void setTotalPrice(float totalPrice){
        this.totalPrice = totalPrice;
    }
    public void setIsShipped(boolean isShipping){
        this.isShipped = isShipping;
    }
//    getter methods

    public int getOrderId() {
        return orderId;
    }

    public String getUsername() {
        return username;
    }

    public float getShippingFees() {
        return shippingFees;
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
    public boolean getIsShipped(){
        return this.isShipped;
    }
}
