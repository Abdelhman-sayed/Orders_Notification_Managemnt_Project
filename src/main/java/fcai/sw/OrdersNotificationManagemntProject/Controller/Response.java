package fcai.sw.OrdersNotificationManagemntProject.Controller;

import lombok.Getter;

@Getter
public class Response {

    private boolean status;
    private String message;
    private String token;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {this.token = token;}
}
