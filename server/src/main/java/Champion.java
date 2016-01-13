/**
 * Created by CLOE on 13/01/2016.
 */
public class Champion {

    private int _id;
    private double id;
    private String key;
    private String name;
    private String title;

    public Champion(int _id, double id, String key, String name, String title) {
        this._id = _id;
        this.id = id;
        this.key = key;
        this.name = name;
        this.title = title;
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
}
