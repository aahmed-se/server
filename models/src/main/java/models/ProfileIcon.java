package models;

import java.lang.reflect.Field;

/**
 * Created by CLOE on 13/01/2016.
 */
public class ProfileIcon {

    private int _id;
    private int id;
    private String blob;

    public ProfileIcon(int _id, int id, String blob) {
        this._id = _id;
        this.id = id;
        this.blob = blob;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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
