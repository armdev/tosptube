package com.web.jsf.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"fileId", "userId"})
@ToString
public class FileModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileId;
    private String userId;
    private String title;
    private String mimetype;
    private Long filesize;
    private String filepath;  
    private String content;
    private Date insertedDate;

 

}
