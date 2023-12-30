package fcai.sw.OrdersNotificationManagemntProject.Database;
import fcai.sw.OrdersNotificationManagemntProject.Models.Product;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Random;

public class ProductDB {
    // get product from database to show it for user
    @Getter
    private static ArrayList<Product> products = new ArrayList<>();
    public ProductDB()
    {
        if(products.isEmpty()){
            this.setData();
        }
    }
    public void setData() {
        String[] PRODUCT_NAMES = {"Product1", "Product2", "Product3", "Product4", "Product5", "Product6", "Product7", "Product8", "Product9", "Product10"};
        String[] VENDORS = {"Vendor1", "Vendor2", "Vendor3", "Vendor4", "Vendor5", "Vendor6", "Vendor7", "Vendor8", "Vendor9", "Vendor10"};
        String[] CATEGORIES = {"Category1", "Category2", "Category3", "Category4", "Category5", "Category6", "Category7", "Category8", "Category9", "Category10"};
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setSerialNumber(i + 1); // Assuming serial numbers start from 1
            product.setName(PRODUCT_NAMES[i]);
            product.setVendor(VENDORS[i]);
            product.setCategory(CATEGORIES[i]);
            product.setPrice(random.nextFloat() * 100); // Random float price between 0 and 100
            product.setAvailableQuantity(random.nextInt(100)); // Random integer available quantity between 0 and 99
            products.add(product);
        }
    }

    public Product getProductBySN(int serialNumber){
        for (int i = 0; i < products.size(); i++){
            if (serialNumber == products.get(i).getSerialNumber()) {
                return products.get(i);
            }
        }
        return null;
    }
    public boolean checkAvailability(Product product){
        for (int i = 0; i < products.size(); i++){
            if (product.getName().equals(products.get(i).getName())
                    && products.get(i).getAvailableQuantity() > 0) {
                return true;
            }
        }
        return false;
    }
//    to update available quantity
    public void update(int serialNum, int quantity){
        for (int i = 0; i <  products.size(); i++){
            if(serialNum == products.get(i).getSerialNumber()) {
                products.get(i).setAvailableQuantity(products.get(i).getAvailableQuantity() - quantity);
                break;
            }
        }
    }
}
