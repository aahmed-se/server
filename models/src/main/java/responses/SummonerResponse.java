package responses;

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
    public Region region;

    @Override
    public Summoner castToModel() {
        return new Summoner(this);
    }

    @Override
    public String toString() {
        return "SummonerResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profileIconId=" + profileIconId +
                ", revisionDate=" + revisionDate +
                ", summonerLevel=" + summonerLevel +
                ", region=" + region +
                '}';
    }
}
