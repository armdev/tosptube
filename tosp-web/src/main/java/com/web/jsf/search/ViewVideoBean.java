package com.web.jsf.search;

import com.web.jsf.dto.YoutubeVideo;
import com.web.jsf.dto.Comment;
import com.web.jsf.handlers.SessionContext;
import com.web.jsf.utils.FacesContextUtil;
import com.web.rest.client.HealthCheckClient;
import com.web.rest.client.RESTClientBean;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "viewVideoBean")
@SessionScoped
public class ViewVideoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{restClient}")
    @Setter
    private RESTClientBean restClient;
    @ManagedProperty(value = "#{healthCheckClient}")
    @Setter
    private HealthCheckClient healthCheckClient;
    @ManagedProperty("#{sessionContext}")
    @Setter
    private SessionContext sessionContext = null;
    @Setter
    @Getter
    private String videoId;
    @Setter
    @Getter
    private YoutubeVideo youtubeVideo = null;
    @Setter
    @Getter
    private Comment comment;
    @Setter    
    private List<YoutubeVideo> videoList = new ArrayList<>();

    public ViewVideoBean() {
        System.out.println("ViewVideoBean called");
    }

    @PostConstruct
    public void init() {
        comment = new Comment();
        youtubeVideo = new YoutubeVideo();
        videoId = ((FacesContextUtil.getRequestParameter("videoId")));

        if (youtubeVideo != null && videoId != null) {
            restClient.updateCount(videoId);
        }
        youtubeVideo = restClient.getVideoById(videoId);
    }

    public void initRandom() {
        if (healthCheckClient.isBackendHealth()) {
            youtubeVideo = restClient.getRandomVideo();
            if (youtubeVideo.getVideoId() != null) {
                restClient.updateCount(youtubeVideo.getVideoId());
            }
            videoList = restClient.getVideoListByQuery(youtubeVideo.getSearchKey() != null ? youtubeVideo.getSearchKey() : "Armenia");
        }
    }

    public List<YoutubeVideo> getVideoList() {
        try {
            if (healthCheckClient.isBackendHealth()) {
                videoList = restClient.getVideoListByQuery(youtubeVideo.getSearchKey() != null ? youtubeVideo.getSearchKey() : "Armenia");
            }
        } catch (Exception e) {
            try {
                FacesContextUtil.getFacesExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.jsf");
            } catch (IOException ex) {
                Logger.getLogger(ViewVideoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return videoList;
    }

    public void addComment() {
        try {
            comment.setResourceId(youtubeVideo.getVideoId());
            comment.setUsername(sessionContext.getUser().getUsername());
            comment.setUserId(sessionContext.getUser().getUserId());
            restClient.addComment(comment);
        } catch (Exception e) {
        }
        try {
            FacesContextUtil.getFacesExternalContext().redirect("single.jsf?videoId=" + youtubeVideo.getVideoId());
        } catch (IOException ex) {
            Logger.getLogger(ViewVideoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
