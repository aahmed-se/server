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

        for (int i = 0; i < 1; i++) {
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
            Task task;
            ObjectMapper mapper = new ObjectMapper();

            for(int j = 0;i < jsonArray.length();j++) {
                JSONObject aJsonKey = jsonArray.getJSONObject(j);
                if(aJsonKey.getString("id_region") == "euw"){
                    euw.add(aJsonKey.getInt("id-lol"));
                    if(euw.size() == 10 ) {
                        task = new Task(SummonerResource.getSummoners(Region.valueOf(aJsonKey.getString("id_region")),new Integer[]{}), Priority.LOW);
                        channel.basicPublish("", Amqp.QUEUE_TASK, null, mapper.writeValueAsBytes(task));
                        euw = new ArrayList<>();
                    }
                }else{
                    na.add(aJsonKey.getInt("id-lol"));
                    if(na.size()==10){
                        task = new Task(SummonerResource.getSummoners(Region.valueOf(aJsonKey.getString("id_region")),(Integer[])na.toArray()), Priority.LOW);
                        channel.basicPublish("", Amqp.QUEUE_TASK, null, mapper.writeValueAsBytes(task));
                        na = new ArrayList<>();
                    }
                }
                task = new Task(SummonerResource.getSummoners(Region.euw,(Integer[])euw.toArray()), Priority.LOW);
                channel.basicPublish("", Amqp.QUEUE_TASK, null, mapper.writeValueAsBytes(task));
                task = new Task(SummonerResource.getSummoners(Region.na,(Integer[])na.toArray()), Priority.LOW);
                channel.basicPublish("", Amqp.QUEUE_TASK, null, mapper.writeValueAsBytes(task));

            }


        }

        System.exit(0);
    }
}
