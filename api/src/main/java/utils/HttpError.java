package utils;

import javax.ws.rs.core.Response;

/**
 * Created by thomas on 29/04/16.
 */
public class HttpError {

    public Response.Status status;
    public int code;
    public String message;

    public HttpError(Response.Status status, String message) {
        this.status = status;
        this.code = status.getStatusCode();
        this.message = message;
    }

    public HttpError(int status, String message) {
        this.status = Response.Status.fromStatusCode(status);
        this.code = status;
        this.message = message;
    }

    public HttpError() {
    }
}
