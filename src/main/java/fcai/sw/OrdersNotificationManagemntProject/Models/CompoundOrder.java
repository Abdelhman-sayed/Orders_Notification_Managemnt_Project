package fcai.sw.OrdersNotificationManagemntProject.Models;
import java.util.ArrayList;
public class CompoundOrder {
    private int compoundOrderId;
    private ArrayList<Order>compoundOrder;

    public void setCompoundOrderId(int compoundOrderId) {
        this.compoundOrderId = compoundOrderId;
    }

    public void setCompoundOrder(ArrayList<Order> compoundOrder) {
        this.compoundOrder = compoundOrder;
    }

//    getter methods

    public int getCompoundOrderId() {
        return compoundOrderId;
    }

    public ArrayList<Order> getCompoundOrder() {
        return compoundOrder;
    }
}
//arrayList<CompoundOrder>