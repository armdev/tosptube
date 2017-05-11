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
@EqualsAndHashCode(exclude = {"userId", "email"})
@ToString
public class UserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String profileLink;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String username;
    private Date registeredDate;
    private Integer status;
    private Integer role;
    private String ip;

    

}
