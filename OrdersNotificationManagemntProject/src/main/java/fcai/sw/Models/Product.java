package fcai.sw.Models;
public class Product {
    private int serialNumber;
    private String name;
    private String vendor;
    private String category;
    private float price;
    private int availableQuantity;
    private int requiredAmount;

//    setter methods

    public void setName(String name) {
        this.name = name;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public void setRequiredAmount(int amount) {
        this.requiredAmount = amount;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

//    getter methods
    public int getSerialNumber() {
        return serialNumber;
    }

    public String getName() {
        return name;
    }

    public String getVendor() {
        return vendor;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }
    public int getRequiredAmount(){
        return this.requiredAmount;
    }
}
