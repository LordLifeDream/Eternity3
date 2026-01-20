package eternity3.commands;

import eternity3.AppManager;
import eternity3.app.App;

import java.util.List;

public class RemoveAppCommand extends Command{
    public RemoveAppCommand(){
        this.name = "RemoveApp";
        this.aliases = new String[]{"removeapp", "rm", "remove"};
    }

    @Override
    void onExecute(List<String> params) {
        String name = params.get(1);
        var p = AppManager.getApp(name);
        AppManager.removeApp(p);
        p.stop();
        System.out.println("app removed!");
    }
}
