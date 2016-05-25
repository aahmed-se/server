package models;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import responses.GameResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by Thomas.B on 18/11/2015.
 */
@Entity("game")
public class Game extends Model{

    private long id;
    private int championId;
    private long endGame;
    private List<Player> fellowPlayers;
    private GameMode gameMode;
    private String gameType;
    private GameSubType gameSubType;
    private boolean invalid;
    private int ipEarned;
    private int level;
    private double gameId;
    private SummonerSpell spell1;
    private SummonerSpell spell2;
    private Map<String, Integer> stats;
    private Team teamId;

    @Override
    public Game find() {
        return null;
    }

    @Embedded
    public class Player{
        public Player(){}
        public int championId;
        public long summonerId;
        public int teamId;
    }
    public Game(){
    }

    public Game(long id, int championId, long endGame, List<Player> fellowPlayers, GameMode gameMode, String gameType, GameSubType gameSubType, boolean invalid, int ipEarned, int level, double gameId, SummonerSpell spell1, SummonerSpell spell2, Map<String, Integer> stats, Team teamId) {
        this.id = id;
        this.championId = championId;
        this.endGame = endGame;
        this.fellowPlayers = fellowPlayers;
        this.gameMode = gameMode;
        this.gameType = gameType;
        this.gameSubType = gameSubType;
        this.invalid = invalid;
        this.ipEarned = ipEarned;
        this.level = level;
        this.gameId = gameId;
        this.spell1 = spell1;
        this.spell2 = spell2;
        this.stats = stats;
        this.teamId = teamId;
    }

    public Game(String _id, long id, int championId, long endGame, List<Player> fellowPlayers, GameMode gameMode, String gameType, GameSubType gameSubType, boolean invalid, int ipEarned, int level, double gameId, SummonerSpell spell1, SummonerSpell spell2, Map<String, Integer> stats, Team teamId) {
        super(_id);
        this.id = id;
        this.championId = championId;
        this.endGame = endGame;
        this.fellowPlayers = fellowPlayers;
        this.gameMode = gameMode;
        this.gameType = gameType;
        this.gameSubType = gameSubType;
        this.invalid = invalid;
        this.ipEarned = ipEarned;
        this.level = level;
        this.gameId = gameId;
        this.spell1 = spell1;
        this.spell2 = spell2;
        this.stats = stats;
        this.teamId = teamId;
    }

    public Game(GameResponse response) {
        this(response.id,
                response.championId,
                response.endGame,
                response.fellowPlayers,
                response.gameMode,
                response.gameType,
                response.gameSubType,
                response.invalid,
                response.ipEarned,
                response.level,
                response.gameId,
                response.spell1,
                response.spell2,
                response.stats,
                response.teamId);
    }

    public long getId() {
        return id;
    }

    public int getChampionId() {
        return championId;
    }

    public long getEndGame() {
        return endGame;
    }

    public List<Player> getFellowPlayers() {
        return fellowPlayers;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public GameSubType getGameSubType() {
        return gameSubType;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public int getIpEarned() {
        return ipEarned;
    }

    public int getLevel() {
        return level;
    }

    public double getGameId() {
        return gameId;
    }

    public SummonerSpell getSpell1() {
        return spell1;
    }

    public SummonerSpell getSpell2() {
        return spell2;
    }

    public Map<String, Integer> getStats() {
        return stats;
    }

    public Team getTeamId() {
        return teamId;
    }
}
