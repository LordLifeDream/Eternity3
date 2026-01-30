package eternity3.app.features;

import com.fasterxml.jackson.annotation.JsonTypeName;
import eternity3.app.App;

@JsonTypeName("PullOnInitFeature")
public class PullOnInitFeature extends FeatureImpl{
    @Override
    public void onInit(App app) {
        //TODO ORDERING OF FEATURE CREATION, THIS !!NEEDS!! TO BE CREATED ""AFTER!! THE GIT
        GitFeature f = app.getFeature(GitFeature.class);
        f.pull();
    }
}
