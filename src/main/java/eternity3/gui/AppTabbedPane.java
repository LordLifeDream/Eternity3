package eternity3.gui;

import eternity3.app.App;
import eternity3.gui.app.AppPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class AppTabbedPane extends JTabbedPane {
    private HashMap<App, Component> appComponents = new HashMap<>();

    public void selectApp(App app){
        if(!appComponents.containsKey(app)){
            //Component p = new JLabel(app.getName());
            AppPanel p = new AppPanel(app);
            appComponents.put(app, p);
            this.addTab(app.getName(), p);
        }
        this.setSelectedComponent(appComponents.get(app));
    }
}
