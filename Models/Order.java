package Models;

import java.util.ArrayList;

public class Order {
    private int orderId;
    private String username;
    private  float shippingFees;
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
}
// pal counterPla++
// ship counterShipping++