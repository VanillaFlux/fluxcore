package me.quickscythe.fluxcore.api.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigValue {
    boolean override() default false;
}
