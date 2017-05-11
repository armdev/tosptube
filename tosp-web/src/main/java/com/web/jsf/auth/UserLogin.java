package com.web.jsf.auth;

import com.web.jsf.dto.UserModel;
import com.web.jsf.handlers.SessionContext;
import com.web.jsf.utils.CommonConstants;
import com.web.jsf.utils.FacesContextUtil;
import com.web.rest.client.RESTAuthBean;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author armena
 */
@ManagedBean(name = "userLogin")
@RequestScoped
public class UserLogin {

    // ab -n 1000 -c 5 link
//    https://www.cyberciti.biz/tips/howto-performance-benchmarks-a-web-server.html
    @ManagedProperty(value = "#{restAuthBean}")
    @Setter
    private RESTAuthBean restAuthBean;
    @ManagedProperty("#{i18n}")
    @Setter
    private ResourceBundle bundle = null;
    @ManagedProperty("#{sessionContext}")
    @Setter
    private SessionContext sessionContext = null;
    @Setter
    @Getter
    private UserModel userModel;

    public UserLogin() {

    }

    @PostConstruct
    public void init() {
        userModel = new UserModel();
      //  System.out.println("UserLogin called");
    }

    public String loginUser() {
        HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContextUtil.getFacesExternalContext().getRequest();
        String ip = httpServletRequest.getRemoteAddr();
        userModel.setIp(ip);
        UserModel user = restAuthBean.userLogin(userModel);
        if (user != null && user.getStatus().equals(CommonConstants.ACTIVATED)) {
            sessionContext.setUser(user);
            return "profile";
        }
        if (user == null) {
            FacesMessage msg = new FacesMessage(bundle.getString("nouser"), bundle.getString("nouser"));
            FacesContextUtil.addMessage(null, msg);
            return null;
        }
        return null;
    }

}
