package conf;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by thomas on 20/05/16.
 */
public class Configuration {
    public static final Config CONFIG = ConfigFactory.load("server");
}
