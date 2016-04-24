package consumers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rabbitmq.client.*;
import mongoClient.Model;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import responses.Response;
import utils.Amqp;

import java.io.IOException;

/**
 * Created by Thomas on 19/11/2015.
 */
public class ModelConsumer extends DefaultConsumer{
    private static final Logger log = LoggerFactory.getLogger(ModelConsumer.class);

    private static ModelConsumer instance;

    private ModelConsumer(Channel channel ) throws Exception {
        super(channel);
        try {
            channel.basicConsume(Amqp.QUEUE_MODEL,true,this);
        } catch (IOException e) {
            if(log.isDebugEnabled()) e.printStackTrace();
            log.error(e.getMessage());
        }
        instance = this;
    }

    public static ModelConsumer init() throws Exception {
        if(instance == null){
            new ModelConsumer(Amqp.get().getChannel());
        }
        return instance;
    }

    public void handleConsumeOk(String consumerTag) {
        log.debug("Awaiting to consume messages.");
    }

    public void handleCancelOk(String consumerTag) {
        log.debug("Consumer is canceled");
    }

    public void handleCancel(String consumerTag) throws IOException {
        log.debug("Consumer has been cancelled unexpectedly");
    }

    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        log.debug("The channel has been shut down because : {}",sig.getReason());
    }

    public void handleRecoverOk(String consumerTag) {
        log.debug("A recover sign has been sent. Messages will be redelivered.");
    }

    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        log.debug("New messages incoming on {}", Amqp.QUEUE_MODEL);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readValue(body, ObjectNode.class);
            Class<? extends Response> responseClass = (Class<? extends Response>) Class.forName(node.get("class").asText());
            Response response = mapper.readValue(node.get("object").asText(), responseClass);
            log.debug("Object receive : {} from {}", response, responseClass);
            Model model = response.castToModel();
            ObjectId id = model.save();
            log.debug("Object saved from {} into id {}",model.getClass(),id);
        } catch (Exception e) {
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
