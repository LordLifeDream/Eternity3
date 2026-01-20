package eternity3;

import eternity3.app.App;
import eternity3.app.features.*;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.json.JsonMapper;

import java.io.File;
import java.util.ArrayList;

public class AppManager {
    private static final ObjectMapper MAPPER;// = new ObjectMapper();
    private static ArrayList<App> apps=new ArrayList<>();
    public static boolean prettyPrint = true;

    static {
        MAPPER = JsonMapper.builder()
                .registerSubtypes(Eternity.FEATURE_CLASSES)
                .build();
    }

    public static App getApp(String name){
        for(App app: apps){
            if(app.getName().equals(name)) return app;
        }
        System.out.println("app "+ name + " not found!");
        return null;
    }

    public static void removeApp(App app){
        apps.remove(app);
        save();
    }

    public static ArrayList<App> getApps(){
        return apps;
    }

    public static void addApp(App app){
        apps.add(app);
        save();
    }

    public static void load(){
        File f = new File("./apps.json");
        if(!f.exists()){
            System.out.println("no apps file!");
            return;
        }
        apps = MAPPER.readValue(new File("./apps.json"),
                new TypeReference<ArrayList<App>>(){});
        apps.forEach(App::init);
    }

    public static void save(){
        ObjectWriter wr = prettyPrint? MAPPER.writer():MAPPER.writerWithDefaultPrettyPrinter();
        wr.writeValue(new File("./apps.json"), apps);
    }
}
