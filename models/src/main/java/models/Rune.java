package models;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by CLOE on 13/01/2016.
 */
@Entity("runes")
public class Rune{
    @Id
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

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public SubRune getRune() {
        return rune;
    }
}

@Embedded
class SubRune {

    public SubRune() {
    }

    public boolean isRune;
    public String tier;
    public String type;
}
