package models;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;

/**
 * Created by CLOE on 13/01/2016.
 */
@Entity("masteries")
public class Mastery{
    @Id
    private long id;
    private String name;
    private ArrayList<String> description;

    public Mastery(){}

    public Mastery(long id, String name, ArrayList<String> description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Mastery(long id, String name) {
        this(id,name,new ArrayList<>());
    }

    public double getId() {
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

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }
}
