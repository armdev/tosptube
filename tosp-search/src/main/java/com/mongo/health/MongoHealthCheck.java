package com.mongo.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.CommandResult;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;

public class MongoHealthCheck extends HealthCheck {
    //http://localhost:9001/admin/healthcheck

    //http://metrics.dropwizard.io/3.1.0/manual/healthchecks/
    private final MongoClient mongo;

    public MongoHealthCheck(MongoClient mongo) {
        super();
        this.mongo = mongo;
    }

    @Override
    protected Result check() throws Exception {
        try {
            CommandResult db = mongo.getDB("system").getStats();
            if (!db.ok()) {
                Result.unhealthy("DOWN_DOWN");
            }
        } catch (MongoClientException ex) {
            return Result.unhealthy("MONGODOWN");
        }
        return Result.healthy("MONGOUP");
    }

}
