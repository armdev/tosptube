package io.project.app.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Document(collection = "youtube")
@JsonIgnoreProperties(ignoreUnknown = true)
public class YoutubeVideo implements Serializable {

    private static final long serialVersionUID = -3827360841749573458L;

    @Id
    private String id;  
    private String url;    
    private String videoId;
    @TextIndexed(weight=2)
    private String title;
    private String thumbnailUrl;
    @TextIndexed(weight=2)
    private String description;        
    private String channelTitle;   
    private String channelId;   
    @TextIndexed(weight=2)
    private String searchKey;    
    private String datePublished;

    public YoutubeVideo(String url, String videoId, String title, String thumbnailUrl, String description, String channelTitle, String channelId, String searchKey, String datePublished) {
        this.url = url;
        this.videoId = videoId;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.channelTitle = channelTitle;
        this.channelId = channelId;
        this.searchKey = searchKey;
        this.datePublished = datePublished;
    }
  
    
    

}
