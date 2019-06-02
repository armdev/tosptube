package io.project.app.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "answers")
public class Answer implements Serializable {

    @Id
    private String id;
    @Indexed
    private String userId;
    private String username;
    private String questionId;
    private String answer;
    private Date publishDate;
    private Date updateDate;
    private Integer status;

}
