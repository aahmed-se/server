package resources;

import models.Region;
import services.SummonerService;

/**
 * Created by Thomas on 03/11/2015.
 * Here we can see that we load 1 or N summoners with the same method
 */
public class SummonerResource extends Resource {

    public SummonerResource(Class aClass, String method, Class[] parametersType, Object[] parameters) {
        super(aClass, method, parametersType, parameters);
    }

    /**
     * Method per action
     */

    public static Resource getSummoners(Region region, Integer... ids){
        return new SummonerResource(SummonerService.class, "getSumonersByIds",new Class[]{Region.class,ids.getClass(),},new Object[]{region,ids});
    }

    public static Resource getSummonersByNames(Region region, String... names){
        return new SummonerResource(SummonerService.class,"getSummonersByNames",new Class[]{Region.class,names.getClass()},new Object[]{region,names});
    }
}
