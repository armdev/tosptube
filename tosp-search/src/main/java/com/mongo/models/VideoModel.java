package com.mongo.models;

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

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoModel implements Serializable {

    private static final long serialVersionUID = -3827360841749573458L;

    private String resourceId;
    private String title;
    private String videoId;
    private String thumbnailUrl;
    private String channelTitle;
    private String description;
    private String channelId;
    private List<Comment> commentList = new ArrayList<>();
    private int viewCount;
    private String searchKey;
    private String lastViewed;
    private String datePublished;
    private Integer count;

}
