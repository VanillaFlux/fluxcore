package me.quickscythe.fluxcore.api.config;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ConfigContent {

    @ConfigValue(override = true)
    public Number version = 1;

    Field[] getContentValues() {
        return Arrays.stream(this.getClass().getFields()).filter(f -> f.isAnnotationPresent(ConfigValue.class)).toArray(Field[]::new);
    }
}
