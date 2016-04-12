package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import responses.SummonerResponse;

import java.lang.reflect.Field;

/**
 * Created by CLOE on 13/01/2016.
 */
@Entity(value = "summoner")
public class Summoner extends Model {

    private double id;
    private String name;
    private double profileIconId;
    private double summonerLevel;
    private long revisionDate;

    public Summoner(ObjectId _id, double id, String name, double profileIconId, double summonerLevel, long revisionDate) {
        super(_id);
        this.id = id;
        this.name = name;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
        this.save();
    }

    public Summoner(double id, String name, double profileIconId, double summonerLevel, long revisionDate) {
        super(new ObjectId());
        this.id = id;
        this.name = name;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
    }

    public Summoner(SummonerResponse response){
        this(response.id,response.name,response.profileIconId,response.summonerLevel,response.revisionDate);
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

    public long getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(long revisionDate) {
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
