package utils;

import javax.ws.rs.core.Response;

/**
 * Created by thomas on 29/04/16.
 */
public class HttpResponse {

    public Response.Status status;
    public int code;
    public String message;

    public HttpResponse(Response.Status status, String message) {
        this.status = status;
        this.code = status.getStatusCode();
        this.message = message;
    }

    public HttpResponse(int status, String message) {
        this.status = Response.Status.fromStatusCode(status);
        this.code = status;
        this.message = message;
    }

    public HttpResponse() {
    }
}
