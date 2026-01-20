package eternity3.app;

public abstract class FeatureChangeListener implements AppListener{

    @Override
    public void onStart(App app) {}

    @Override
    public void onStop(App app, int exitCode) {}

    @Override
    public void onBuilder(App app, ProcessBuilder b) {}

    @Override
    public void onInit(App app) {}

}
