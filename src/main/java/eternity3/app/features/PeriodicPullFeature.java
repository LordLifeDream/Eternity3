package eternity3.app.features;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eternity3.app.App;
import org.eclipse.jgit.api.PullResult;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class PeriodicPullFeature extends FeatureImpl{
    private long pullTime;
    @JsonIgnore
    private Timer timer;

    @Override
    public void onInit(App app) {
        timer = new Timer("periodicPullTimer"+app.getName());
        GitFeature git = app.getFeature(GitFeature.class);
        TimerTask t= new TimerTask() {
            @Override
            public void run() {
                PullResult r = git.pull();
                boolean changed =
                        r.getFetchResult().getTrackingRefUpdates()!=null
                                &&  !r.getFetchResult().getTrackingRefUpdates().isEmpty();
                if(changed){
                    app.restart();
                }
            }
        };
        timer.scheduleAtFixedRate(t, pullTime, pullTime);
    }

    public long getPullTime() {
        return pullTime;
    }

    @Override
    public void guidedInit(boolean ui) {
        this.pullTime = promptLong(ui, "how often should be pulled (every x minutes)?", String.valueOf(pullTime/1000*60))*1000*60;
        if(true)return;
        Scanner sc = new Scanner(System.in);
        System.out.println("how often should be pulled (every x minutes)?");
        this.pullTime = sc.nextLong()*1000*60;
        sc.nextLine();
    }
}
