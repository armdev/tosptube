package com.web.jsf.video.view;

import com.web.jsf.dto.YoutubeVideo;
import com.web.jsf.utils.FacesContextUtil;
import com.web.rest.client.RESTClientBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javaslang.control.Try;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import lombok.Setter;

@ManagedBean(name = "savedListBean")
@ViewScoped
public class SavedListBean implements Serializable {

    private static final long serialVersionUID = -8838824829643009619L;

    @ManagedProperty(value = "#{restClient}")
    @Setter
    private RESTClientBean restClient;

    private List<YoutubeVideo> generalList = new ArrayList<>();

    public SavedListBean() {

    }

    @PostConstruct
    public void init() {
        Try<List> fetch = this.fetchList();
        if (fetch.isSuccess()) {
            generalList = fetch.get();
        }

    }

    public Try<List> fetchList() {
        return Try.of(() -> restClient.getLastSessionList());
    }

    public Try<List> fetchSubList(int a, int b) {
        return Try.of(() -> generalList.subList(a, b));
    }

    public List<YoutubeVideo> sublistFetcher(int a, int b) {
        Try<List> sub = fetchSubList(a, b);
        if (sub.isSuccess()) {
            return sub.get();
        }
        return null;
    }

     public List<YoutubeVideo> getFirstRowList() {
        if (!FacesContextUtil.getFacesContext().getRenderResponse()) {
            return null;
        }
        return sublistFetcher(0, 6);
    }

    public List<YoutubeVideo> getSecondRowList() {
        if (!FacesContextUtil.getFacesContext().getRenderResponse()) {
            return null;
        }
        return sublistFetcher(6, 12);
    }

    public List<YoutubeVideo> getNext3RowList() {
        if (!FacesContextUtil.getFacesContext().getRenderResponse()) {
            return null;
        }
        return sublistFetcher(12, 18);
    }

    public List<YoutubeVideo> getNext4RowList() {
        if (!FacesContextUtil.getFacesContext().getRenderResponse()) {
            return null;
        }
        return sublistFetcher(18, 24);
    }

    public List<YoutubeVideo> getNext5RowList() {
        if (!FacesContextUtil.getFacesContext().getRenderResponse()) {
            return null;
        }
        return sublistFetcher(24, 30);
    }

    public List<YoutubeVideo> getNext6RowList() {
        if (!FacesContextUtil.getFacesContext().getRenderResponse()) {
            return null;
        }
        return sublistFetcher(30, 36);
    }

    public List<YoutubeVideo> getNext7RowList() {
        if (!FacesContextUtil.getFacesContext().getRenderResponse()) {
            return null;
        }
        return sublistFetcher(36, 42);
    }

    public List<YoutubeVideo> getNext8RowList() {
        if (!FacesContextUtil.getFacesContext().getRenderResponse()) {
            return null;
        }
        return sublistFetcher(42, 48);
    }

    public List<YoutubeVideo> getNext9RowList() {
        if (!FacesContextUtil.getFacesContext().getRenderResponse()) {
            return null;
        }
        return sublistFetcher(48, 54);
    }

    public List<YoutubeVideo> getNext10RowList() {
        if (!FacesContextUtil.getFacesContext().getRenderResponse()) {
            return null;
        }
        return sublistFetcher(54, 60);
    }
}
