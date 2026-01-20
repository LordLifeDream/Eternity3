package eternity3.gui;

import eternity3.AppManager;
import eternity3.app.App;

import javax.swing.*;
import java.awt.*;

public class AppListPanel extends JPanel {
    private AppList appList;
    private JPanel actions;
    public AppListPanel(){
        this.appList = new AppList();
        this.setLayout(new BorderLayout());
        JScrollPane wrap = new JScrollPane(appList);
        this.add(wrap, BorderLayout.CENTER);
        //actions
        this.actions = new JPanel();
        fillActions();
        this.add(actions, BorderLayout.SOUTH);
    }

    public AppList getAppList(){
        return this.appList;
    }

    private void fillActions(){
        JButton remove = new JButton("delete");
        JButton add = new JButton("new...");
        add.addActionListener((e)->{
            String name = JOptionPane.showInputDialog("Please enter the app's name");
            App app = new App(name, "./example", "java -jar example.jar");
            AppManager.addApp(app);
            appList.rebuild();
            remove.setEnabled(false);
        });
        actions.add(add);
        //remove selected button
        remove.setEnabled(false);
        appList.addListSelectionListener((e)-> remove.setEnabled(true));
        remove.addActionListener((e)->{
            AppManager.removeApp(appList.getSelectedValue());
            appList.rebuild();
            remove.setEnabled(false);
        });
        actions.add(remove);

    }

}
