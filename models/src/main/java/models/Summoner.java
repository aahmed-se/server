package models;

import mongo.Database;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kaylleur on 13/01/2016.
 */
@Entity(value = "summoners", noClassnameStored = true)
public class Summoner extends Model {

    private static final Logger log = LoggerFactory.getLogger(Summoner.class);

    private long id;
    private String name;
    private long profileIconId;
    private int summonerLevel;
    private long revisionDate;
    private Region region;
    private long created;
    private long updated;

    public Summoner() {
    }

    public Summoner(ObjectId _id, long id, String name, long profileIconId, int summonerLevel, long revisionDate, Region region) {
        super(_id);
        this.id = id;
        this.name = name;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
        this.region = region;
    }

    public Summoner(long id, String name, long profileIconId, int summonerLevel, long revisionDate, Region region) {
        super();
        this.id = id;
        this.name = name;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
        this.region = region;
    }

    public double getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Region getRegion() {
        return region;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(long profileIconId) {
        this.profileIconId = profileIconId;
    }

    public double getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(int summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public long getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(long revisionDate) {
        this.revisionDate = revisionDate;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Summoner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profileIconId=" + profileIconId +
                ", summonerLevel=" + summonerLevel +
                ", revisionDate=" + revisionDate +
                ", region=" + region +
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
