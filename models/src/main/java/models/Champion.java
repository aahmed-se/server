package models;

import org.mongodb.morphia.annotations.*;

import java.util.List;

/**
 * Created by Kaylleur on 13/01/2016.
 */
@Entity(value = "champions", noClassnameStored = true)
@Indexes(@Index("key"))
public class Champion{
    @Id
    private int id;
    private String key;
    private String name;
    private String title;
    @Embedded
    private List<Skin> skins;

    public Champion(int id, String key, String name, String title) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.title = title;
    }

    public Champion() {
    }

    public double getId() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Skin> getSkins() {
        return skins;
    }

    public void setSkins(List<Skin> skins) {
        this.skins = skins;
    }

    @Override
    public String toString() {
        return "Champion{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", skins=" + skins +
                '}';
    }
}
