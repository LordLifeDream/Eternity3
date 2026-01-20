package eternity3.gui;

import eternity3.AppManager;
import eternity3.app.App;

import javax.swing.*;
import java.awt.*;

public class AppList extends JList<App> {
    public AppList(){
        rebuild();
        //this.setMinimumSize(new Dimension(500, 1000));
        //this.setPreferredSize(new Dimension(150, 1000));
    }

    public void rebuild(){
        this.setListData(AppManager.getApps().toArray(new App[0]));
    }
}
