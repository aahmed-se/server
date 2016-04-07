package senders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import models.Priority;
import models.Region;
import models.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.SummonerResource;
import utils.Amqp;

/**
 * Created by Thomas on 20/11/2015.
 */
public class TaskSender {
    private static final Logger log = LoggerFactory.getLogger(TaskSender.class);

    public static void main(String[] args) throws Exception {
        //Connection to the amqp server
        Amqp amqp = Amqp.get();
        Channel channel = Amqp.get().getChannel();
        channel.queueDeclare(Amqp.QUEUE_TASK,false,false,false,null);

        Task request = new Task(SummonerResource.getSummoners(Region.euw,new Integer[]{22253079,22169683}), Priority.LOW);

        //publish the json to the queue
        ObjectMapper mapper = new ObjectMapper();
        String jsonToSend = mapper.writeValueAsString(request);
        channel.basicPublish("", Amqp.QUEUE_TASK, null, jsonToSend.getBytes());
        log.debug(jsonToSend);

        System.exit(0);
    }
}
