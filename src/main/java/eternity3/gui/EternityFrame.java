package eternity3.gui;

import eternity3.AppManager;
import eternity3.Eternity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class EternityFrame extends JFrame {

    public static void main(String[] args) {
        AppManager.load();
        new EternityFrame();
        Eternity.main(new String[0]);
    }


    public EternityFrame(){
        this.setSize(1600, 900);
        this.setContentPane(new EternityMainPanel());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        addMenuBar();
        this.setTitle("Eternity 3");
        this.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("images/icon_superSmall.png"))).getImage());
        this.setVisible(true);
    }

    private void addMenuBar(){
        JMenuBar b = new JMenuBar();
        addEternityCategory(b);
        addAppManagerCategory(b);
        this.setJMenuBar(b);
    }
    private void addAppManagerCategory(JMenuBar b){
        JMenu appManager = new JMenu("AppManager");
        //save
        JMenuItem save = new JMenuItem("save");
        save.addActionListener((e)->AppManager.save());
        appManager.add(save);
        //stop all
        JMenuItem stopAll = new JMenuItem("end all");
        stopAll.addActionListener((e)->AppManager.endAll());
        appManager.add(stopAll);

        //-----------
        appManager.addSeparator();
        //pretty print
        JCheckBoxMenuItem prettyPrint = new JCheckBoxMenuItem("pretty print");
        prettyPrint.setState(AppManager.prettyPrint);
        prettyPrint.addActionListener((e)->AppManager.prettyPrint = prettyPrint.getState());
        appManager.add(prettyPrint);

        b.add(appManager);
    }

    private void addEternityCategory(JMenuBar b){
        JMenu eternity = new JMenu("Eternity");
        ImageIcon eternityIcon = new ImageIcon(getClass().getClassLoader().getResource("images/icon_superSmall.png"));
        eternity.setIcon(eternityIcon);
        //dispose
        JMenuItem dispose = new JMenuItem("dispose window");
        dispose.addActionListener((e)->Eternity.disposeUI());
        eternity.add(dispose);
        //quit
        JMenuItem quit = new JMenuItem("exit");
        quit.addActionListener((e)-> {
            this.setVisible(false);
            Eternity.exit();
        });
        eternity.add(quit);
        b.add(eternity);
    }
}
