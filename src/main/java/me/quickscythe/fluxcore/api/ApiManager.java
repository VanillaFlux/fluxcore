package me.quickscythe.fluxcore.api;

import me.quickscythe.fluxcore.api.config.ConfigManager;
import me.quickscythe.fluxcore.api.config.files.Default;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class ApiManager {

    private static String TOKEN = "";
    private static String API_URL = "";

    public static void init(){
        API_URL = (String) ConfigManager.getConfig(Default.class).get("api_url");
       generateNewToken();
    }

    private static void generateNewToken() {
        try {
            TOKEN = new JSONObject(getContext(URI.create(API_URL + "/app/token").toURL())).getString("success");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkToken(){
        try {
            if(getContext(URI.create(API_URL + "/app/tokens").toURL()).contains("error")){
                generateNewToken();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getContext(URL url){
        StringBuilder result = new StringBuilder();
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String appData(String s) {
        try {
            String surl = API_URL + "/app/v1/" + TOKEN + "/" + s;
            System.out.println("surl = " + surl);
            URL url = URI.create(surl).toURL();
            System.out.println("url = " + url);
            return getContext(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
