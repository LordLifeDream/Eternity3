package eternity3.app.features;

import eternity3.app.App;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.TransportCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.net.URI;
import java.util.Scanner;

public class GitFeature extends FeatureImpl{
    private Git git;
    private String remoteURL;
    private String username, pass;

    @Override
    public void guidedInit(boolean ui) {
        this.remoteURL = this.promptString(ui, "enter remote url");
        this.username = promptString(ui, "enter username, or nothing for none");
        this.pass = promptString(ui, "enter pass/token, or nothing for none");

        if(true) return;
        Scanner sc = new Scanner(System.in);
        System.out.println("enter remote url");
        this.remoteURL = sc.nextLine();
        System.out.println("enter username, or nothing for none");
        this.username = sc.nextLine();
        System.out.println("enter pass/token, or nothing for none");
        this.pass = sc.nextLine();
    }

    @Override
    public void onInit(App app) {
        this.openOrClone(app);
    }

    public String getRemoteURL(){
        return this.remoteURL;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public boolean remoteChanges(){
        var cmd = git.fetch();//.setRemote(this.remoteURL);
        if(!username.isEmpty()){
            cmd.setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.username, this.pass));
        }
        try {
            cmd.call();

            Repository repo = git.getRepository();
            String currentBranch = repo.getBranch();
            ObjectId localCommit = repo.resolve(currentBranch);
            ObjectId remoteCommit = repo.resolve("origin/" + currentBranch);
            if (localCommit == null || remoteCommit == null) {
                return false;
            }
            try (RevWalk walk = new RevWalk(repo)) {
                RevCommit localRev = walk.parseCommit(localCommit);
                RevCommit remoteRev = walk.parseCommit(remoteCommit);

                walk.setRetainBody(false);

                boolean isMerged = walk.isMergedInto(remoteRev, localRev);
                return !isMerged;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PullResult pull(){
        var cmd =git.pull();
                //.setRemote(this.remoteURL);
        if(!username.isEmpty()){
            cmd.setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.username, this.pass));
        }
        try {
            return cmd.call();
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

    private void openOrClone(App app){
        try{
            git = Git.open(new File(app.getLocalLocation()));
        }catch (Exception e){
            var cmd = Git.cloneRepository()
                    .setDirectory(new File(app.getLocalLocation()))
                    .setURI(this.remoteURL);
                    //.setRemote(this.remoteURL);
            if(!username.isEmpty()){
                cmd.setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.username, this.pass));
            }
            try {
                git = cmd.call();
            } catch (GitAPIException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
