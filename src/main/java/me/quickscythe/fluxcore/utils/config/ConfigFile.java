package me.quickscythe.fluxcore.utils.config;


import json2.JSONObject;
import me.quickscythe.fluxcore.utils.CoreUtils;
import me.quickscythe.fluxcore.utils.logger.LoggerUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.launch.server.FabricServerLauncher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * ConfigFile is a class for creating custom config files.
 * @author QuickSctythe
 */
public class ConfigFile implements Config {

    final JSONObject defaults;
    JSONObject data;
    File file;

    /**
     * Generate new ConfigFile instance. Should only be accessed by {@link ConfigFileManager}
     * @param file Physical location the ConfigFile will be saved to.
     * @param defaults Default values to populate ConfigFile. Can be empty.
     */
     ConfigFile(File file, JSONObject defaults) {
         if(!file.exists()){
             try {
                 CoreUtils.getLoggerUtils().log("Creating " + file.getName() + ": " + file.createNewFile());
             } catch (IOException e) {
                 CoreUtils.getLoggerUtils().log("There was an error creating " + file.getName());
                 CoreUtils.getLoggerUtils().log(LoggerUtils.LogLevel.ERROR, e.getMessage());
             }
         }
        StringBuilder data = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                data.append(scanner.nextLine());
            }
            scanner.close();
        } catch (IOException e) {
            CoreUtils.getLoggerUtils().log("There was an error loading " + file.getName());
            CoreUtils.getLoggerUtils().log(LoggerUtils.LogLevel.ERROR, e.getMessage());
            e.printStackTrace();
        }
        this.data = data.toString().isEmpty() ? defaults : new JSONObject(data.toString());
        for(String s : defaults.keySet()){
            if(!this.data.has(s)){
                this.data.put(s, defaults.get(s));
            }
        }
        this.defaults = new JSONObject(defaults.toString());
        this.file = file;
    }

    public void save() {

        CoreUtils.getLoggerUtils().log("Saving " + file.getName());
        try {
            FileWriter f2 = new FileWriter(file, false);
            f2.write(data.toString(2));
            f2.close();
        } catch (IOException e) {
            CoreUtils.getLoggerUtils().log("There was an error saving " + file.getName());
            CoreUtils.getLoggerUtils().log(LoggerUtils.LogLevel.ERROR, e.getMessage());
        }
    }


    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public void reset() {
        this.data = new JSONObject(defaults.toString());
        save();
    }
}
