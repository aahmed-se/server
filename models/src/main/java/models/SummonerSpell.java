package models;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Kaylleur on 13/01/2016.
 */
@Entity(value = "summonerSpells",noClassnameStored = true)
public class SummonerSpell extends Model {

    private String name;
    private String description;
    private int summonerLevel;
    private int id;
    private String key;

    public SummonerSpell(String name, String description, int summonerLevel, int id, String key) {
        this.name = name;
        this.description = description;
        this.summonerLevel = summonerLevel;
        this.id = id;
        this.key = key;
    }

    @Override
    public SummonerSpell find() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(int summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
