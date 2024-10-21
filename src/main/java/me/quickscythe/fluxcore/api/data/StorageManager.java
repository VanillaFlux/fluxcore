package me.quickscythe.fluxcore.api.data;

import me.quickscythe.fluxcore.api.JavaMod;
import me.quickscythe.fluxcore.api.config.ConfigManager;
import me.quickscythe.fluxcore.api.config.files.Default;
import me.quickscythe.fluxcore.utils.CoreUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StorageManager {

    private static File configFolder;

    private static final InternalStorage storage = new InternalStorage();
    private static final Map<String, DataManager> dataManagers = new HashMap<>();
    public static void init(JavaMod mod) {

        configFolder = new File("fluxdata");
        if (!configFolder.exists()) {
            CoreUtils.getLoggerUtils().log("Creating " + configFolder.getName() + " folder: " + configFolder.mkdir());
        }
        registerDataManager(new AccountManager());
        ConfigManager.registerConfig(mod, Default.class);
//        ConfigFile file = ConfigFileManager.getFile("config", "defaults/config.json");

    }

    public static void registerDataManager(DataManager dataManager) {
        dataManagers.put(dataManager.getName(), dataManager);
    }

    public static DataManager getDataManager(String name) {
        return dataManagers.get(name);
    }

    public static InternalStorage getStorage() {
        return storage;
    }

    public static File getConfigFolder() {
        return configFolder;
    }


}
