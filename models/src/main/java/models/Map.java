package models;

import java.lang.reflect.Field;

/**
 * Created by CLOE on 13/01/2016.
 */
public class Map {

    private int _id;
    private String mapId;
    private String name;
    private String notes;

    public Map(int _id, String mapId, String name, String notes) {
        this._id = _id;
        this.mapId = mapId;
        this.name = name;
        this.notes = notes;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
