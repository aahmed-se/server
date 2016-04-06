package models;

import java.util.ArrayList;

/**
 * Created by Clo� on 18/11/2015.
 */
public class Game {

    private int id;
    private double gameLength;
    private String gameMode;
    private double mapId;
    private ArrayList<BannedChampion> bannedChampions;
    private String gameType;
    private double gameId;
    private ArrayList<Summoner> observers;
    private double gameQueueConfigId;
    private double gameStartTime;
    private ArrayList<Summoner> participants;
    private String platformId;

    public Game(int id, double gameLength, String gameMode, double mapId, ArrayList<BannedChampion> bannedChampions, String gameType, double gameId, ArrayList<Summoner> observers, double gameQueueConfigId, double gameStartTime, ArrayList<Summoner> participants, String platformId) {
        this.id = id;
        this.gameLength = gameLength;
        this.gameMode = gameMode;
        this.mapId = mapId;
        this.bannedChampions = bannedChampions;
        this.gameType = gameType;
        this.gameId = gameId;
        this.observers = observers;
        this.gameQueueConfigId = gameQueueConfigId;
        this.gameStartTime = gameStartTime;
        this.participants = participants;
        this.platformId = platformId;
    }

    public Game(int id, double gameLength, String gameMode, double mapId, String gameType, double gameId, double gameQueueConfigId, double gameStartTime, String platformId) {
        this.id = id;
        this.gameLength = gameLength;
        this.gameMode = gameMode;
        this.mapId = mapId;
        this.bannedChampions = new ArrayList<BannedChampion>();
        this.gameType = gameType;
        this.gameId = gameId;
        this.observers = new ArrayList<Summoner>();
        this.gameQueueConfigId = gameQueueConfigId;
        this.gameStartTime = gameStartTime;
        this.participants = new ArrayList<Summoner>();
        this.platformId = platformId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGameLength() {
        return gameLength;
    }

    public void setGameLength(double gameLength) {
        this.gameLength = gameLength;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public double getMapId() {
        return mapId;
    }

    public void setMapId(double mapId) {
        this.mapId = mapId;
    }

    public ArrayList<BannedChampion> getBannedChampions() {
        return bannedChampions;
    }

    public void setBannedChampions(ArrayList<BannedChampion> bannedChampions) {
        this.bannedChampions = bannedChampions;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public double getGameId() {
        return gameId;
    }

    public void setGameId(double gameId) {
        this.gameId = gameId;
    }

    public ArrayList<Summoner> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<Summoner> observers) {
        this.observers = observers;
    }

    public double getGameQueueConfigId() {
        return gameQueueConfigId;
    }

    public void setGameQueueConfigId(double gameQueueConfigId) {
        this.gameQueueConfigId = gameQueueConfigId;
    }

    public double getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(double gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public ArrayList<Summoner> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Summoner> participants) {
        this.participants = participants;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }
}
