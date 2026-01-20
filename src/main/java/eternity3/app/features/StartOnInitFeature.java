package eternity3.app.features;

import com.fasterxml.jackson.annotation.JsonTypeName;
import eternity3.app.App;

@JsonTypeName("StartOnInitFeature")
public class StartOnInitFeature extends FeatureImpl{
    @Override
    public void onInit(App app) {
        app.start();
    }
}
