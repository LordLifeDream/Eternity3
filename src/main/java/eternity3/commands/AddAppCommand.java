package eternity3.commands;

import eternity3.AppManager;
import eternity3.app.App;

import java.util.List;
import java.util.Scanner;

public class AddAppCommand extends Command{
    public AddAppCommand(){
        this.name = "AddApp";
        this.aliases = new String[]{"add", "addapp"};
    }


    @Override
    void onExecute(List<String> params) {
        if(params.size()<2){
            guidedAdd();
            return;
        }
        System.out.println("please use guided for now...");
    }

    private void guidedAdd(){
        Scanner sc = new Scanner(System.in);
        System.out.println("please enter a name");
        String name = sc.nextLine();
        System.out.println("please enter a local location");
        String locloc = sc.nextLine();
        System.out.println("please enter a run cmd");
        String rcmd = sc.nextLine();
        App app = new App(name, locloc, rcmd);
        AppManager.addApp(app);
        System.out.println("app added!");
    }
}
