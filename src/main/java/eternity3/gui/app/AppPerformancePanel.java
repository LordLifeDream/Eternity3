package eternity3.gui.app;

import eternity3.app.App;
import eternity3.app.StartStopListener;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class AppPerformancePanel extends JPanel {
    private App app;
    private JPanel centerPanel;
    //----
    private JLabel memoryLabel, cpuLabel, extraLabel;
    //--
    public AppPerformancePanel(App app){
        this.app = app;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(250, this.getPreferredSize().height));
        //this.getPreferredSize().width = 250;
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(),
                "performance"));

        this.centerPanel = new JPanel();
        this.centerPanel.setLayout(new BoxLayout(this.centerPanel, BoxLayout.Y_AXIS));
        this.memoryLabel = new JLabel("memory not yet recorded");
        this.cpuLabel = new JLabel("cpu not yet recorded");
        this.extraLabel = new JLabel("");
        this.centerPanel.add(this.memoryLabel);
        this.centerPanel.add(this.cpuLabel);
        this.centerPanel.add(this.extraLabel);
        this.centerPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        this.add(centerPanel, BorderLayout.CENTER);

        ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();
        //ScheduledFuture<?> loop = null;
        AtomicReference<ScheduledFuture<?>> loop = new AtomicReference<>();
        app.addListener(new StartStopListener() {
            @Override
            public void onStart(App app) {
                if(loop.get()!=null) loop.get().cancel(true);
                loop.set(s.scheduleAtFixedRate(AppPerformancePanel.this::updateStats, 1000, 1000, TimeUnit.MILLISECONDS));
                extraLabel.setText("");
            }

            @Override
            public void onStop(App app, int exitCode) {
                if(loop.get()==null) return;
                loop.get().cancel(true);
                loop.set(null);
                extraLabel.setText("monitoring ended with app stop");
            }
        });
        //start monitor loop if app is already running.
        if(app.isRunning()) loop.set(s.scheduleAtFixedRate(AppPerformancePanel.this::updateStats, 1000, 1000, TimeUnit.MILLISECONDS));
    }

    public void updateStats(){
        long mem = app.getMem();
        double cpu = app.getCPU();
        //--
        String cpuStr = "CPU: "+String.format("%.2f", cpu)+"%";
        String memStr = "memory: "+makeMemoryString(mem);
        this.memoryLabel.setText(memStr);
        this.cpuLabel.setText(cpuStr);
    }

    private String makeMemoryString(long bytes){
        if(bytes<1024)return bytes+" B";
        String[] units = {"KiB", "MiB", "GiB", "TiB", "PiB"};
        double value = bytes;
        int i = 0;
        while(value >= 1024 && i < units.length - 1){
            value/=1024;
            i++;
        }
        return String.format("%.2f %s", value, units[i]);
    }
}
