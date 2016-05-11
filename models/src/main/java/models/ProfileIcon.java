package models;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by CLOE on 13/01/2016.
 */
@Entity("profileIcon")
public class ProfileIcon extends Model{

    private int id;
    private String blob;

    public ProfileIcon(int id, String blob) {
        this.id = id;
        this.blob = blob;
    }

    @Override
    public ProfileIcon find() {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlob() {
        return blob;
    }

    public void setBlob(String blob) {
        this.blob = blob;
    }

}
