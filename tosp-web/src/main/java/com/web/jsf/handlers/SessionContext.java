package com.web.jsf.handlers;

import com.web.jsf.dto.UserModel;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Armen Arzumanyan
 */
@ManagedBean(name = "sessionContext")
@SessionScoped
public class SessionContext implements Serializable {

    private static final long serialVersionUID = 1L;

    @Setter
    @Getter
    private UserModel user;

    public SessionContext() {

    }

    @PostConstruct
    public void init() {
        user = new UserModel();
    }

}
