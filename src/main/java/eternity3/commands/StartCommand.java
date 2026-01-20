package eternity3.commands;

import eternity3.AppManager;

import java.util.List;

public class StartCommand extends Command{
    public StartCommand(){
        this.name = "Start";
        this.aliases = new String[]{"start", "run"};
    }

    @Override
    void onExecute(List<String> params) {
        String appName = params.get(1);
        AppManager.getApp(appName).start();
    }
}
