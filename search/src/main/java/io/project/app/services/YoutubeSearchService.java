package io.project.app.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.model.SearchListResponse;
import io.project.app.domain.YoutubeVideo;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Service
@Component
@Slf4j
@AllArgsConstructor
public class YoutubeSearchService {

    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    //private static final String PROPERTIES_FILENAME = "GOOGLEYOUTUBE";
    private static String GOOGLE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";
    private static final String YOUTUBE_SEARCH_TYPE = "video";
    private static final String YOUTUBE_SEARCH_FIELDS = "items(id/kind,id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)";
    private static final String YOUTUBE_API_APPLICATION = "google-youtube-api-search";
    private static final String YOUTUBE_APIKEY_ENV = "YOUTUBE_APIKEY";

    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;

    private static YouTube youtube;

    // private static final ResourceBundle propertiesBundle;
    @PostConstruct
    public void init() {
        //propertiesBundle = ResourceBundle.getBundle(PROPERTIES_FILENAME);

        // This object is used to make YouTube Data API requests. The last
        // argument is required, but since we don't need anything
        // initialized when the HttpRequest is initialized, we override
        // the interface and provide a no-op function.
        youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, (HttpRequest request) -> {
            // intentionally left empty
        }).setApplicationName(YOUTUBE_API_APPLICATION).build();
    }

    /**
     * Makes YouTube search into video library with given keywords.
     *
     * @param searchQuery
     * @param maxSearch
     * @return
     */
    public List<YoutubeVideo> youTubeSearch(String searchQuery, int maxSearch) {
        log.info("Starting YouTube search... " + searchQuery);

        List<YoutubeVideo> rvalue = new ArrayList<>();

        try {

            if (youtube != null) {

                log.info("Define the API request for retrieving search results");
                YouTube.Search.List search = youtube.search().list("id,snippet");
                search.setKey("AIzaSyA9xclW6n912Diq3HdX4HeIWvWKPIwN_O0");
                search.setQ(searchQuery);
                search.setOrder("viewcount");//"rating"                
                search.setType(YOUTUBE_SEARCH_TYPE);
                search.setFields("items(id/kind,id/videoId,id/channelId,id/playlistId,snippet/title,snippet/publishedAt,snippet/description,snippet/channelTitle, snippet/channelId, snippet/thumbnails/default/url)");
                search.setMaxResults(25L);
                log.info("execute");
                SearchListResponse searchResponse = search.execute();
                log.info("execute 1 ");
                List<SearchResult> searchResultList = searchResponse.getItems();
                log.info("Result size " + searchResultList.size());
                if (searchResultList.size() > 0) {
                    log.info("Fetch videos ");
                    for (SearchResult r : searchResultList) {
                        log.info("Create objects ");
//                        log.info("1 " + r.getId().getVideoId());
//                        log.info("2 " +  r.getSnippet().getTitle());
//                        log.info("3 " +   r.getSnippet().getThumbnails().getDefault().getUrl());
//                        log.info("4 " +   r.getSnippet().getDescription(), r.getSnippet().getChannelTitle());
//                        log.info("5 " +    r.getSnippet().getChannelId());
//                        log.info("6 " +    r.getSnippet().getPublishedAt().toString());

                        YoutubeVideo item = new YoutubeVideo(
                                "https://www.youtube.com/watch?v=" + r.getId().getVideoId(),
                                r.getId().getVideoId(),
                                r.getSnippet().getTitle(),
                                r.getSnippet().getThumbnails().getDefault().getUrl(),
                                r.getSnippet().getDescription(), r.getSnippet().getChannelTitle(),
                                r.getSnippet().getChannelId(),
                                searchQuery,
                                r.getSnippet().getPublishedAt() != null ? r.getSnippet().getPublishedAt().toString() : "");

                        log.info("Add to list");
                        rvalue.add(item);
                    }
                } else {
                    log.info("No search results got from YouTube API");
                }

            } else {
                log.warn("YouTube API not initialized correctly!");
            }

        } catch (GoogleJsonResponseException e) {
            log.warn("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            log.warn(e.getMessage() + e.getCause() + "There was an IO error: " + " : ");
        } catch (NumberFormatException t) {
            log.warn("Severe errors!", t);
        }
        return rvalue;
    }
}
