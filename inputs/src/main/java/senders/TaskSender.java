package senders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import models.Priority;
import models.Region;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.SummonerResource;
import utils.Amqp;
import utils.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

            File fileKey = new File("./inputs/src/main/resources/ids.json");
            String aString = "";
            try {
                aString = new Scanner(fileKey).useDelimiter("\\Z").next();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            JSONArray jsonArray = new JSONArray(aString);

            List<Integer> euw = new ArrayList<>();
            List<Integer> na= new ArrayList<>();
            Integer[] euwArray = new Integer[10];
            Integer[] naArray = new Integer[10];
            Task task;

            for(int j = 0;j < jsonArray.length();j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                if(jsonObject.getString("id_region").equals("euw")){
                    euw.add(jsonObject.getInt("id_lol"));
                    if(euw.size() == 10 ) {
                        task = new Task(SummonerResource.getSummoners(Region.valueOf(jsonObject.getString("id_region")),euw.toArray(euwArray)), Priority.LOW);
                        channel.basicPublish("", Amqp.QUEUE_TASK_LOW, null, mapper.writeValueAsBytes(task));
                        log.debug("task {} sent",task);
                        euw = new ArrayList<>();
                    }
                }else{
                    na.add(jsonObject.getInt("id_lol"));
                    if(na.size()==10){
                        task = new Task(SummonerResource.getSummoners(Region.valueOf(jsonObject.getString("id_region")),na.toArray(naArray)), Priority.LOW);
                        channel.basicPublish("", Amqp.QUEUE_TASK_LOW, null, mapper.writeValueAsBytes(task));
                        log.debug("task {} sent",task);
                        na = new ArrayList<>();
                    }
                }
            }
        if(euw.size()>0) {
            task = new Task(SummonerResource.getSummoners(Region.euw, euw.toArray(new Integer[euw.size()])), Priority.LOW);
            channel.basicPublish("", Amqp.QUEUE_TASK_LOW, null, mapper.writeValueAsBytes(task));
        }
        if(na.size()>0) {
            task = new Task(SummonerResource.getSummoners(Region.na, na.toArray(new Integer[na.size()])), Priority.LOW);
            channel.basicPublish("", Amqp.QUEUE_TASK_LOW, null, mapper.writeValueAsBytes(task));
        }

        System.exit(0);
    }
}
