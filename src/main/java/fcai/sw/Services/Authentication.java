package fcai.sw.Services;
import fcai.sw.Database.CustomerDB;
import fcai.sw.Models.Customer;
import org.springframework.stereotype.Service;

@Service
public class Authentication {
    CustomerDB customerDB;
    public Authentication(){
        customerDB = new CustomerDB();
    }
    public boolean isUniqueCustomer(Customer customer){
        return customerDB.isUnique(customer);
    }
    public String register(Customer customer){
        return customerDB.addCustomer(customer);
    }
    public boolean login(Customer customer){
        return customerDB.isExist(customer);
    }
}
