package eternity3.app;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import eternity3.app.features.Feature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    @JsonIgnore
    private Process process;
    private String name;
    private String localLocation;
    private String runCmd;
    private ArrayList<Feature> features;

    @JsonIgnore
    private ArrayList<AppListener> listeners = new ArrayList<>();

    /* --INFO difference feature-listener--
     * Features:
     * features are saved into the app JSON and are intended to be used
     * like plug-ins or add-ons for apps
     * Listeners:
     * standard listeners. These are lost when the app is closed
     * note that Features are automatically added as listeners.
     */

    public App(String name, String localLocation, String runCmd){
        this(name, localLocation, runCmd, new ArrayList<>());
    }

    @JsonCreator
    public App(
            @JsonProperty("name")
            String name,
            @JsonProperty("localLocation")
            String localLocation,
            @JsonProperty("runCmd")
            String runCmd,
            @JsonProperty("features")
            ArrayList<Feature> features){
        this.name = name;
        this.localLocation = localLocation;
        this.runCmd = runCmd;
        this.features = features;
        listeners.addAll(this.features);

    }

    //this is only for the event at creation, called by AppManager
    public void init(){
        try {
            listeners.forEach((l) -> l.onInit(this));
        }catch (Exception e){
            System.err.println("init listener fail @ "+this.name+"!");
            e.printStackTrace();
        }
    }

    public void restart(){
        this.stop();
        this.start();
    }

    public <T extends Feature> T getFeature(Class<T> type){
        for(Feature f: features){
            if(f.getClass() == type){
                return (T) f;
            }
        }
        return null;
    }

    public Process getProcess() {
        return process;
    }

    public String getLocalLocation() {
        return localLocation;
    }

    public String getRunCmd() {
        return runCmd;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocalLocation(String localLocation) {
        this.localLocation = localLocation;
    }

    public void setRunCmd(String runCmd) {
        this.runCmd = runCmd;
    }

    public void addFeature(Feature f){
        this.features.add(f);
        this.listeners.add(f);
        this.listeners.forEach((l)->l.onFeatureChange(this));
    }

    public void removeFeature(Feature f){
        this.features.remove(f);
        this.listeners.remove(f);
        this.listeners.forEach((l)->l.onFeatureChange(this));
    }

    public void addListener(AppListener l){
        this.listeners.add(l);
    }

    public int stop(){
        if(!isRunning()) return -1;
        process.destroy();
        process.descendants().forEach(ProcessHandle::destroy);
        try{
            process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int exitValue = process.exitValue();
        listeners.forEach((l)->l.onStop(this, exitValue));
        return exitValue;
    }

    public void start(){
        if(isRunning()) stop();
        //npm fix for windows
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");
        if(isWindows&& runCmd.contains("npm ")) runCmd= runCmd.replaceAll("npm ", "npm.cmd ");
        ProcessBuilder pb = new ProcessBuilder(this.runCmd.split(" "))
                .directory(new File(this.localLocation));
        listeners.forEach((l)->l.onBuilder(this, pb));
        try {
            this.process = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listeners.forEach((l)->l.onStart(this));
    }

    @JsonIgnore
    public boolean isRunning(){
        return this.process !=null && this.process.isAlive();
    }

    @Override
    public String toString() {
        return this.name + " ("+this.localLocation+")";
    }
}
