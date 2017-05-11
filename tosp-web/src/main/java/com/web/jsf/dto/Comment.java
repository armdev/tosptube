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
@EqualsAndHashCode(exclude = {"resourceId", "userId"})
@ToString
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;   

    private String resourceId;   
    private String comment;
    private String userId;
    private String username;
    private Date insertedDate;  

}
