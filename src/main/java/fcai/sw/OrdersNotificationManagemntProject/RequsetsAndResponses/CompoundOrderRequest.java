package fcai.sw.OrdersNotificationManagemntProject.RequsetsAndResponses;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CompoundOrderRequest {
    private Map<Integer, OrderRequest> compoundOrder;

    public CompoundOrderRequest(Map<Integer, OrderRequest> com){this.compoundOrder=com;}

    public void setCompoundOrder(Map<Integer, OrderRequest> compoundOrder) {
        this.compoundOrder = compoundOrder;
    }
    public CompoundOrderRequest(){
        this.compoundOrder = new HashMap<>();
    }
}