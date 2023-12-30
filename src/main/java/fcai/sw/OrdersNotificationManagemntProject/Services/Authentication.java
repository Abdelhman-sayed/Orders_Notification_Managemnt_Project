package fcai.sw.OrdersNotificationManagemntProject.Services;
import fcai.sw.OrdersNotificationManagemntProject.Database.CustomerDB;
import fcai.sw.OrdersNotificationManagemntProject.Models.Customer;
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

    public String dummy(Customer customer){
        return customer.getUsername() + " " +
                customer.getBalance() + " "+customer.getPassword();
    }
}
