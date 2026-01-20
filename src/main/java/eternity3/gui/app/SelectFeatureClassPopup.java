package eternity3.gui.app;

import eternity3.Eternity;
import eternity3.app.features.Feature;

import javax.swing.*;

public class SelectFeatureClassPopup extends JDialog {
    private JList<Class<Feature>> list;

    public SelectFeatureClassPopup(){
        this.list = new JList<>();
        this.list.setListData(Eternity.FEATURE_CLASSES);
        this.setContentPane(list);
        this.setModal(true);
        this.setAlwaysOnTop(true);
        this.pack();
        this.list.addListSelectionListener((e)->{
            this.dispose();
        });
    }

    public Class<Feature> select(){
        this.setVisible(true);
        return list.getSelectedValue();
    }




}
