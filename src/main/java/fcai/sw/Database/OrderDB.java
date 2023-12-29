package fcai.sw.Database;
import fcai.sw.Models.Order;
import java.util.ArrayList;
public class OrderDB {
    private ArrayList<Order>orders;
    public OrderDB(){
        orders = new ArrayList<>();
    }
//   add order to arrayList orders
    public void addOrder(Order order){
        orders.add(order);
    }
//    get order with idOrder
    public Order getOrder(int orderId){
        Order o = new Order();
        for(int i = 0;i < orders.size();i++){
            if(orders.get(i).getOrderId() == (orderId)){
                o = orders.get(i);
                break;
            }
        }
        return o;
    }
    public void cancelOrder(Order order){
        orders.remove(order);
    }
}
