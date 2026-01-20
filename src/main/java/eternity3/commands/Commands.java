package eternity3.commands;

import java.util.ArrayList;

public class Commands {
    static {
        commands = new ArrayList<>();
        addCommands();
    }
    private static ArrayList<Command> commands;

    public static void execute(String line){
        try {
            String cmd = line.strip().split(" ")[0];
            for (Command c : commands) {
                if (c.matches(cmd)) {
                    c.execute(line);
                    return;
                }
            }
        }catch (Exception e){
            System.err.println("command execution errored!");
            e.printStackTrace();
        }
        System.err.println("command not found!");
    }


    private static void addCommands(){
        commands.add(new StartCommand());
        commands.add(new StopCommand());
        commands.add(new QuitCommand());
        commands.add(new AddAppCommand());
        commands.add(new RemoveAppCommand());
        commands.add(new AddFeatureCommand());
        commands.add(new GUICommand());
    }
}
