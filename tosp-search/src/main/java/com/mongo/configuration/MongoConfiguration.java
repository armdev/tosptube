package com.mongo.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Setter
@Getter
public class MongoConfiguration extends Configuration {

    @JsonProperty
    @NotEmpty
    private String mongohost;

    @JsonProperty
    private int mongoport = 27017;

    @JsonProperty
    @NotEmpty
    private String mongodb = "youtuberDB";

    @JsonProperty
    private Map<String, Object> defaultHystrixConfig;

    public Map<String, Object> getDefaultHystrixConfig() {
        return defaultHystrixConfig;
    }   
}
