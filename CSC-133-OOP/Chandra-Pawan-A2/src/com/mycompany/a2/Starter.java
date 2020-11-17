package com.mycompany.a2;


import static com.codename1.ui.CN.*;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Toolbar;


public class Starter
{
    private Form current;
    private Resources theme;

    public void init(Object context)
    {
        updateNetworkThreadCount(2);
        theme = UIManager.initFirstTheme("/theme");
        Toolbar.setGlobalToolbar(true);
    }

    public void start()
    {
        if(current != null)
        {
            current.show();
            return;
        }
        new Game();
    }

    public void stop()
    {
        current = getCurrentForm();
        if(current instanceof Dialog)
        {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy()
    {

    }

}
