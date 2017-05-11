package com.web.jsf.upload;

import com.web.jsf.dto.FileModel;
import com.web.jsf.handlers.SessionContext;
import com.web.jsf.utils.FacesContextUtil;
import com.web.rest.client.RESTVideoUploadBean;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "showVideoBean")
@ViewScoped
public class ShowVideoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{i18n}")
    @Setter
    private ResourceBundle bundle = null;
    @ManagedProperty("#{sessionContext}")
    @Setter
    private SessionContext sessionContext = null;
    @ManagedProperty("#{videoUploadRest}")
    @Setter
    private RESTVideoUploadBean videoUploadRest = null;
    @Setter
    @Getter
    private FileModel fileModel = null;
    @Setter
    @Getter
    private String videoId;

    public ShowVideoBean() {
        //  System.out.println("ShowVideoBean called");
    }

    @PostConstruct
    public void init() {
        fileModel = new FileModel();
        videoId = ((FacesContextUtil.getRequestParameter("videoId")));
        fileModel = videoUploadRest.getVideoByIdAndUserId(videoId, sessionContext.getUser().getUserId());
    }

}
