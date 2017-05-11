package com.web.jsf.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author armenar
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"videoId", "title"})
@ToString
public class YoutubeVideo implements Serializable{

    private static final long serialVersionUID = 1L;

    private String videoId;
    private String title;
    private String thumbnailUrl;
    private String channelTitle;
    private String description;
    private String channelId;
    private List<Comment> commentList = new ArrayList<>();
    private int viewCount;
    private String searchKey;
    private String lastViewed;
    private String resourceId;
    private String datePublished;
    private Integer count;   

}
