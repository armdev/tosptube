package com.web.jsf.search;

import com.web.jsf.dto.YoutubeVideo;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
//import com.google.api.services.samples.youtube.cmdline.Auth;
//import com.google.api.services.samples.youtube.cmdline.data.Search;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.web.jsf.utils.FacesContextUtil;
import com.web.rest.client.RESTClientBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javaslang.control.Try;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author armenar
 */
@ManagedBean(name = "frontBean")
@SessionScoped
public class FrontBean implements Serializable {

    private static final String PROPERTIES_FILENAME = "youtube.properties";

    private static final long NUMBER_OF_VIDEOS_RETURNED = 50;

    private static YouTube youtube;
    // https://developers.google.com/youtube/v3/docs/search/list

    @ManagedProperty(value = "#{restClient}")
    @Setter
    private RESTClientBean restClient;
    @Setter
    @Getter
    private String searchQuery;

    private final List<YoutubeVideo> youtubeVideo = new ArrayList<>();

    public FrontBean() {
        //System.out.println("FrontBean called");
    }

    public String doSearch() {
        if (searchQuery == null) {
            return null;
        }
        Properties properties = new Properties();
        try {
            InputStream in = FrontBean.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);
        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }
        try {

            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, (HttpRequest request) -> {
            }).setApplicationName("youtube-cmdline-search-sample").build();
            // Prompt the user to enter a query term.
            String queryTerm = searchQuery;
            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");
            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);
            search.setQ(queryTerm);
            search.setOrder("viewcount");//rating 
            // search.setOrder("rating");//rating 
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,id/channelId,id/playlistId,snippet/title,snippet/publishedAt,snippet/description,snippet/channelTitle, snippet/channelId, snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                prettyPrint(searchResultList.iterator(), queryTerm);
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
        }
        return "index";
    }

    private void prettyPrint(Iterator<SearchResult> iteratorSearchResults, String query) {
        if (searchQuery == null) {
            return;
        }
        youtubeVideo.clear();
        if (!iteratorSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }
        //https://www.thewebtaylor.com/articles/how-to-get-a-youtube-videos-thumbnail-image-in-high-quality

        YoutubeVideo video = null;
        // System.out.println("prettyPrint iteratorSearchResults has next ");
        while (iteratorSearchResults.hasNext()) {
            // System.out.println("prettyPrint  next ");
            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();

            if (rId.getKind().equals("youtube#video")) {
                // System.out.println("prettyPrint  kind ");
                video = new YoutubeVideo();
                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
                video.setVideoId(rId.getVideoId());
                video.setTitle(singleVideo.getSnippet().getTitle());
                video.setDescription(singleVideo.getSnippet().getDescription());
                Date publishedDate = new Date(singleVideo.getSnippet().getPublishedAt().getValue() * 1000);
                video.setDatePublished(singleVideo.getSnippet().getPublishedAt().toString());
                video.setChannelId(singleVideo.getSnippet().getChannelId());
                video.setChannelTitle(singleVideo.getSnippet().getChannelTitle());
                //video.setThumbnailUrl(thumbnail.getUrl());
                video.setThumbnailUrl("https://i.ytimg.com/vi/" + rId.getVideoId() + "/hqdefault.jpg");
                //https://i.ytimg.com/vi/uS2bomiDOko/hqdefault.jpg
                //http://img.youtube.com/vi/<insert-youtube-video-id-here>/hqdefault.jpg
                video.setSearchKey(searchQuery);
                // System.out.println("add video " + video);
                youtubeVideo.add(0, video);
                restClient.saveVideo(video);
            }
        }
        restClient.saveSearchKey(searchQuery);

    }

    public Try<List> fetchSubList(int a, int b) {
        //Collections.reverse(youtubeVideo);
        return Try.of(() -> youtubeVideo.subList(a, b));
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
