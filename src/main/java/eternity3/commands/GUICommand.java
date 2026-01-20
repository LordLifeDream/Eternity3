package eternity3.commands;

import eternity3.Eternity;

import java.util.List;

public class GUICommand extends Command{

    public GUICommand(){
        this.name = "GUI";
        this.aliases = new String[]{"gui", "window", "graphicaluserinterface"};
    }
    @Override
    void onExecute(List<String> params) {
        if(params.size()>1){
            if(params.get(1).startsWith("o")) Eternity.openUI();
            else Eternity.disposeUI();
            return;
        }
        Eternity.toggleUI();
    }
}
