package responses;

import models.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Kaylleur on 27/01/2016.
 */
public class GameResponse extends Response{

    public long id;
    public int championId;
    public long endGame;
    public List<Game.Player> fellowPlayers;
    public GameMode gameMode;
    public String gameType;
    public GameSubType gameSubType;
    public boolean invalid;
    public int ipEarned;
    public int level;
    public double gameId;
    public SummonerSpell spell1;
    public SummonerSpell spell2;
    public Map<String, Integer> stats;
    public Team teamId;
    
    @Override
    public Game castToModel() {
        return new Game(this);
    }
}
