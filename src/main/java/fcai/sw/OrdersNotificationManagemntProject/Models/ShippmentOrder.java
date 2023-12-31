package fcai.sw.OrdersNotificationManagemntProject.Models;

import java.util.ArrayList;

public class ShippmentOrder {
    private boolean isShipped;
    private float shipmentDuration;
    private float currentTime;
    private  float shippingFees;
    public ShippmentOrder(){
        this.shippingFees = 0;
        this.isShipped = false;
        this.shipmentDuration = 0;
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

    public float getShipmentDuration() {
        return shipmentDuration;
    }

    public float getCurrentTime() {
        return currentTime;
    }

    public float getShippingFees() {
        return this.shippingFees;
    }
}
