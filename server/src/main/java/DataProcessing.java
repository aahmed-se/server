import com.mongodb.WriteResult;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by CLOE on 10/02/2016.
 */
public interface DataProcessing<T> {

    public Field[] getDeclaredFields();

    public void insert();

}
