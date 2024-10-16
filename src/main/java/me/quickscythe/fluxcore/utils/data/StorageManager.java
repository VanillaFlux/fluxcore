package me.quickscythe.fluxcore.utils.data;

import me.quickscythe.fluxcore.utils.ApiManager;
import me.quickscythe.fluxcore.utils.CoreUtils;
import me.quickscythe.fluxcore.utils.config.ConfigFile;
import me.quickscythe.fluxcore.utils.config.ConfigFileManager;
import me.quickscythe.fluxcore.utils.data.api.DataManager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StorageManager {

    private static File configFolder;

    private static final InternalStorage storage = new InternalStorage();
    private static final Map<String, DataManager> dataManagers = new HashMap<>();
    public static void init() {

        configFolder = new File("fluxdata");
        if (!configFolder.exists()) {
            CoreUtils.getLoggerUtils().log("Creating " + configFolder.getName() + " folder: " + configFolder.mkdir());
        }
        registerDataManager(new AccountManager());
        ConfigFile file = ConfigFileManager.getFile("config", "defaults/config.json");

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
