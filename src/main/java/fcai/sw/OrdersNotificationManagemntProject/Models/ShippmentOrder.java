package fcai.sw.OrdersNotificationManagemntProject.Models;

import lombok.Getter;

import java.util.ArrayList;

public class ShippmentOrder {
    private boolean isShipped;
    @Getter
    private float shipmentDuration;
    @Getter
    private float currentTime;
    @Getter
    private  float shippingFees;
    public ShippmentOrder(){
        this.shippingFees = 0;
        this.isShipped = false;
        this.shipmentDuration = 2;
        this.currentTime = 0;
    }
//    setter methods

    public void setShipped(boolean shipped) {
        isShipped = shipped;
    }

    public void setShipmentDuration(float shipmentDuration) {
        this.shipmentDuration = shipmentDuration;
    }

    public void setCurrentTime(float currentTime) {
        this.currentTime = currentTime;
    }

    public void setShippingFees(float shippingFees) {
        this.shippingFees = shippingFees;
    }
//    getter methods

    public boolean isShipped() {
        return isShipped;
    }

}
