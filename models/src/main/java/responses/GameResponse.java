package responses;

import mongoClient.Model;

/**
 * Created by Maxime on 27/01/2016.
 */
public class GameResponse extends Response{
    @Override
    public <T extends Model> T castToModel() {
        return null;
    }
}
