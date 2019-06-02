package io.project.app.services;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
//import com.google.api.services.samples.youtube.cmdline.Auth;
//import com.google.api.services.samples.youtube.cmdline.data.Search;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import io.project.app.domain.YoutubeVideo;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import lombok.Getter;
import lombok.Setter;


public class YoutubeSearch implements Serializable {

    private static final String PROPERTIES_FILENAME = "youtube.properties";

    private static final long NUMBER_OF_VIDEOS_RETURNED = 50;

    private static YouTube youtube;
  

   
    @Setter
    @Getter
    private String searchQuery;

    private final List<YoutubeVideo> youtubeVideo = new ArrayList<>();

    public YoutubeSearch() {
        //System.out.println("FrontBean called");
    }

    public String doSearch() {
        if (searchQuery == null) {
            return null;
        }
        Properties properties = new Properties();
        try {
            InputStream in = YoutubeSearch.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);
        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }
        try {

//            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, (HttpRequest request) -> {
//            }).setApplicationName("youtube-cmdline-search-sample").build();
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
                //restClient.saveVideo(video);
            }
        }
       // restClient.saveSearchKey(searchQuery);

    }

   

  
}
