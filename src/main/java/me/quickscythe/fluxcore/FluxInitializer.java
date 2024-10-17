package me.quickscythe.fluxcore;

import net.fabricmc.api.ModInitializer;

public abstract class FluxInitializer implements ModInitializer {
    public  String NAME = "FluxMod";
    public String ID = "fluxmod";
    public String VERSION = "DEBUG_VERSION";

    boolean debug(){
        return VERSION.equalsIgnoreCase("DEBUG_VERSION");
    }
}
