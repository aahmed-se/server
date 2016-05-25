package senders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.Priority;
import models.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tasks.SummonerTask;
import utils.Amqp;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 20/11/2015.
 */
public class TaskSender {
    private static final Logger log = LoggerFactory.getLogger(TaskSender.class);

    public static void main(String[] args) throws Exception {
        //Connection to the amqp server
        Amqp amqp = Amqp.get();
        Channel channel = amqp.getChannel();
        ObjectMapper mapper = new ObjectMapper();

        Config ids = ConfigFactory.load("ids.json");

            List<? extends Config> idsConfigList= ids.getConfigList("ids");

            List<Integer> euw = new ArrayList<>();
            List<Integer> na= new ArrayList<>();
            Integer[] euwArray = new Integer[10];
            Integer[] naArray = new Integer[10];
            Task task;

            for(int j = 0;j < idsConfigList.size();j++) {
                Config jsonObject = idsConfigList.get(j);
                if(jsonObject.getString("id_region").equals("euw")){
                    euw.add(jsonObject.getInt("id_lol"));
                    if(euw.size() == 10 ) {
                        task = new Task(SummonerTask.getSummoners(Region.valueOf(jsonObject.getString("id_region")),euw.toArray(euwArray)), Priority.LOW);
                        channel.basicPublish("", Amqp.QUEUE_TASK_LOW, null, mapper.writeValueAsBytes(task));
                        log.debug("task {} sent",task);
                        euw = new ArrayList<>();
                    }
                }else{
                    na.add(jsonObject.getInt("id_lol"));
                    if(na.size()==10){
                        task = new Task(SummonerTask.getSummoners(Region.valueOf(jsonObject.getString("id_region")),na.toArray(naArray)), Priority.LOW);
                        channel.basicPublish("", Amqp.QUEUE_TASK_LOW, null, mapper.writeValueAsBytes(task));
                        log.debug("task {} sent",task);
                        na = new ArrayList<>();
                    }
                }
            }
        if(euw.size()>0) {
            task = new Task(SummonerTask.getSummoners(Region.euw, euw.toArray(new Integer[euw.size()])), Priority.LOW);
            channel.basicPublish("", Amqp.QUEUE_TASK_LOW, null, mapper.writeValueAsBytes(task));
        }
        if(na.size()>0) {
            task = new Task(SummonerTask.getSummoners(Region.na, na.toArray(new Integer[na.size()])), Priority.LOW);
            channel.basicPublish("", Amqp.QUEUE_TASK_LOW, null, mapper.writeValueAsBytes(task));
        }

        System.exit(0);
    }
}
