package Services;
import Database.CustomerDB;
import Models.Customer;
public class Authentication {
    CustomerDB customerDB;
    public Authentication(){
        customerDB = new CustomerDB();
    }
    public void register(Customer customer){
        customerDB.addCustomer(customer);
    }
    public boolean login(Customer customer){
        return customerDB.isExist(customer);
    }
}
