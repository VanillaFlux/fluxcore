package me.quickscythe.fluxcore.utils.config;

import json2.JSONObject;

/**
 * Easier way to specify a class as a ConfigFile manager.
 */
public class ConfigClass {
    protected final ConfigFile CONFIG;

    /**
     * @param configFile String to save file. Do not include file extension.
     */
    public ConfigClass(String configFile) {
        CONFIG = ConfigFileManager.getFile(configFile);
    }

    /**
     * @param configFile String to save file. Do not include file extension.
     * @param resource   String path to plugin resource. Include file extension.
     */
    public ConfigClass(String configFile, String resource) {
        CONFIG = ConfigFileManager.getFile(configFile, resource);
    }

    /**
     * @param configFile String to save file. Do not include file extension.
     * @param defaults   JSONObject of default values to populate ConfigFile.
     */
    public ConfigClass( String configFile, JSONObject defaults) {
        CONFIG = ConfigFileManager.getFile(configFile, defaults);
    }

    public ConfigFile getConfig(){
        return CONFIG;
    }

    /**
     * Save ConfigFile
     */
    public void finish() {
        CONFIG.save();
    }
}
