package Models;
public class Customer {
    private String username;
    private String password;
    private String email;
    private String location;
    private float balance;
//    for point no 10 in requirement
    private int numNotifiedInEmail;

//    setter methods

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setNumNotifiedInEmail(int numNotifiedInEmail) {
        this.numNotifiedInEmail = numNotifiedInEmail;
    }
    public void setLocation(String location){
        this.location = location;
    }

//    getter methods

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public float getBalance() {
        return balance;
    }

    public int getNumNotifiedInEmail() {
        return numNotifiedInEmail;
    }
    public String getLocation(){
        return this.location;
    }
}
