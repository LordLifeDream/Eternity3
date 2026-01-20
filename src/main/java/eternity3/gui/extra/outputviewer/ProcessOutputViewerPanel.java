package eternity3.gui.extra.outputviewer;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessOutputViewerPanel extends JPanel {
//TODO MAKE THIS BETTER!
    private JPanel logPanel;
    public ProcessOutputViewerPanel(){
        this.setLayout(new BorderLayout());
        //
        this.logPanel = new JPanel();
        this.logPanel.setLayout(new BoxLayout(this.logPanel, BoxLayout.Y_AXIS));

        JScrollPane wrap = new JScrollPane(this.logPanel);
        this.add(wrap, BorderLayout.CENTER);
        //TODO process input

        //initListener(p);
    }

    public void initListener(Process p){
        new Thread(() -> {
            try (InputStream inputStream = p.getInputStream();
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String finalLine = line;
                    addLog(finalLine, Color.BLUE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try (InputStream errorStream = p.getErrorStream();
                 InputStreamReader errorStreamReader = new InputStreamReader(errorStream);
                 BufferedReader bufferedReader = new BufferedReader(errorStreamReader)) {

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String finalLine = line;
                    addLog(finalLine, Color.RED);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void addLog(String log, Color col){
        JLabel lab = new JLabel(log);
        lab.setForeground(col);
        if(logPanel.getComponentCount()>200){
            logPanel.remove(0);
        }
        logPanel.add(lab);
        this.revalidate();
    }
}
