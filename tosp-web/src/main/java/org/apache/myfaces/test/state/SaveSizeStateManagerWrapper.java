
package org.apache.myfaces.test.state;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.faces.application.StateManager;
import javax.faces.application.StateManagerWrapper;
import javax.faces.context.FacesContext;


public class SaveSizeStateManagerWrapper extends StateManagerWrapper
{

    private final StateManager _delegate;
    
    public SaveSizeStateManagerWrapper(StateManager delegate)
    {
        this._delegate = delegate;
    }
    
    @Override
    public StateManager getWrapped() {
        return _delegate;
    }

    @Override
    public Object saveView(FacesContext context)
    {
        Object state = super.saveView(context);
        
        if (state != null)
        {
            try
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();  
                try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                    oos.writeObject(state);
                }
               //System.out.println("State size: " + Integer.toString(baos.toByteArray().length) + " bytes");
            }
            catch (IOException e)
            {
                //no op
                //System.out.println("State size: ERROR");
            }
        }
        else
        {
            //System.out.println("State size: 0 bytes");
        }
        return state;
    }
}
