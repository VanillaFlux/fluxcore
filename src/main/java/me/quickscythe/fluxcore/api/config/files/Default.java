package me.quickscythe.fluxcore.api.config.files;

import me.quickscythe.fluxcore.api.config.ConfigContent;
import me.quickscythe.fluxcore.api.config.ConfigFile;
import me.quickscythe.fluxcore.api.config.ConfigValue;

@ConfigFile(name = "config")
public class Default extends ConfigContent {

    @ConfigValue
    public String api_url = "http://localhost:8585/";

}
