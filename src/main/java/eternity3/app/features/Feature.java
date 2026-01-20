package eternity3.app.features;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import eternity3.app.AppListener;

import javax.swing.*;
import java.util.Scanner;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
public abstract class Feature implements AppListener {
    public abstract void guidedInit(boolean ui);

    protected String promptString(boolean ui, String msg, String initialValue){
        if(ui) return JOptionPane.showInputDialog(msg, initialValue);
        Scanner sc = new Scanner(System.in);
        System.out.println(msg);
        return sc.nextLine();
    }

    protected long promptLong(boolean ui, String msg, String initialValue){
        if(ui) return Long.parseLong(JOptionPane.showInputDialog(msg, initialValue));
        Scanner sc = new Scanner(System.in);
        System.out.println(msg);
        long val = sc.nextLong();
        sc.nextLine();
        return val;
    }
}

