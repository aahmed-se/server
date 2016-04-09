package consumers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.AMQP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Amqp;
import utils.Harvester;


import java.io.IOException;

/**
 * Created by Thomas on 19/11/2015.
 */
public class ModelConsumer extends DefaultConsumer {
    private static final Logger log = LoggerFactory.getLogger(ModelConsumer.class);

    private static ModelConsumer instance;

    private ModelConsumer(Channel channel) throws IOException {
        super(channel);
        channel.basicConsume(Amqp.QUEUE_MODEL,true,this);

        instance = this;
    }

    public static ModelConsumer get(Channel channel){
        if(instance == null){
            try {
                new ModelConsumer(channel);
            } catch (IOException e) {
                log.error("An exception has been thrown '{}', {}",e.getClass(),e.getMessage());
            }
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

    }

    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        log.debug("The channel has been shut down because : {}",sig.getReason());
    }

    public void handleRecoverOk(String consumerTag) {
        log.debug("A recover sign has been sent. Messages will be redelivered.");
    }

    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        log.debug("New messages incoming on {}", Amqp.QUEUE_MODEL);
        Harvester.basicReceipt(body);
    }
}
