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

            for(int j = 0;i < jsonArray.length();j++) {
                JSONObject aJsonKey = jsonArray.getJSONObject(j);

                Task task = new Task(SummonerResource.getSummoners(Region.valueOf(aJsonKey.getString("id_region")),new Integer[]{aJsonKey.getInt("id-lol")}), Priority.LOW);

                //publish the json to the queue
                ObjectMapper mapper = new ObjectMapper();
                channel.basicPublish("", Amqp.QUEUE_TASK, null, mapper.writeValueAsBytes(task));
                log.debug("Sent {}", task.toString());
            }

        }

        System.exit(0);
    }
}
