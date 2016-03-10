package senders;

import controller.Priority;
import controller.Region;
import controller.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.SummonerResource;
import utils.Amqp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

/**
 * Created by Thomas on 20/11/2015.
 */
public class RequestSender {
    private static final Logger log = LoggerFactory.getLogger(RequestSender.class);


    public static void main(String[] args) throws Exception {
        //Connection to the amqp server
        Amqp amqp = Amqp.get();
        Channel channel;
        channel = Amqp.get().getChannel();
        channel.queueDeclare(Amqp.QUEUE_TASK,false,false,false,null);

        Request request = new Request(SummonerResource.getSummoners(new Integer[]{22253079}, Region.euw, Priority.LOW));

        //publish the json to the queue
        ObjectMapper mapper = new ObjectMapper();
        String jsonToSend = mapper.writeValueAsString(request);
        channel.basicPublish("", Amqp.QUEUE_TASK, null, jsonToSend.getBytes());
        log.error(jsonToSend);

        System.exit(0);
    }
}
