package eternity3.app;

public interface AppListener {
    void onStart(App app);
    void onStop(App app, int exitCode);
    void onBuilder(App app, ProcessBuilder b);
    void onInit(App app);
    void onFeatureChange(App app);
}
