package com.web.jsf.auth;

import com.web.jsf.dto.UserModel;
import com.web.jsf.utils.FacesContextUtil;
import com.web.rest.client.RESTAuthBean;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;

@ManagedBean(name = "userRegister")
@ViewScoped
public class UserRegister implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{restAuthBean}")
    @Setter
    private RESTAuthBean restAuthBean;
    @ManagedProperty("#{i18n}")
    @Setter
    private ResourceBundle bundle = null;
    @Setter
    @Getter
    private UserModel userModel;
    @Setter
    @Getter
    private boolean agree;

    public UserRegister() {
       // System.out.println("UserRegister called");
    }

    @PostConstruct
    public void init() {
        userModel = new UserModel();
    }

    public String register() {
        UserModel checkEmail = restAuthBean.getUserByEmail(userModel.getEmail());
        //System.out.println("checkEmail   " + checkEmail);
        if (checkEmail != null && checkEmail.getEmail() != null) {
           // System.out.println("first case ");
            FacesMessage msg = new FacesMessage(bundle.getString("emailbusy"), bundle.getString("emailbusy"));
            FacesContextUtil.addMessage(null, msg);
            return null;
        }
        UserModel checkUserName = restAuthBean.getUserByEmail(userModel.getEmail());
       // System.out.println("checkUserName   " + checkUserName);
        if (checkUserName != null && checkUserName.getUsername() != null) {
           // System.out.println("secopnd case ");
            FacesMessage msg = new FacesMessage(bundle.getString("usernameused"), bundle.getString("usernameused"));
            FacesContextUtil.addMessage(null, msg);
            return null;
        }
        //System.out.println("Save success called");
        restAuthBean.saveUser(userModel);
        return "success";

    }

}
