package com.web.rest.client;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

@ManagedBean(eager = true, name = "healthCheckClient")
@ApplicationScoped
public class HealthCheckClient implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String SERVICE_PATH = "http://localhost:9000/youtuber/metrics/health";

    @Setter
    @Getter
    private boolean backendHealth = true;

    private ScheduledExecutorService scheduler;

    public HealthCheckClient() {
        //System.out.println("HealthCheckClient called");
    }

    @PostConstruct
    public void init() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new HealthCheck(), 0, 15, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void destroy() {
        scheduler.shutdownNow();
    }

    public boolean checkBackendHealth() {
        // System.out.println("Check started !!!");
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(SERVICE_PATH);
            HttpResponse response = CLIENT.execute(request);
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            String ok = (EntityUtils.toString(entity));
            backendHealth = ok.equalsIgnoreCase("ok"); // System.out.println("Ok ? " + ok);
        } catch (IOException | ParseException ex) {
            backendHealth = false;
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(HealthCheckClient.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(HealthCheckClient.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return backendHealth;
    }

    class HealthCheck implements Runnable {
        @Override
        public void run() {
            checkBackendHealth();
        }
    }
}
