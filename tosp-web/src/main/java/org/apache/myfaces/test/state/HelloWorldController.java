
package org.apache.myfaces.test.state;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * A typical simple backing bean, that is backed to <code>helloWorld.xhtml</code>
 */
@ManagedBean(name = "helloWorld")
@RequestScoped
public class HelloWorldController
{

    //properties
    private String name;
    
    /**
     * default empty constructor
     */
    public HelloWorldController()
    {
    }

    /**
     * Method that is backed to a submit button of a form.
     * @return 
     */
    public String send()
    {
        //do real logic, return a string which will be used for the navigation system of JSF
        return null; //"page2.xhtml";
    }

    //-------------------getter & setter

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public void calculateStateSize()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Object state = context.getApplication().getStateManager().saveView(context);
        
        if (state != null)
        {
            try
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();  
                try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                    oos.writeObject(state);
                }
                context.getAttributes().put("stateSize", 
                        Integer.toString(baos.toByteArray().length));
            }
            catch (IOException e)
            {
                //no op
                context.getAttributes().put("stateSize", "ERROR");
            }
        }
        else
        {
            context.getAttributes().put("stateSize", "0");
        }
    }
}
