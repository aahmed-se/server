import java.util.ArrayList;

/**
 * Created by CLOE on 13/01/2016.
 */
public class Masteries {

    private int _id;
    private double id;
    private String name;
    private ArrayList<String> description;

    public Masteries(int _id, double id, String name, ArrayList<String> description) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Masteries(int _id, double id, String name) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.description = new ArrayList<String>();
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

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }
}
