package com.web.jsf.auth;

import com.web.jsf.utils.FacesContextUtil;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Armen Arzumanyan
 */
@ManagedBean(name = "userLogout")
@RequestScoped
public class UserLogout implements Serializable {

    private static final long serialVersionUID = 1L;

    public UserLogout() {
        // System.out.println("UserLogout called");
    }

    public String doLogout() {       
        FacesContextUtil.getFacesExternalContext().getSessionMap().remove("sessionContext");
        HttpSession session = (HttpSession) FacesContextUtil.getFacesExternalContext().getSession(true);
        session.invalidate();
        HttpServletResponse response = (HttpServletResponse) FacesContextUtil.getFacesExternalContext().getResponse();
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "logout";
    }
}
