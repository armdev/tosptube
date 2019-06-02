package io.project.app.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "user")
public class User implements Serializable {

    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String mobileno;   
    private String gender;
    private int age;
    
    
    private Date registerDate;
    private Integer status;

    public User(String firstname, String lastname, String email, String password, String mobileno, String gender, int age, Date registerDate, Integer status) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.mobileno = mobileno;
        this.gender = gender;
        this.age = age;       
        this.registerDate = registerDate;
        this.status = status;
    }
    
    
}
