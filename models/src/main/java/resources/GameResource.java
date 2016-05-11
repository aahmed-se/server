package resources;

import models.Region;

/**
 * Created by Maxime on 27/01/2016.
 */
public class GameResource extends Resource {

    protected GameResource(Class aClass, String method, Class[] parametersType, Object[] parameters, Region region) {
        super(aClass, method, parametersType, parameters, region);
    }
}
