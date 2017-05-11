package com.web.rest.client;

import com.web.jsf.dto.FileModel;
import com.web.jsf.utils.ParamUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
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

@ManagedBean(eager = true, name = "videoUploadRest")
@ApplicationScoped
public class RESTVideoUploadBean implements Serializable {

    private final String SERVICE_PATH = "http://localhost:7700/upvideo/rest/";

    private static final long serialVersionUID = 1L;

    public RESTVideoUploadBean() {
        //System.out.println("RESTVideoUploadBean called");
    }

    @PostConstruct
    public void init() {

    }

    public Integer saveVideo(FileModel model) {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        Integer returnedId = 0;
        try {
            HttpPost request = new HttpPost(SERVICE_PATH + "file/save");
            JSONObject json = new JSONObject();
            json.put("userId", model.getUserId());
            json.put("title", model.getTitle());
            json.put("mimetype", model.getMimetype());
            json.put("filesize", model.getFilesize());
            json.put("content", model.getContent());
            StringEntity params = new StringEntity(json.toString(), "UTF-8");
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) CLIENT.execute(request);
            HttpEntity entity = response.getEntity();
            returnedId = ParamUtil.integerValue(EntityUtils.toString(entity));
        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTVideoUploadBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTVideoUploadBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return returnedId;
    }

    public List<FileModel> getUserUploadedVideoList(String userId) {
        List<FileModel> list = null;
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        try {

            HttpGet request = new HttpGet(SERVICE_PATH + "find/user/uploaded/" + userId);
            //request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            HttpResponse response = CLIENT.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            HttpEntity entity = response.getEntity();

            ObjectMapper mapper = new ObjectMapper();
            list = mapper.readValue(EntityUtils.toString(entity), List.class);
        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTVideoUploadBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTVideoUploadBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return list;
    }

    public List<FileModel> getUploadedVideoList() {
        List<FileModel> list = null;
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        try {

            HttpGet request = new HttpGet(SERVICE_PATH + "find/all");
            //request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            HttpResponse response = CLIENT.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            HttpEntity entity = response.getEntity();

            ObjectMapper mapper = new ObjectMapper();
            list = mapper.readValue(EntityUtils.toString(entity), List.class);
        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTVideoUploadBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTVideoUploadBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return list;
    }

    public FileModel getVideoById(String videoId) {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        FileModel model = null;
        try {

            HttpGet request = new HttpGet(SERVICE_PATH + "findone/" + videoId);
            request.addHeader("charset", "UTF-8");
            HttpResponse response = CLIENT.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            HttpEntity entity = response.getEntity();

            ObjectMapper mapper = new ObjectMapper();
            model = mapper.readValue((EntityUtils.toString(entity)), FileModel.class);

        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTVideoUploadBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTVideoUploadBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return model;

    }

    public FileModel getVideoByIdAndUserId(String videoId, String userId) {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        FileModel model = null;
        try {

            HttpGet request = new HttpGet(SERVICE_PATH + "find/user/one/" + videoId + "/" + userId);
            request.addHeader("charset", "UTF-8");
            HttpResponse response = CLIENT.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            HttpEntity entity = response.getEntity();

            ObjectMapper mapper = new ObjectMapper();
            model = mapper.readValue((EntityUtils.toString(entity)), FileModel.class);

        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTVideoUploadBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTVideoUploadBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return model;

    }

}
