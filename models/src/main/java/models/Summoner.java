package models;

import manager.CRUD;
import responses.SummonerResponse;

import java.lang.reflect.Field;
import java.math.BigInteger;

/**
 * Created by CLOE on 13/01/2016.
 */
public class Summoner extends CRUD {

    private int _id;
    private double id;
    private String name;
    private double profileIconId;
    private double summonerLevel;
    private BigInteger revisionDate;

    public Summoner(int _id, double id, String name, double profileIconId, double summonerLevel, BigInteger revisionDate) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
        this.saveObject();
    }

    public Summoner(double id, String name, double profileIconId, double summonerLevel, BigInteger revisionDate) {
        this.id = id;
        this.name = name;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
    }

    public Summoner(SummonerResponse response){
        this(response.id,response.name,response.profileIconId,response.summonerLevel,response.revisionDate);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(double profileIconId) {
        this.profileIconId = profileIconId;
    }

    public double getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(double summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public BigInteger getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(BigInteger revisionDate) {
        this.revisionDate = revisionDate;
    }

    public Field[] getDeclaredFields() {
        return this.getDeclaredFields();
    }


    @Override
    public String toString() {
        return "Summoner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profileIconId='" + profileIconId + '\'' +
                ", revisionDate=" + revisionDate +
                ", summonerLevel=" + summonerLevel +
                '}';
    }
}
