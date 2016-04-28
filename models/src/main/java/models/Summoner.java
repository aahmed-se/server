package models;

import mongo.Database;
import mongo.Model;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import responses.SummonerResponse;

/**
 * Created by CLOE on 13/01/2016.
 */
@Entity(value = "summoner", noClassnameStored=true)
public class Summoner extends Model {

    private double id;
    private String name;
    private double profileIconId;
    private double summonerLevel;
    private long revisionDate;
    @Embedded
    private Region region;

    public Summoner() {
    }

    public Summoner(String _id, double id, String name, double profileIconId, double summonerLevel, long revisionDate, Region region) {
        super(_id);
        this.id = id;
        this.name = name;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
        this.region = region;
    }

    public Summoner(double id, String name, double profileIconId, double summonerLevel, long revisionDate,Region region) {
        super();
        this.id = id;
        this.name = name;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
        this.region = region;
    }

    public Summoner(SummonerResponse response){
        this(response.id,response.name,response.profileIconId,response.summonerLevel,response.revisionDate,response.region);
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

    @Override
    public Summoner find() {
        return Database.get().getDatastore()
                .find(Summoner.class)
                .filter("id =",this.id)
                .filter("region =",this.region).get();
    }
}
