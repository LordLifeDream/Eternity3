package eternity3.app;

import eternity3.app.features.FeatureImpl;
import tools.jackson.databind.ObjectMapper;

import java.io.File;

public class TEST {
    public static void main(String[] args) {
        //var a =new App();
        //new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File("./test.json"), a);
        new ObjectMapper().readValue(new File("./test.json"), App.class);
    }
}
