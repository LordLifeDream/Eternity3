package eternity3;

import eternity3.app.features.*;
import eternity3.commands.Commands;
import eternity3.gui.EternityFrame;

import java.util.Scanner;

public class Eternity {
    public static Scanner inScanner;
    private static EternityFrame uiWindow;

    public static void main(String[] args) {
        AppManager.load();
        for(int i = 0; i<args.length; ++i){
            if(args[i].equals("-gui")){
                openUI();
            }
        }
        setupInput();
    }

    public static void openUI(){
        if(uiWindow!=null) return;
        uiWindow = new EternityFrame();
    }
    public static void disposeUI(){
        if(uiWindow == null) return;
        uiWindow.dispose();
        uiWindow = null;
    }
    public static void toggleUI(){
        if(uiWindow == null) openUI();
        else disposeUI();
    }

    private static void  setupInput(){
        inScanner = new Scanner(System.in);
        System.out.println("Welcome to Eternity v3! You may now enter commands  below! vvv");
        while (inScanner.hasNext()){
            String line = inScanner.nextLine();
            Commands.execute(line);
            System.out.println("Welcome to Eternity v3! You may now enter commands  below! vvv");
        }
        System.out.println("eol? Goodbye!");
    }

    public static void exit(){
        AppManager.save();
        System.out.println("goodbye!");
        System.exit(0);
    }

    //constants
    public static  final Class<Feature>[] FEATURE_CLASSES = new Class[]{
            StartOnInitFeature.class,
            PeriodicRestartFeature.class,
            GitFeature.class,
            GHWWPullFeature.class,
            PeriodicPullFeature.class
    };
}
