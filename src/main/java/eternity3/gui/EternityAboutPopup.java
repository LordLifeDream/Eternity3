package eternity3.gui;

import eternity3.selfupdate.SelfUpdater;

import javax.swing.*;
import java.awt.*;

public class EternityAboutPopup extends JDialog {
    private JPanel content;
    public EternityAboutPopup(){
        this.setModal(true);
        content = new JPanel(new BorderLayout());
        //ok button
        JButton okButton = new JButton("ok");
        okButton.addActionListener((e)->dispose());
        content.add(okButton, BorderLayout.SOUTH);
        JLabel mainText = new JLabel("eternity. wow. super. duper expiyy\nthis thing can do multiline, right?\nnope it can not!\n\ni will replace you with a text area or soemthing");
        content.add(mainText, BorderLayout.CENTER);
        TextArea are = new TextArea("hi im a text area\nthat can do multiline and stuff");
        are.setText(getText());
        are.setEditable(false);
        content.add(are, BorderLayout.CENTER);
        //logo
        JLabel logo = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("images/logo.png")));
        content.add(logo, BorderLayout.NORTH);
        this.setContentPane(content);
        this.setTitle("About Eternity");
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    private String getText(){
        StringBuilder b = new StringBuilder("Eternity version 3.");
        b.append("\n").append("© LLD Games, 2026").append("\n\n");
        b.append("Java version ").append(System.getProperty("java.version")).append("\n");
        String sVer = tryGetVersion();
        if(sVer!=null && !sVer.isEmpty()){
            b.append("Eternity release ").append(sVer).append("\n");
        }
        b.append("\nSupport mail: support@lldgames.de\n");
        return b.toString();
    }

    private String tryGetVersion(){
        return SelfUpdater.readThisVersion();
    }


}
