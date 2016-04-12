package responses;

import manager.CRUD;

/**
 * Created by Maxime on 27/01/2016.
 */
public class GameResponse extends Response{
    @Override
    public <T extends CRUD> T castToModel() {
        return null;
    }
}
