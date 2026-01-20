package eternity3.commands;

import eternity3.AppManager;
import eternity3.Eternity;

import java.util.List;

public class QuitCommand extends Command{
    public QuitCommand(){
        this.name = "Quit";
        this.aliases = new String[]{"quit", "q", "bye"};
    }

    @Override
    void onExecute(List<String> params) {
        Eternity.exit();
    }
}
