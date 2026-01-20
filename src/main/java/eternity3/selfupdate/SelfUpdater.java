package eternity3.selfupdate;

import net.lingala.zip4j.ZipFile;
import org.kohsuke.github.GHAsset;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class SelfUpdater {
    private static final String REPO_NAME = "LordLifeDream/Eternity3";
    private static final GitHub gh;

    static {
        try {
            gh = GitHub.connectAnonymously();
        } catch (IOException e) {
            System.err.println("failed to connect to github. SelfUpdater unavailable");
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        update();
    }

    public static void checkUpdate(){
        try {
            String thisVersion = readThisVersion();
            if(thisVersion == null){
                System.err.println("VERSION CHECK: could not identify installed version!");
                System.err.println("run the update command to fix this!");
                return;
            }
            GHRepository repo = gh.getRepository(REPO_NAME);
            GHRelease latest = repo.getLatestRelease();
            String latestTag = latest.getTagName();
            if(!thisVersion.equals(latestTag)){
                System.err.println("VERSION CHECK: a new version is available:\n"
                +"your version: "+thisVersion+"\nlatest version: "+latestTag
                        +"\nRun the update command to update"
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update() throws IOException {
        GHRepository repo = gh.getRepository(REPO_NAME);
        //System.out.println(repo.getReleaseByTagName(" v0.1.0-test"));
        GHRelease release = repo.getLatestRelease();
        GHAsset asset = release.getAssets().stream()
                .filter(a -> a.getName().equals("Eternity.zip"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Asset nicht gefunden"));

        String url = asset.getBrowserDownloadUrl();
        System.out.println("downloading "+url);
        URL u = URI.create(url).toURL();
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        File temp = new File("./Eternity.zip");
        try(FileOutputStream fos = new FileOutputStream(temp)) {
            fos.write(conn.getInputStream().readAllBytes());
        }
        conn.disconnect();
        try(ZipFile zip = new ZipFile(temp)){
            zip.extractAll("./");
        }
        if(!temp.delete()){
            System.err.println("UPDATE: failed to delete the temp Eternity.zip. Deal with it, ig...");
        }
        writeVersion(release.getTagName());
    }


    private static String readThisVersion(){
        File versionFile = new File("./.releaseVersion.txt");
        if(!versionFile.exists()) return null;
        try(FileInputStream fis = new FileInputStream(versionFile)){
            return new String(fis.readAllBytes());
        } catch (Exception e) {
            return null;
        }
    }

    private static void writeVersion(String version){
        File versionFile = new File("./.releaseVersion.txt");
        try {
            if (!versionFile.exists()) versionFile.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
        }
        try(FileOutputStream fos = new FileOutputStream(versionFile)){
            fos.write(version.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
