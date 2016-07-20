package models;

/**
 * Created by Kaylleur on 02/12/2015.
 */
public enum Priority {
    LOW(0),
    MEDIUM(5),
    HIGH(10);

    private int index;

    Priority(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
