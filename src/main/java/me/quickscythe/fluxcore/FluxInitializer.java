package me.quickscythe.fluxcore;

import me.quickscythe.fluxcore.api.FluxEntrypoint;
import me.quickscythe.fluxcore.api.JavaMod;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FluxInitializer extends FluxEntrypoint {
    public  String NAME = "FluxMod";
    public String ID = "fluxmod";
    public String VERSION = "DEBUG_VERSION";
    public JavaMod mod = null;

    private final Logger LOGGER = LoggerFactory.getLogger(FluxInitializer.class);



    @Override
    public void onInitialize() {
        System.out.println("TEST");

    }
}
