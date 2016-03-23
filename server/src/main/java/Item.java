import java.lang.reflect.Field;

/**
 * Created by CLOE on 13/01/2016.
 */
public class Item implements DataProcessing<Item> {

    private int _id;
    private double id;
    private String name;
    private String group;
    private String description;
    private String plainText;


    public Item(int _id, double id, String name, String group, String description, String plainText) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.group = group;
        this.description = description;
        this.plainText = plainText;
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

    public Field[] getDeclaredFields() {
        return this.getDeclaredFields();
    }

}
