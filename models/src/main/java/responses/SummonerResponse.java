package responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.Region;
import models.Summoner;

/**
 * Created by Maxime on 26/12/2015.
 */
public class SummonerResponse extends Response{

    public Integer id;
    public String name;
    public Integer profileIconId;
    public long revisionDate;
    public Integer summonerLevel;
    @JsonIgnore
    public Region region;

    @Override
    public Summoner castToModel() {
        return new Summoner(this);
    }
}
