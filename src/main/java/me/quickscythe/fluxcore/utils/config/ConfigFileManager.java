package me.quickscythe.fluxcore.utils.config;

import json2.JSONObject;
import me.quickscythe.fluxcore.utils.CoreUtils;
import me.quickscythe.fluxcore.utils.data.StorageManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigFileManager {

    private static final Map<String, ConfigFile> FILE_MAP = new HashMap<>();



    /**
     * Get or generate ConfigFile.
     * @param filename String key file will be stored as. Do not include file extension.
     * {@return New or generated ConfigFile}
     */
    public static ConfigFile getFile(String filename) {
        return getFile(filename, new JSONObject());
    }


    /**
     * Get or generate ConfigFile.
     * @param filename String key file will be stored as. Do not include file extension.
     * @param defaults JSONObject to load into file if empty.
     * {@return New or generated ConfigFile}
     */
    public static ConfigFile getFile(String filename, JSONObject defaults) {

        if (!FILE_MAP.containsKey(filename)) {
            File file = new File(StorageManager.getConfigFolder(), filename + ".json");
            ConfigFile config = new ConfigFile(file, defaults);
            FILE_MAP.put(filename, config);
            config.save();
        }
        return FILE_MAP.get(filename);
    }


    /**
     * Get or generate ConfigFile.
     * @param filename String key file will be stored as. Do not include file extension.
     * @param resource String path to plugin resource. File extension must be included.
     * {@return New or generated ConfigFile}
     */
    public static ConfigFile getFile(String filename, String resource) {

        JSONObject defaults = new JSONObject();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(defaults.getClass().getClassLoader().getResource(resource).openStream()))) {
            StringBuilder data = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
                data.append("\n");
            }
            defaults = data.toString().isEmpty() ? defaults : new JSONObject(data.toString());
        } catch (IOException e) {
            CoreUtils.getLoggerUtils().getLogger().error("There was an error", e);
        }
        return getFile(filename, defaults);
    }

    /**
     * Get all file names.
     * @return {@code Set<String>} of filenames.
     */
    public static Set<String> getFiles() {
        return FILE_MAP.keySet();
    }
}
