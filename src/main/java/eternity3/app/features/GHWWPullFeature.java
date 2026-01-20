package eternity3.app.features;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eternity3.AppManager;
import eternity3.app.App;
import org.eclipse.jgit.api.Git;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GHWWPullFeature extends FeatureImpl{
    private String ghwwUrl;
    private String ghwwToken;
    @JsonIgnore
    private String repoName;
    @JsonIgnore
    private GitFeature git;
    @JsonIgnore
    private App app;

    public String getGhwwUrl() {
        return ghwwUrl;
    }

    public String getGhwwToken() {
        return ghwwToken;
    }

    @Override
    public void onInit(App app) {
        GitFeature f = app.getFeature(GitFeature.class);
        this.app = app;
        this.git = f;
        String remoteUrl = f.getRemoteURL();
        Pattern p = Pattern.compile(".*//github\\.com/(.+)\\.git");
        Matcher m = p.matcher(remoteUrl);
        m.matches();
        this.repoName = m.group(1);
        createClient();
    }

    @Override
    public void guidedInit(boolean ui) {
        this.ghwwUrl = promptString(ui, "please enter your ghww url");
        this.ghwwToken = promptString(ui, "please enter your ghww token");
        if(true) return;

        Scanner sc = new Scanner(System.in);
        System.out.println("please enter your ghww url");
        String url = sc.nextLine();
        System.out.println("please enter your ghww token");
        String token = sc.nextLine();
        this.ghwwUrl = url;
        this.ghwwToken = token;
    }

    private void createClient(){
        WebSocketClient cl = new WebSocketClient(URI.create(ghwwUrl)) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                this.send("{\"t\":\"GREETINGS\",\"token\":\""+ghwwToken+"\"}");
            }

            @Override
            public void onMessage(String message) {
                System.out.println(message);
                JsonNode root =new ObjectMapper().readTree(message);
                String type = root.get("t").asString();
                switch(type){
                    case "GREETINGS_ACK"-> this.send("{\"t\":\"SUBSCRIBE\",\"repo\":\""+repoName+"\"}");
                    case "EVENT"->{
                        String eventType = root.get("type").asString();
                        if(eventType.toLowerCase().equals("push")){
                            git.pull();
                            app.restart();
                        }
                    }
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                try {
                    TimeUnit.MILLISECONDS.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //try again after 5 seconds
                createClient();
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };
        cl.connect();
    }
}
