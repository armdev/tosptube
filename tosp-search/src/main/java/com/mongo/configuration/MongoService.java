package com.mongo.configuration;

import com.codahale.metrics.MetricRegistry;
import com.mongo.dao.SearchDAO;
import com.mongo.dao.VideoDAO;
import com.mongo.resources.SearchKeyResource;
import com.mongo.resources.VideoResource;
import com.mongo.health.MongoHealthCheck;
import com.mongo.resources.HealthResource;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.contrib.codahalemetricspublisher.HystrixCodaHaleMetricsPublisher;
import com.netflix.hystrix.strategy.HystrixPlugins;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.util.Arrays;
import org.apache.commons.configuration.MapConfiguration;
import org.bson.Document;

public class MongoService extends Application<MongoConfiguration> {

    public static void main(String[] args) throws Exception {
        new MongoService().run(args);
    }    

    @Override
    public void initialize(Bootstrap<MongoConfiguration> bootstrap) {
        //bootstrap.("mongo");        
    }

    @Override
    public void run(MongoConfiguration configuration, Environment environment) throws Exception {

        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(100).minConnectionsPerHost(0).threadsAllowedToBlockForConnectionMultiplier(5)
                .connectTimeout(30000).maxWaitTime(120000).maxConnectionIdleTime(0).maxConnectionLifeTime(0).connectTimeout(10000).socketTimeout(0)
                .socketKeepAlive(false).heartbeatFrequency(10000).minHeartbeatFrequency(500).heartbeatConnectTimeout(20000).localThreshold(15)
                .build();
        //Create Mongo instance      
        MongoClient mongo = new MongoClient(Arrays.asList(
                new ServerAddress(configuration.getMongohost(), configuration.getMongoport())),
                options);

        //Add Managed for managing the Mongo instance
        MongoManaged mongoManaged = new MongoManaged(mongo);
        environment.jersey().register(mongoManaged);

        environment.healthChecks().register("mongo", new MongoHealthCheck(mongo));

        // Configuration des commandes Hystrix
        ConfigurationManager.install(new MapConfiguration(configuration.getDefaultHystrixConfig()));

        MetricRegistry metricRegistry = new MetricRegistry();
        HystrixPlugins.getInstance().registerMetricsPublisher(new HystrixCodaHaleMetricsPublisher(metricRegistry));
        MongoDatabase db = mongo.getDatabase(configuration.getMongodb());           
        MongoCollection<Document> videoCollection = db.getCollection("youtube");
        final VideoDAO videoDAO = new VideoDAO(videoCollection);
        environment.jersey().register(new VideoResource(videoDAO));
             
        MongoCollection<Document> searchKeyCollection = db.getCollection("searchkeycoll"); 
        final SearchDAO searchDAO = new SearchDAO(searchKeyCollection);
        environment.jersey().register(new SearchKeyResource(searchDAO));
        
        environment.jersey().register(new HealthResource(mongo));

    }
}
