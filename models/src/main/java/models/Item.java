package models;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by Kaylleur on 13/01/2016.
 */
@Entity(value = "items",noClassnameStored = true)
public class Item {

    @Id
    private long id;
    private String name;
    private String group;
    private String description;
    private String plainText;


    public Item(long id, String name, String group, String description, String plainText) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.description = description;
        this.plainText = plainText;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

}
