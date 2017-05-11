package com.mongo.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.Mongo;
import java.util.List;

public class MongoHealthCheck extends HealthCheck {
//http://localhost:8081/healthcheck

    private final Mongo mongo;

    public MongoHealthCheck(Mongo mongo) {
        // super("MongoDBHealthCheck");
        this.mongo = mongo;
    }

    @Override
    protected Result check() throws Exception {
        List<String> databaseNames = mongo.getDatabaseNames();
        return Result.healthy();
    }

}
