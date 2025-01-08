package me.quickscythe.fluxcore.api;

import me.quickscythe.fluxcore.api.config.ConfigManager;
import me.quickscythe.fluxcore.api.config.files.Default;
import me.quickscythe.fluxcore.api.logger.LoggerUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ApiManager {

    private static String TOKEN = "";
    private static String API_URL = "";

    public static void init(){

        API_URL = (String) ConfigManager.getConfig(Default.class).get("api_url");
        LoggerUtils.getLogger().info("Initializing API Manager... ({})", API_URL);
       generateNewToken();
    }

    private static void generateNewToken() {
        try {
            LoggerUtils.getLogger().info("Generating new token... ({})", API_URL);
            TOKEN = new JSONObject(getContext(URI.create(API_URL + "/app/token").toURL())).getString("success");
        } catch (MalformedURLException e) {
            LoggerUtils.getLogger().error("URL malformed. Couldn't generate new token. ({})", e.getMessage());
        } catch (JSONException e){
            LoggerUtils.getLogger().error("Failed to generate new token. ({})", e.getMessage());
            TOKEN = "";
        }
    }

    public static void checkToken(){
        try {
            if(getContext(URI.create(API_URL + "/app/tokens").toURL()).contains("error")){
                generateNewToken();
            }
        } catch (MalformedURLException e) {
            LoggerUtils.getLogger().error("Failed to check token. ({})", e.getMessage());
        }
    }

    public static String getContext(URL url){
        StringBuilder result = new StringBuilder();
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (IOException e) {
            LoggerUtils.getLogger().error("Failed to get context. ({})", e.getMessage());
            return "";
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
            LoggerUtils.getLogger().error("Failed to get app data. ({})", e.getMessage());
            return "";
        }
    }
}
