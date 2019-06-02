package io.project.app.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Document(collection = "searchkey")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchKey implements Serializable {

    private static final long serialVersionUID = 2099119595418807689L;    
    
    @Id
    private String id;   
    @Indexed
    private String searchKey;
    private Integer count;    

}
