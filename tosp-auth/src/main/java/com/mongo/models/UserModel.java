package com.mongo.models;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class UserModel implements Serializable {

    private static final long serialVersionUID = -6516997653347077161L;   
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
    private String token;

   
   

}
