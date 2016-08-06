package utils;

import static conf.Configuration.CONFIG;

/**
 * Created by Thomas on 04/08/2016.
 */
public class AmqpQueues {
    public final static String QUEUE_TASK_HIGH = CONFIG.getString("amqp.queues.tasks.high");
    public final static String QUEUE_TASK_MEDIUM = CONFIG.getString("amqp.queues.tasks.medium");
    public final static String QUEUE_TASK_LOW = CONFIG.getString("amqp.queues.tasks.low");
    public final static String QUEUE_MODEL_SUMMONER = CONFIG.getString("amqp.queues.models.summoners");

}
