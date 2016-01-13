/**
 * Created by CLOE on 13/01/2016.
 */
public class Summoner {

    private int _id;
    private double id;
    private String name;
    private double profileIconId;
    private double summonerLevel;
    private double revisionDate;

    public Summoner(int _id, double id, String name, double profileIconId, double summonerLevel, double revisionDate) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
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

    public double getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(double revisionDate) {
        this.revisionDate = revisionDate;
    }
}
