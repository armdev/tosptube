package com.web.jsf.upload;

import com.google.api.client.util.Base64;
import com.web.jsf.dto.FileModel;
import com.web.jsf.handlers.SessionContext;
import com.web.jsf.utils.FacesContextUtil;
import com.web.rest.client.RESTVideoUploadBean;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "videoUploadBean")
@ViewScoped
public class VideoUploadBean implements Serializable {

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
    private Part uploadedFile = null;
    @Setter
    @Getter
    private FileModel fileModel = null;

    public VideoUploadBean() {

    }

    @PostConstruct
    public void init() {
        fileModel = new FileModel();
    }

    public String doAction() throws IOException {
        if (uploadedFile == null) {
            System.out.println("File is null ");
            return null;
        }
        if (uploadedFile != null) {
            long size = uploadedFile.getSize();
            long maxSize = 152956040;
            if (size > maxSize) {
                System.out.println("file size is too big");
                FacesContextUtil.facesError(bundle.getString("fileTooBig"));
                return null;
            }
            String content = uploadedFile.getContentType();
            if (content.equalsIgnoreCase("video/mp4")) {
                InputStream stream = uploadedFile.getInputStream();
                byte[] contentBytes = new byte[(int) size];
                stream.read(contentBytes);
                String base64String = Base64.encodeBase64String(contentBytes);
                fileModel.setContent(base64String);
                fileModel.setFilesize(size);
                fileModel.setMimetype(content);
                fileModel.setTitle(uploadedFile.getSubmittedFileName().replaceAll("\\s+", "-"));
                fileModel.setUserId(sessionContext.getUser().getUserId());
                videoUploadRest.saveVideo(fileModel);
            } else {
                System.out.println("no ta mp4 fail");
            }
        }
        return "videolist";

    }

    public void asyncUpload(final FileModel fileModel) {
        Runnable task = () -> {
            try {
                videoUploadRest.saveVideo(fileModel);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };
        new Thread(task, "AsynUpload").start();
    }

}
