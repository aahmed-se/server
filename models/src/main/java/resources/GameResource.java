package resources;

import models.Priority;
import models.Region;
import services.SummonerService;

/**
 * Created by Maxime on 27/01/2016.
 */
public class GameResource extends Resource {

    protected GameResource(Class aClass, String method, Class[] parametersType, Object[] parameters, Region region, Priority priority) {
        super(aClass, method, parametersType, parameters, region, priority);
    }

    public static Resource getResources(Integer[] ids, Region reg, Priority prio){
        Class[] classes = new Class[]{ids.getClass()};
        System.out.println("(M) Nombre de class dans la resources : " + classes.length);
        return new GameResource(SummonerService.class,"load",classes,new Object[]{ids},reg,prio);
    }
}
