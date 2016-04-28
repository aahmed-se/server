package utils;

import mongo.Model;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by thomas on 26/04/16.
 */
public class Harvester implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Harvester.class);

    private static final Harvester harvester = new Harvester();

    private Queue<Model> queue;

    private Harvester() {
        queue = new LinkedList<Model>();
    }

    public static Harvester get(){
        return harvester;
    }

    public synchronized <T extends Model> void addModel(T model){
        notify();
        queue.offer(model);
    }

    public synchronized Model getNextModel(){
        return queue.poll();
    }

    public synchronized void run(){
        while(true){
            try {
                Model model = getNextModel();
                if(model != null){
                    String id = model.save();
                    log.debug("Model {} save into id {}", model,id);
                }else{
                    wait();
                }
            } catch (Exception e) {
                if(log.isDebugEnabled())e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }


}
