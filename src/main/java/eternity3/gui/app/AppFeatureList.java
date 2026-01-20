package eternity3.gui.app;

import eternity3.app.App;
import eternity3.app.FeatureChangeListener;
import eternity3.app.features.Feature;

import javax.swing.*;

public class AppFeatureList extends JList<Feature> {
    private App app;
    public AppFeatureList(App app){
        this.app = app;
        rebuild();
        app.addListener(new FeatureChangeListener() {
            @Override
            public void onFeatureChange(App app) {
                rebuild();
            }
        });
    }

    public void rebuild(){
        this.setListData(app.getFeatures().toArray(new Feature[0]));
    }
}
