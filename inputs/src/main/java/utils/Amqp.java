package utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Thomas on 19/11/2015.
 */
public class Amqp {
    private static final Logger log = LoggerFactory.getLogger(Amqp.class);

    public final static String QUEUE_TASK = "tasks";
    public final static String QUEUE_MODEL = "models";

    //Singleton
    public static Amqp instance;

    private String uri;
    private String username;
    private String password;
    private Connection connection;
    private Channel channel;
    private ConnectionFactory factory;

    private Amqp() throws Exception {
        uri = System.getenv("cloud_amqp").split(";")[0];
        username = System.getenv("cloud_amqp").split(";")[1];
        password = System.getenv("cloud_amqp").split(";")[2];

        //initialize channel
        factory = new ConnectionFactory();
        factory.setUri(uri);
        factory.setUsername(username);
        factory.setPassword(password);
        connection = factory.newConnection();

        channel = connection.createChannel();

        instance = this;
    }

    public static Amqp get() {
        if(instance.equals(null)){
            try {
                new Amqp();
            } catch (Exception e) {
                log.error("An exception has been thrown '{}', {}",e.getClass(),e.getMessage());
            }
        }
        return instance;
    }

    public Connection getConnection() throws Exception {
        if(!connection.isOpen())
            connection = factory.newConnection();

        return connection;
    }

    public Channel getChannel() throws Exception {
        if(!channel.isOpen())
            channel = connection.createChannel();
        return channel;
    }
}
