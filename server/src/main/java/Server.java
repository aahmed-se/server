import consumers.ModelConsumer;
import mongo.Database;
import utils.Amqp;

/**
 * Created by Thomas on 06/04/2016.
 */
public class Server {

    public static void main(String[] args) {
        try {
            Database.get();
            ModelConsumer.get(Amqp.get().getChannel());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
