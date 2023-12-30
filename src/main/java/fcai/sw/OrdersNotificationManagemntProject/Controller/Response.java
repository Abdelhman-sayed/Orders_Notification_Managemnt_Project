package fcai.sw.OrdersNotificationManagemntProject.Controller;

import lombok.Getter;

@Getter
public class Response {

    private boolean status;
    private String message;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
