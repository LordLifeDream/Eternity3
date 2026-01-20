package eternity3.commands;

import eternity3.AppManager;
import eternity3.app.features.Feature;

import java.util.List;

public class AddFeatureCommand extends Command{
    public AddFeatureCommand(){
        this.name = "AddFeature";
        this.aliases = new String[]{"addfeature"};
    }
    @Override
    void onExecute(List<String> params) {
        String app = params.get(1);
        String feature = params.get(2);
        if(!feature.startsWith("eternity3.app.features")) feature="eternity3.app.features."+feature;
        try {
            Class<Feature> clazz = (Class<Feature>) Class.forName(feature);
            Feature f = clazz.getConstructor().newInstance();
            f.guidedInit(false);
            AppManager.getApp(app).addFeature(f);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
