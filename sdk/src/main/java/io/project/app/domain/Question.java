package io.project.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "questions")
public class Question implements Serializable {

    @Id
    private String id; 
    @Indexed
    private String userId;
    private String username;
    @TextIndexed(weight=2)
    private String title;
    @TextIndexed(weight=2)
    private String question;
    private Date publishDate;
    private Date updateDate;
    private Integer status;
    private List<Answer> answers = new ArrayList<>(); 
    @TextIndexed(weight=2)
    private List<String> tags = new ArrayList<>(); 

}
