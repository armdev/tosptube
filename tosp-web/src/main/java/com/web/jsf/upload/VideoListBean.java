package com.web.jsf.upload;

import com.web.jsf.dto.FileModel;
import com.web.jsf.handlers.SessionContext;
import com.web.rest.client.RESTVideoUploadBean;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "uploadedVideoList")
@ViewScoped
public class VideoListBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty("#{i18n}")
    @Setter
    @Getter
    private ResourceBundle bundle = null;
    @ManagedProperty("#{sessionContext}")
    @Setter
    private SessionContext sessionContext = null;
    @ManagedProperty("#{videoUploadRest}")
    @Setter
    private RESTVideoUploadBean videoUploadRest = null;
    @Setter
    @Getter
    private Part uploadedFile = null;
    @Setter
    @Getter
    private FileModel fileModel = null;

    public VideoListBean() {
    }

    @PostConstruct
    public void init() {
      //  System.out.println("VideoListBean called");
        fileModel = new FileModel();
    }

    public List<FileModel> getUploadedVideoList() {
        return videoUploadRest.getUserUploadedVideoList(sessionContext.getUser().getUserId());
    }

}
