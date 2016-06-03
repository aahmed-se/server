package models;

import java.lang.reflect.Field;

/**
 * Created by Kaylleur on 13/01/2016.
 */
public class BannedChampion {

    private double pickTurn;
    private double championId;
    private double teamId;

    public BannedChampion(double pickTurn, double championId, double teamId) {
        this.pickTurn = pickTurn;
        this.championId = championId;
        this.teamId = teamId;
    }

    public double getPickTurn() {
        return pickTurn;
    }

    public void setPickTurn(double pickTurn) {
        this.pickTurn = pickTurn;
    }

    public double getChampionId() {
        return championId;
    }

    public void setChampionId(double championId) {
        this.championId = championId;
    }

    public double getTeamId() {
        return teamId;
    }

    public void setTeamId(double teamId) {
        this.teamId = teamId;
    }
}
