package bootstrap;


import io.swagger.jaxrs.config.BeanConfig;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;

import static conf.Configuration.CONFIG;
/**
 * Created by thomas on 27/04/16.
 */
public class Main {

    final static Logger log = LoggerFactory.getLogger(Main.class);

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://" + CONFIG.getString("api.address") + CONFIG.getString("api.port") + CONFIG.getString("api.basePath");

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in resources package
//        final ResourceConfig rc = new ResourceConfig().packages("resources","io.swagger.jaxrs.listing");
        final ResourceConfig rc = new ResourceConfig().packages("resources","io.swagger.jaxrs.listing");

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost(CONFIG.getString("api.address") + CONFIG.getString("api.port"));
        beanConfig.setBasePath(CONFIG.getString("api.basePath"));
        beanConfig.setResourcePackage("resources");
        beanConfig.setScan(true);

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = Main.startServer();

        CLStaticHttpHandler staticHttpHandler = new CLStaticHttpHandler(Main.class.getClassLoader(), "swagger/");//bound to the resources/swagger
        server.getServerConfiguration().addHttpHandler(staticHttpHandler, "/api-docs/");//listen on :8080/api-docs/

        log.info("Jersey app started with WADL available at {}\n" +
                "Api documentation is available at {}\n" +
                "Hit enter to stop it...",
                Main.BASE_URI,
                Main.BASE_URI.replace("api","api-docs"));
        System.in.read();
        server.stop();
    }
}
