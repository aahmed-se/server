package models;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by thomas on 27/04/16.
 */
@Embedded
public class Skin {
    private long id;
    private String name;
    private int num;
    private byte[] splashArt;
    private byte[] loadingArt;

    public long getId() {
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public byte[] getSplashArt() {
        return splashArt;
    }

    public void setSplashArt(byte[] splashArt) {
        this.splashArt = splashArt;
    }

    public byte[] getLoadingArt() {
        return loadingArt;
    }

    public void setLoadingArt(byte[] loadingArt) {
        this.loadingArt = loadingArt;
    }

    @Override
    public String toString() {
        return "Skin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}
