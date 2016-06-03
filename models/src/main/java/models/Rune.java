package models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by Kaylleur on 13/01/2016.
 */
@Entity(value = "rune",noClassnameStored = true)
public class Rune extends Model{

    private long id;
    private String name;
    private String description;
    @Embedded
    private SubRune rune;

    public Rune() {
    }

    public Rune(long id, String name, String description, SubRune rune) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rune = rune;
    }

    public Rune(ObjectId _id, long id, String name, String description, SubRune rune) {
        super(_id);
        this.id = id;
        this.name = name;
        this.description = description;
        this.rune = rune;
    }

    @Embedded
    public class SubRune {

        public SubRune() {
        }

        public boolean isRune;
        public String tier;
        public String type;
    }
}
