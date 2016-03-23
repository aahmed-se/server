package models;

import java.lang.reflect.Field;

/**
 * Created by CLOE on 13/01/2016.
 */
public class SummonerSpell {

    private int _id;
    private String name;
    private String description;
    private int summonerLevel;
    private int id;
    private String key;

    public SummonerSpell(int _id, String name, String description, int summonerLevel, int id, String key) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.summonerLevel = summonerLevel;
        this.id = id;
        this.key = key;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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
