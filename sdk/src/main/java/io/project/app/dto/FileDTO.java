package io.project.app.dto;

import java.io.Serializable;
import java.util.Date;
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
public class FileDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String id;    

    private String userId;
    
    private String fileName;
    
    private String contentType;
    
    private Long fileSize;
    
    private Date uploadDate;
    
    private String fileContent;
}
