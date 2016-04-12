package responses;

import manager.CRUD;

/**
 * Created by thomas on 10/03/16.
 */
public class KeyResponse extends Response{

    public String value;
    public Integer rateLimit;

    @Override
    public <T extends CRUD> T castToModel() {
        return null;
    }
}