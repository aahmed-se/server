package responses;

/**
 * Created by to116676 on 02/06/2016.
 */
public class ForgottenPasswordResponse {
    public boolean sent;
    public String message;

    public ForgottenPasswordResponse() {
    }
    public ForgottenPasswordResponse(boolean sent, String message) {
        this.sent = sent;
        this.message = message;
    }


}
