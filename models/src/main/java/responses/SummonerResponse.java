package responses;

import models.Summoner;

import java.math.BigInteger;

/**
 * Created by Maxime on 26/12/2015.
 */
public class SummonerResponse extends Response{

    public Integer id;
    public String name;
    public Integer profileIconId;
    public BigInteger revisionDate;
    public Integer summonerLevel;

    @Override
    public Summoner castToModel() {
        return new Summoner(this);
    }
}
