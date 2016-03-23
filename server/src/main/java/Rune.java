import java.lang.reflect.Field;

/**
 * Created by CLOE on 13/01/2016.
 */
public class Rune implements DataProcessing<Rune> {

    private String tier;
    private String type;

    public Rune(String tier, String type) {
        this.tier = tier;
        this.type = type;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Field[] getDeclaredFields() {
        return this.getDeclaredFields();
    }

}
