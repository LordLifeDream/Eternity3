package eternity3.commands;

import eternity3.selfupdate.SelfUpdater;

import java.util.List;

public class UpdateCheckCommand extends Command{
    public UpdateCheckCommand(){
        this.name = "UpdateCheck";
        this.aliases = new String[]{"updatecheck", "checkupdate", "check"};
    }

    @Override
    void onExecute(List<String> params) {
        SelfUpdater.checkUpdate();
    }
}
