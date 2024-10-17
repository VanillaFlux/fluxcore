package me.quickscythe.fluxcore;

import me.quickscythe.fluxcore.utils.CoreUtils;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FluxInitializer implements ModInitializer {
    public  String NAME = "FluxMod";
    public String ID = "fluxmod";
    public String VERSION = "DEBUG_VERSION";

    private final Logger LOGGER = LoggerFactory.getLogger(FluxInitializer.class);



    boolean debug(){
        return VERSION.equalsIgnoreCase("DEBUG_VERSION");
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing {} v{} ({})...", NAME, VERSION, ID);
        if(debug()){
            LOGGER.info("Starting debug mode...");
        }
    }
}
