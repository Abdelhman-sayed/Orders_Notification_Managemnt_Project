package fcai.sw.OrdersNotificationManagemntProject.Database;

import fcai.sw.OrdersNotificationManagemntProject.Models.CompoundOrder;
import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;

import java.util.ArrayList;

public class CompoundOrderDB {
    private static ArrayList<CompoundOrder> compoundOrdersDB = new ArrayList<>();
//    add
    public void addCompoundOrder(CompoundOrder compoundOrder){
        compoundOrdersDB.add(compoundOrder);
    }
//    get compoundOrder
    public ArrayList<CompoundOrder> getCompoundOrders(){
        return compoundOrdersDB;
    }
    public int getSize(){if (compoundOrdersDB.isEmpty()) return 0; return compoundOrdersDB.size();}
}
