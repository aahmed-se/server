import consumers.ModelConsumer;
import mongo.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Harvester;

/**
 * Created by Thomas on 06/04/2016.
 */
public class Server {
    final static Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        try {
            Database.get();

            ModelConsumer.init();
            Thread threadHarvester = new Thread(Harvester.get());
            threadHarvester.start();

        } catch (Exception e) {
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
