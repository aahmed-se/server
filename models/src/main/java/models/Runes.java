package models;

import java.util.ArrayList;

/**
 * Created by CLOE on 13/01/2016.
 */
public class Runes {

    private int _id;
    private double id;
    private String name;
    private String description;
    private ArrayList<Rune> rune;

    public Runes(int _id, double id, String name, String description, ArrayList<Rune> rune) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.description = description;
        this.rune = rune;
    }

    public Runes(int _id, double id, String name, String description) {
        this._id = _id;
        this.id = id;
        this.name = name;
        this.description = description;
        this.rune = new ArrayList<Rune>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Rune> getRune() {
        return rune;
    }

    public void setRune(ArrayList<Rune> rune) {
        this.rune = rune;
    }

}
