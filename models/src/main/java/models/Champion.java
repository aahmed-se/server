package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import java.util.List;

/**
 * Created by Kaylleur on 13/01/2016.
 */
@Entity(value = "champion", noClassnameStored = true)
@Indexes(@Index("key"))
public class Champion extends Model {

    private int id;
    private String key;
    private String name;
    private String title;
    @Embedded
    private List<Skin> skins;

    public Champion(ObjectId _id, int id, String key, String name, String title) {
        super(_id);
        this.id = id;
        this.key = key;
        this.name = name;
        this.title = title;
    }

    public Champion() {
    }

    @Override
    public Champion find() {
        return null;
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
