package com.web.jsf.search;

import com.web.jsf.dto.YoutubeVideo;
import com.web.jsf.utils.FacesContextUtil;
import com.web.rest.client.RESTClientBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javaslang.control.Try;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "topKeyVideo")
@ViewScoped
public class TopKeyVideo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{restClient}")
    @Setter
    private RESTClientBean restClient;
    @Setter
    @Getter
    private String searchKey;
    @Setter
    @Getter
    private YoutubeVideo youtubeVideo;
    @Setter
    @Getter
    private List<YoutubeVideo> videoList = new ArrayList<>();

    public TopKeyVideo() {
        System.out.println("TopKeyVideo called");
    }

    @PostConstruct
    public void init() {
        youtubeVideo = new YoutubeVideo();
        searchKey = ((FacesContextUtil.getRequestParameter("searchKey")));
        if (searchKey != null) {
            Try<List> fetch = this.fetchList();
            if (fetch.isSuccess()) {
                videoList = fetch.get();
              //  Collections.reverse(videoList);
            }
        }
    }

    public Try<List> fetchList() {
        return Try.of(() -> restClient.getFullTextSearchResult(searchKey != null ? searchKey : "armenia"));
    }

    public Try<List> fetchSubList(int a, int b) {
        return Try.of(() -> videoList.subList(a, b));
    }

    public List<YoutubeVideo> sublistFetcher(int a, int b) {
        Try<List> sub = fetchSubList(a, b);
        if (sub.isSuccess()) {
            return sub.get();
        }
        return null;
    }

    public List<YoutubeVideo> getFirstRowList() {
        return sublistFetcher(0, 4);
    }

    public List<YoutubeVideo> getSecondRowList() {
        return sublistFetcher(4, 8);
    }

    public List<YoutubeVideo> getNext3RowList() {
        return sublistFetcher(8, 12);
    }

    public List<YoutubeVideo> getNext4RowList() {
        return sublistFetcher(12, 16);
    }

    public List<YoutubeVideo> getNext5RowList() {
        return sublistFetcher(16, 20);
    }

    public List<YoutubeVideo> getNext6RowList() {
        return sublistFetcher(20, 24);
    }

    public List<YoutubeVideo> getNext7RowList() {
        return sublistFetcher(24, 28);
    }

    public List<YoutubeVideo> getNext8RowList() {
        return sublistFetcher(28, 32);
    }

    public List<YoutubeVideo> getNext9RowList() {
        return sublistFetcher(32, 36);
    }

    public List<YoutubeVideo> getNext10RowList() {
        return sublistFetcher(36, 40);
    }

}
