package eternity3.gui;

import javax.swing.*;
import java.awt.*;

public class EternityMainPanel extends JPanel {
    public EternityMainPanel(){
        this.setLayout(new BorderLayout());
        //tabbedPane in middle
        AppTabbedPane tabs = new AppTabbedPane();
        this.add(tabs, BorderLayout.CENTER);


        //app list
        AppListPanel alp = new AppListPanel();
        alp.getAppList().addListSelectionListener((e)->{
            var selected = alp.getAppList().getSelectedValue();
            if(selected!=null) tabs.selectApp(selected);
        });
        this.add(alp, BorderLayout.WEST);
        /*
        AppList list = new AppList();
        list.addListSelectionListener((e)->{
            tabs.selectApp(list.getSelectedValue());
        });
        this.add(list, BorderLayout.WEST);*/


    }
}
