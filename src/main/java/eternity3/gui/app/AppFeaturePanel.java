package eternity3.gui.app;

import eternity3.app.App;

import javax.swing.*;
import java.awt.*;

public class AppFeaturePanel extends JPanel {
    private App app;
    private AppFeatureList featureList;
    public AppFeaturePanel(App app){
        this.app = app;
        this.setLayout(new BorderLayout());
        JButton remove = new JButton("remove");
        JButton reconfigure = new JButton("reconfigure");
        this.featureList = new AppFeatureList(app);
        featureList.addListSelectionListener((e)->{
            remove.setEnabled(true);
            reconfigure.setEnabled(true);
        });
        JScrollPane wrap = new JScrollPane(featureList);
        this.add(wrap, BorderLayout.CENTER);
        //buttons
        JPanel buttons = new JPanel();
        //remove
        remove.addActionListener((e)->{
            app.removeFeature(featureList.getSelectedValue());
            remove.setEnabled(false);
            reconfigure.setEnabled(false);
        });
        remove.setEnabled(false);
        buttons.add(remove);
        //reconfigure
        reconfigure.setEnabled(false);
        reconfigure.addActionListener((e)->{
            featureList.getSelectedValue().guidedInit(true);
        });
        buttons.add(reconfigure);
        //add
        this.add(buttons, BorderLayout.SOUTH);
    }
}
