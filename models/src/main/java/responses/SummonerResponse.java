package responses;

import models.Summoner;

/**
 * Created by Kaylleur on 26/12/2015.
 */
public class SummonerResponse extends Response{
    private Summoner[] summoner;

    public SummonerResponse(Integer code, Summoner[] summoner) {
        super(code);
        this.summoner = summoner;
    }

    public Summoner[] getSummoner() {
        return summoner;
    }
}
