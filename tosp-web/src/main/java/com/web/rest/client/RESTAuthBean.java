package com.web.rest.client;

import com.web.jsf.dto.UserModel;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

@ManagedBean(eager = true, name = "restAuthBean")
@ApplicationScoped
public class RESTAuthBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String SERVICE_PATH = "http://localhost:9900/auth/user/";

    public RESTAuthBean() {

    }

    @PostConstruct
    public void init() {
        //  System.out.println("RESTAuthBean called");
    }

    public static void main(String args[]) {
        RESTAuthBean obj = new RESTAuthBean();
        UserModel model = new UserModel();
        model.setPassword("aaaaaa");
        model.setUsername("cdawefrkkyyegelos");
        model.setEmail("carlodswefgeyyrk@mail.ru");
        model.setFirstname("Hdafrloks");
        model.setLastname("HarlfweoskLastname");
        UserModel userId = obj.saveUser(model);
      //  System.out.println("userId.getUserId() " + userId.getUserId());
        UserModel newModel = obj.getUserById(userId.getUserId());
        //  System.out.println("newModel " + newModel.toString());

        UserModel newModel1 = obj.getUserByEmail(newModel.getEmail());
        obj.userLogin(newModel1);

    }

    public UserModel saveUser(UserModel model) {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        //  String userId = null;
        try {
            HttpPost request = new HttpPost(SERVICE_PATH + "register");
            JSONObject json = new JSONObject();
            json.put("firstname", model.getFirstname());
            json.put("lastname", model.getLastname());
            json.put("email", model.getEmail());
            json.put("password", model.getPassword());
            json.put("username", model.getUsername());
            StringEntity params = new StringEntity(json.toString(), "UTF-8");
            request.addHeader("content-type", "application/json;charset=UTF-8");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) CLIENT.execute(request);
            HttpEntity entity = response.getEntity();
            // userId = (EntityUtils.toString(entity));

            ObjectMapper mapper = new ObjectMapper();
            model = mapper.readValue((EntityUtils.toString(entity)), UserModel.class);
        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTAuthBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTAuthBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return model;
    }

    public UserModel getUserById(String userId) {
        UserModel userModel = new UserModel();
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(SERVICE_PATH + "find/" + userId);
            request.addHeader("charset", "UTF-8");
            HttpResponse response = CLIENT.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            userModel = mapper.readValue((EntityUtils.toString(entity)), UserModel.class);

        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTAuthBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTAuthBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return userModel;

    }

    public UserModel getUserByEmail(String email) {
        UserModel userModel = null;
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        try {

            HttpGet request = new HttpGet(SERVICE_PATH + "find/email/" + email);
            request.addHeader("charset", "UTF-8");
            HttpResponse response = CLIENT.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            HttpEntity entity = response.getEntity();

            ObjectMapper mapper = new ObjectMapper();
            userModel = mapper.readValue((EntityUtils.toString(entity)), UserModel.class);
        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTAuthBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTAuthBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return userModel;

    }

    public UserModel getUserByUsername(String username) {
        UserModel userModel = new UserModel();
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(SERVICE_PATH + "find/username/" + username);
            request.addHeader("charset", "UTF-8");
            HttpResponse response = CLIENT.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            HttpEntity entity = response.getEntity();

            ObjectMapper mapper = new ObjectMapper();
            userModel = mapper.readValue((EntityUtils.toString(entity)), UserModel.class);

        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTAuthBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTAuthBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return userModel;

    }

    public UserModel userLogin(UserModel model) {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        UserModel userModel = null;
        try {
            HttpPost request = new HttpPost(SERVICE_PATH + "login");
            JSONObject json = new JSONObject();
            json.put("password", model.getPassword());
            json.put("email", model.getEmail());
            StringEntity params = new StringEntity(json.toString(), "UTF-8");
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) CLIENT.execute(request);
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            userModel = mapper.readValue((EntityUtils.toString(entity)), UserModel.class);
            System.out.println("userModel after login " + userModel.toString());
        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTAuthBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTAuthBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return userModel;
    }

}
