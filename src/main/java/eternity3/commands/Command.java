package eternity3.commands;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    protected String name;
    protected String[] aliases;

    public boolean matches(String firstToken){
        List<String> s = List.of(aliases);
        for(String s2 : s){
            if(s2.equalsIgnoreCase(firstToken)) return true;
        }
        return false;
    }

    abstract void onExecute(List<String> params);

    public void execute(String fullLine){
        ArrayList<String> params = new ArrayList<>();
        StringBuilder currentParam = new StringBuilder();
        boolean inQuote = false;
        //char lastChar = 0;
        boolean escaped = false;
        for(char c: fullLine.toCharArray()){
            if(escaped){
                currentParam.append(c);
                escaped = false;
            } else{
                switch (c){
                    case '"'-> inQuote = !inQuote;
                    case ' '->{
                        if(inQuote){
                            currentParam.append(c);
                        }else {
                            if(!currentParam.isEmpty())params.add(currentParam.toString());
                            currentParam = new StringBuilder();
                        }
                    }
                    case '\\'-> {
                        escaped = true;
                    }
                    default -> currentParam.append(c);
                }
            }
        }
        if(!currentParam.isEmpty())params.add(currentParam.toString());
        onExecute(params);
    }
}
