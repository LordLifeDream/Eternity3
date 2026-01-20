package eternity3.gui.app;

import eternity3.app.App;
import eternity3.app.StartStopListener;
import eternity3.app.features.Feature;
import eternity3.gui.extra.outputviewer.ProcessOutputViewerPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Date;

public class AppPanel extends JPanel {
    private App app;
    private JPanel appOptions;
    //option components
    private JTextField name, localLocation, runCmd;
    //bottom control bar
    private JPanel controlBar;
    //write change needs this scope so it can be enabled
    private JButton writeChanges;
    //private JButton restartButton;
    private JButton resetButton;
    public AppPanel(App app){
        this.app = app;
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(),app.getName()));
        this.setLayout(new BorderLayout());
        this.appOptions = new JPanel();
        this.appOptions.setLayout(new BoxLayout(appOptions, BoxLayout.Y_AXIS));
        this.fillAppOptions();
        this.add(appOptions, BorderLayout.WEST);
        this.addStatusText();
        this.controlBar = new JPanel();
        this.filLControlBar();
        this.add(controlBar, BorderLayout.SOUTH);
        //app out
        ProcessOutputViewerPanel output = new ProcessOutputViewerPanel();
        if(app.getProcess() !=null) output.initListener(app.getProcess());
        app.addListener(new StartStopListener() {
            @Override
            public void onStart(App app) {
                String msg = "--App started at "+new Date()+"--";
                output.addLog(msg, Color.MAGENTA);
                output.initListener(app.getProcess());
            }
            @Override
            public void onStop(App app, int exitCode) {
                String msg = "--App stopped at "+new Date()+"--";
                output.addLog(msg, Color.MAGENTA);
            }
        });
        output.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "output"));
        this.add(output, BorderLayout.CENTER);
    }

    private void filLControlBar(){
        //addfeature
        JButton addFeature = new JButton("add feature");
        addFeature.addActionListener((e)->{
            SelectFeatureClassPopup p = new SelectFeatureClassPopup();
            try {
                Class<Feature> f = p.select();
                Feature feature = f.getConstructor().newInstance();
                feature.guidedInit(true);
                app.addFeature(feature);
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        });
        controlBar.add(addFeature);
        //write changes
        writeChanges = new JButton("write changes");
        writeChanges.setEnabled(false);
        writeChanges.addActionListener((e)->{
            write();
            //writeChanges.setEnabled(false);
            setChangeButton(false);
        });
        controlBar.add(writeChanges);
        //reset changes
        resetButton = new JButton("reset");
        resetButton.setEnabled(false);
        var reset = resetButton;
        reset.addActionListener((e)->{
            fillAppOptions();
            revalidate();
            setChangeButton(false);
        });
        controlBar.add(reset);
        //start/stop button
        this.createStartStopButton();
    }

    private void createStartStopButton(){
        JButton button = new JButton("start");
        JButton restartButton = new JButton("restart");
        restartButton.setEnabled(false);
        app.addListener(new StartStopListener() {
            @Override
            public void onStart(App app) {
                button.setText("stop");
                restartButton.setEnabled(true);
            }

            @Override
            public void onStop(App app, int exitCode) {
                button.setText("start");
                restartButton.setEnabled(false);
            }
        });
        button.addActionListener((e)->{
            try {
                if (button.getText().equals("start")) {
                    app.start();
                } else {
                    app.stop();
                }
            }catch (Exception ee){
                JOptionPane.showMessageDialog(null, ee);
            }
        });
        restartButton.addActionListener((e)->{
            app.restart();
        });
        //restartButton.setEnabled(false);
        //
        controlBar.add(button);
        controlBar.add(restartButton);
    }

    private void addStatusText(){
        JLabel status = new JLabel("app running: " + app.isRunning());
        app.addListener(new StartStopListener() {
            @Override
            public void onStart(App app) {
                status.setText("app running: true");
            }
            @Override
            public void onStop(App app, int exitCode) {
                status.setText("app running: false ("+exitCode+")");
            }
        });
        this.add(status, BorderLayout.NORTH);
    }

    private void write(){
        app.setName(name.getText());
        app.setLocalLocation(localLocation.getText());
        app.setRunCmd(runCmd.getText());
    }

    private void setChangeButton( boolean b){
        writeChanges.setEnabled(b);
        resetButton.setEnabled(b);
    }

    private void fillAppOptions(){
        appOptions.removeAll();
        DocumentListener dl = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setChangeButton(true);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                setChangeButton(true);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                setChangeButton(true);
            }
        };

        //name
        this.name = new JTextField(app.getName());
        this.name.setToolTipText("app name");
        this.name.getDocument().addDocumentListener(dl);
        appOptions.add(this.name);
        //local loc
        this.localLocation = new JTextField(app.getLocalLocation());
        this.localLocation.setToolTipText("app local location");
        this.localLocation.getDocument().addDocumentListener(dl);
        appOptions.add(this.localLocation);
        //run cmd
        this.runCmd = new JTextField(app.getRunCmd());
        this.runCmd.setToolTipText("app run command");
        this.runCmd.getDocument().addDocumentListener(dl);
        appOptions.add(this.runCmd);
        //features
        AppFeaturePanel afp = new AppFeaturePanel(app);
        JPanel featureWrapper = new JPanel(new BorderLayout());
        //AppFeatureList feature = new AppFeatureList(app);
        featureWrapper.add(afp, BorderLayout.CENTER);
        featureWrapper.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLoweredBevelBorder(), "Features"));
        featureWrapper.setPreferredSize(new Dimension(250, 1080));//fill rest of opt panel
        appOptions.add(featureWrapper);
    }
}
