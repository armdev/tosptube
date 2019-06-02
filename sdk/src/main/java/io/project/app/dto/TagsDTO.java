package io.project.app.dto;

import io.project.app.domain.Tag;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author armen
 */
@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class TagsDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private List<Tag> tagList = new ArrayList<>();
  
}
