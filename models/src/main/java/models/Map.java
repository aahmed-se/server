package models;

/**
 * Created by CLOE on 13/01/2016.
 */
public class Map {

    private String mapId;
    private String name;
    private String notes;

    public Map(String mapId, String name, String notes) {
        this.mapId = mapId;
        this.name = name;
        this.notes = notes;
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
