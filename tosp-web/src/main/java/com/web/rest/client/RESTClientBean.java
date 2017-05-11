package com.web.rest.client;

import com.web.jsf.dto.Comment;
import com.web.jsf.dto.YoutubeVideo;
import com.web.jsf.utils.ParamUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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

@ManagedBean(eager = true, name = "restClient")
@ApplicationScoped
public class RESTClientBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private YoutubeVideo oneRandomVideo = new YoutubeVideo();
    private final String SERVICE_PATH = "http://localhost:9000/youtuber/";

    public List<YoutubeVideo> lastSessionList = new ArrayList<>();

    public RESTClientBean() {
      //  System.out.println("RESTClientBean called");
    }

    @PostConstruct
    public void init() {
        this.getLastVideoList();
    }

    public List<YoutubeVideo> getLastVideoList() {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
      //  System.out.println("###getLastVideoList called");
        List<YoutubeVideo> list = new ArrayList<>();
        try {
            HttpGet request = new HttpGet(SERVICE_PATH + "video/find/all");
            request.addHeader("charset", "UTF-8");
            HttpResponse response = CLIENT.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            list = mapper.readValue(EntityUtils.toString(entity), List.class);
            getLastSessionList().addAll(list);
        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return list;
    }

    public Integer addComment(Comment comment) {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        Integer userId = 0;
        try {
            HttpPost request = new HttpPost(SERVICE_PATH + "video/addcomment");
            JSONObject json = new JSONObject();
            json.put("resourceId", comment.getResourceId());
            json.put("comment", comment.getComment());
            json.put("userId", comment.getUserId());
            json.put("username", comment.getUsername());
            StringEntity params = new StringEntity(json.toString(), "UTF-8");
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) CLIENT.execute(request);
            HttpEntity entity = response.getEntity();
            userId = ParamUtil.integerValue(EntityUtils.toString(entity));
        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return userId;
    }

    public Integer saveVideo(YoutubeVideo youtubeVideo) {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        Integer userId = 0;
        try {
            HttpPost request = new HttpPost(SERVICE_PATH + "video/create");
            JSONObject json = new JSONObject();
            json.put("title", youtubeVideo.getTitle());
            json.put("thumbnailUrl", youtubeVideo.getThumbnailUrl());
            json.put("channelTitle", youtubeVideo.getTitle());
            json.put("description", youtubeVideo.getDescription());
            json.put("channelId", youtubeVideo.getChannelId());
            json.put("searchKey", youtubeVideo.getSearchKey());
            json.put("videoId", youtubeVideo.getVideoId());
            json.put("datePublished", youtubeVideo.getDatePublished());
            StringEntity params = new StringEntity(json.toString(), "UTF-8");
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) CLIENT.execute(request);
            HttpEntity entity = response.getEntity();
            userId = ParamUtil.integerValue(EntityUtils.toString(entity));
        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return userId;
    }

    public Integer saveSearchKey(String searchKey) {
        //System.out.println("Key saved " +searchKey);
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        Integer userId = 0;
        try {
            HttpPost request = new HttpPost(SERVICE_PATH + "search/create");
            JSONObject json = new JSONObject();
            json.put("searchKey", searchKey);
            StringEntity params = new StringEntity(json.toString(), "UTF-8");
            request.addHeader("content-type", "application/json");
            request.addHeader("charset", "UTF-8");
            request.setEntity(params);
            HttpResponse response = (HttpResponse) CLIENT.execute(request);
            HttpEntity entity = response.getEntity();
            userId = ParamUtil.integerValue(EntityUtils.toString(entity));
        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return userId;
    }

    public void updateCount(String videoId) {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(SERVICE_PATH + "video/update/count/" + videoId);
            HttpResponse response = CLIENT.execute(request);
            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

    }

    public List<YoutubeVideo> getTopSearches() {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        List<YoutubeVideo> list = null;
        try {

            HttpGet request = new HttpGet(SERVICE_PATH + "search/top/searches");
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
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return list;
    }

    public YoutubeVideo getVideoById(String videoId) {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        YoutubeVideo youtubeVideo = null;
        try {

            HttpGet request = new HttpGet(SERVICE_PATH + "video/find/id/" + videoId);
            request.addHeader("charset", "UTF-8");
            HttpResponse response = CLIENT.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");
            HttpEntity entity = response.getEntity();

            ObjectMapper mapper = new ObjectMapper();
            youtubeVideo = mapper.readValue((EntityUtils.toString(entity)), YoutubeVideo.class);

        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return youtubeVideo;

    }

    public List<YoutubeVideo> getVideoListByQuery(String query) {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        List<YoutubeVideo> list = new ArrayList<>();
        try {

            HttpGet request = new HttpGet(SERVICE_PATH + "video/fetch/key/" + query.replace(" ", "%20"));
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
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return list;
    }

    public List<YoutubeVideo> getRandomList() {
      //  System.out.println("Random call list:");
        List<YoutubeVideo> list = null;
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        try {

            HttpGet request = new HttpGet(SERVICE_PATH + "video/find/random/list");
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
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return list;
    }

    public YoutubeVideo getRandomVideo() {
        YoutubeVideo youtubeVideo = null;
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        try {

            HttpGet request = new HttpGet(SERVICE_PATH + "video/find/random");
            request.addHeader("charset", "UTF-8");
            // System.out.println("Random one video call:");
            HttpResponse response = CLIENT.execute(request);
            response.addHeader("content-type", "application/json;charset=UTF-8");

            HttpEntity entity = response.getEntity();
            ObjectMapper mapper = new ObjectMapper();
            youtubeVideo = mapper.readValue((EntityUtils.toString(entity)), YoutubeVideo.class);
        } catch (IOException | ParseException ex) {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return youtubeVideo;

    }

    public List<YoutubeVideo> getFullTextSearchResult(String query) {        
      //  System.out.println("#Calling  getFullTextSearchResult " + query);        
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        List<YoutubeVideo> list = null;
        try {

            HttpGet request = new HttpGet(SERVICE_PATH + "video/fetch/full/" + query.replace(" ", "%20"));
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
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        //   System.out.println("List size is ## " + list.size());
        return list;
    }

    public List<YoutubeVideo> getTopVideoList() {
       // System.out.println("Calling  getTopVideoList ");
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        List<YoutubeVideo> list = null;
        try {
            HttpGet request = new HttpGet(SERVICE_PATH + "video/find/top");
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
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                CLIENT.close();
            } catch (IOException ex1) {
                java.util.logging.Logger.getLogger(RESTClientBean.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return list;
    }

    public List<YoutubeVideo> getLastSessionList() {       
        return lastSessionList;
    }

    public void initRandom() {
        oneRandomVideo = this.getRandomVideo();
    }

    public YoutubeVideo getOneRandomVideo() {
        return oneRandomVideo;
    }

    public void setOneRandomVideo(YoutubeVideo oneRandomVideo) {
        this.oneRandomVideo = oneRandomVideo;
    }

}
