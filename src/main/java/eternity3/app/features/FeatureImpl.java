package eternity3.app.features;

import com.fasterxml.jackson.annotation.JsonTypeName;
import eternity3.app.App;

@JsonTypeName("FeatureImpl")
public class FeatureImpl extends Feature{
    @Override
    public void onStart(App app) {}

    @Override
    public void onStop(App app, int exitCode) {}

    @Override
    public void onBuilder(App app, ProcessBuilder b) {}

    @Override
    public void onInit(App app) {}

    @Override
    public void onFeatureChange(App app) {}

    @Override
    public void guidedInit(boolean ui) {
        System.out.println("this feature has nothing to init!");
    }
}
