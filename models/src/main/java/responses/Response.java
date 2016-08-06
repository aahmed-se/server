package responses;

/**
 * Created by Kaylleur on 26/12/2015.
 */
public class Response {

    protected Integer code;
    private String message;
    private Object body;

    public Response(Integer code, String message, Object body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public Response(Integer code) {
        this.code = code;
    }

    public Response() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
