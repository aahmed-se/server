package senders;

import controller.Request;
import resources.SummonerResource;
import utils.Amqp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

/**
 * Created by Thomas on 20/11/2015.
 */
public class RequestSender {

    public static void main(String[] args) throws Exception {
        //Connection to the amqp server
        Amqp amqp = Amqp.get();
        Channel channel;
        channel = Amqp.get().getChannel();
        channel.queueDeclare(Amqp.QUEUE_TASK,false,false,false,null);

        Request object = new Request(SummonerResource.getSummoners(new Integer[]{22253079},"euw","LOW"));

        //TODO TO IMPLEMENT TO SEND AFTER RECEIVE
        //publish the json to the queue and write it !
        ObjectMapper mapper = new ObjectMapper();
        String jsonToSend = mapper.writeValueAsString(object);
        channel.basicPublish("", Amqp.QUEUE_MODEL, null, jsonToSend.getBytes());
    }
}
