package me.quickscythe.fluxcore.api.config;

import me.quickscythe.fluxcore.api.JavaMod;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static final Map<Class<? extends ConfigContent>, Config> configs = new HashMap<>();

    public static Config registerConfig(JavaMod mod, Class<? extends ConfigContent> configTemplate) {
        try {
            Config config = new Config(mod, configTemplate);
            configs.put(configTemplate, config);
            return config;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Config getConfig(Class<? extends ConfigContent> clazz) {
        return configs.get(clazz);
    }
}
