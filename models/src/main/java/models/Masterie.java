package models;

import org.mongodb.morphia.annotations.Entity;

import java.util.ArrayList;

/**
 * Created by CLOE on 13/01/2016.
 */
@Entity("masterie")
public class Masterie extends Model{

    private double id;
    private String name;
    private ArrayList<String> description;

    public Masterie(double id, String name, ArrayList<String> description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Masterie(double id, String name) {
        this.id = id;
        this.name = name;
        this.description = new ArrayList<String>();
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

    @Override
    public Masterie find() {
        return null;
    }
}
