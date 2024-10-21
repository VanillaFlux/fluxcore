package me.quickscythe.fluxcore;

import me.quickscythe.fluxcore.api.JavaMod;
import me.quickscythe.fluxcore.utils.CoreUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FluxInitializer implements ModInitializer {
    public  String NAME = "FluxMod";
    public String ID = "fluxmod";
    public String VERSION = "DEBUG_VERSION";
    public JavaMod mod = null;

    private final Logger LOGGER = LoggerFactory.getLogger(FluxInitializer.class);



    boolean debug(){
        return VERSION.equalsIgnoreCase("DEBUG_VERSION");
    }

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getModContainer(ID).ifPresentOrElse(modContainer -> {
            mod = new JavaMod(FabricLoader.getInstance().getModContainer(ID).get().getMetadata());
        }, () -> LOGGER.error("Failed to get mod container for {}", ID));

        LOGGER.info("Initializing {} v{} ({})...", NAME, VERSION, ID);
        if(debug()){
            LOGGER.info("Starting debug mode...");
        }
    }
}
