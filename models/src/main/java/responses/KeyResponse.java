package responses;

import models.Model;

/**
 * Created by thomas on 10/03/16.
 */
public class KeyResponse extends Response{

    public String value;
    public Integer rateLimit;

    @Override
    public <T extends Model> T castToModel() {
        return null;
    }
}