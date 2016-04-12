package responses;

import manager.CRUD;

/**
 * Created by Maxime on 26/12/2015.
 */
public abstract class Response {

    public Response() {
    }

    public abstract <T extends CRUD> T castToModel();
}
