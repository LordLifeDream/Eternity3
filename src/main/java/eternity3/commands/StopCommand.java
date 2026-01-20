package eternity3.commands;

import eternity3.AppManager;
import eternity3.app.App;

import java.util.List;

public class StopCommand extends Command{
    public StopCommand(){
        this.name = "Stop";
        this.aliases = new String[]{"stop", "end"};
    }

    @Override
    void onExecute(List<String> params) {
        App p = AppManager.getApp(params.get(1));
        System.out.println("app stopped with code " + p.stop() + "!");
    }
}
