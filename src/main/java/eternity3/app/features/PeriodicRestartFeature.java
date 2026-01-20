package eternity3.app.features;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import eternity3.app.App;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

@JsonTypeName("PeriodicRestartFeature")
public class PeriodicRestartFeature extends FeatureImpl{
    private long restartTime = 1000*60*60*12;
    @JsonIgnore
    private Timer timer;

    @Override
    public void onStart(App app) {
        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                app.restart();
            }
        };
        if(timer!=null) timer.cancel();
        timer = new Timer("periodicRestart"+app.getName());
        timer.schedule(t, restartTime);
    }

    public long getRestartTime() {
        return restartTime;
    }

    @Override
    public void guidedInit(boolean ui) {
        this.restartTime = promptLong(ui, "please enter the restart time after start in minutes:", String.valueOf(restartTime/(1000*60)))*60*1000;
        if(true) return;
        Scanner sc = new Scanner(System.in);
        System.out.println("please enter the restart time after start in minutes:");
        String input =sc.nextLine();
        long time = Long.parseLong(input.strip())*60*1000;
        this.restartTime = time;
    }
}
