package eternity3.commands;

import eternity3.selfupdate.SelfUpdater;

import java.util.List;

public class UpdateCommand extends Command{
    public UpdateCommand(){
        this.name = "Update";
        this.aliases = new String[]{"update", "upgrade", "checkupdate", "checkupgrade"};
    }

    @Override
    void onExecute(List<String> params) {
        try{
            SelfUpdater.update();
            System.out.println("Eternity updated. You may now restart the program.");
        }catch (Exception e){
            System.err.println("updating FAILED!:");
            e.printStackTrace();
        }
    }
}
