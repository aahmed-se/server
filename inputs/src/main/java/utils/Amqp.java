package utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import models.Priority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static conf.Configuration.CONFIG;
/**
 * Created by Thomas on 19/11/2015.
 */
public class Amqp {
    public final static String QUEUE_TASK_HIGH = "tasks_"+ Priority.HIGH;
    public final static String QUEUE_TASK_MEDIUM = "tasks_"+ Priority.MEDIUM;
    public final static String QUEUE_TASK_LOW = "tasks_"+ Priority.LOW;
    public final static String QUEUE_MODEL = "models";
    private static final Logger log = LoggerFactory.getLogger(Amqp.class);
    //Singleton
    public static Amqp instance;

    private String uri;
    private String username;
    private String password;
    private Connection connection;
    private Channel channel;
    private ConnectionFactory factory;

    private Amqp() throws Exception {
        uri = "amqp://" + CONFIG.getString("amqp.address") + ":" + CONFIG.getString("amqp.port") + "/" + CONFIG.getString("amqp.vhost");
        username = CONFIG.getString("amqp.user.login");
        password = CONFIG.getString("amqp.user.password");

        //initialize channel
        factory = new ConnectionFactory();
        factory.setUri(uri);
        factory.setUsername(username);
        factory.setPassword(password);
        connection = factory.newConnection();

        channel = connection.createChannel();
        Map<String, Object> args = new HashMap<>();

        args.put("x-max-priority", Priority.HIGH.getIndex());
        channel.queueDeclare(Amqp.QUEUE_TASK_HIGH,false,false,false,args);

        args.put("x-max-priority", Priority.MEDIUM.getIndex());
        channel.queueDeclare(Amqp.QUEUE_TASK_MEDIUM,false,false,false,args);

        args.put("x-max-priority", Priority.LOW.getIndex());
        channel.queueDeclare(Amqp.QUEUE_TASK_LOW,false,false,false,args);

        channel.queueDeclare(Amqp.QUEUE_MODEL,false,false,false,null);

        instance = this;
    }

    public static Amqp get() {
        if(instance == null){
            try {
                new Amqp();
            } catch (Exception e) {
                if(log.isDebugEnabled()) e.printStackTrace();
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
