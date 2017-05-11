package com.web.jsf.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author armenar
 */
public class FacesContextUtil {

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static ExternalContext getFacesExternalContext() {
        return getFacesContext().getExternalContext();
    }

    public static void addMessage(String clientId, FacesMessage message) {
        getFacesContext().addMessage(clientId, message);
    }

    public static void facesError(String message) {
        getFacesContext().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    public static String getRequestParameter(String paramName) {
        String returnValue = null;
        if (getFacesExternalContext().getRequestParameterMap().containsKey(paramName)) {
            returnValue = (getFacesExternalContext().getRequestParameterMap().get(paramName));
        }
        return returnValue;
    }
}
