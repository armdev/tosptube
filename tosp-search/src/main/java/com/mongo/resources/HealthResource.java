package com.mongo.resources;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/metrics")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class HealthResource {

    private final MongoClient mongo;

    public HealthResource(MongoClient mongo) {
        this.mongo = mongo;
    }

    @GET
    @Path("/health")
    @Produces("text/plain")
    public String health() {
        CommandResult db = mongo.getDB("system").getStats();

        if (!db.ok()) {
            return "DBNOTOK";
        }

        return "ok";
    }

}
