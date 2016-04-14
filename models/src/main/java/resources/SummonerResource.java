package resources;

import models.Priority;
import models.Region;
import services.SummonerService;

/**
 * Created by Thomas on 03/11/2015.
 * Here we can see that we load 1 or N summoners with the same method
 */
public class SummonerResource extends Resource {

    private SummonerResource(Class aClass, String method, Class[] parametersType, Object[] parameters, Region region, Priority priority) {
        super(aClass, method, parametersType, parameters, region, priority);
    }

    public SummonerResource(Class aClass, String method, Class[] parametersType, Object[] parameters) {
        super(aClass, method, parametersType, parameters);
    }

    /**
     * Method per action
     */

    public static Resource getSummoners(Integer[] ids, Region region, Priority priority){
        return new SummonerResource(SummonerService.class,"load",new Class[]{ids.getClass()},new Object[]{ids},region,priority);
    }

    public static Resource getSummoners(Region region, Integer... ids){
        return new SummonerResource(SummonerService.class, "load",new Class[]{Region.class,ids.getClass(),},new Object[]{region,ids});
    }
}
