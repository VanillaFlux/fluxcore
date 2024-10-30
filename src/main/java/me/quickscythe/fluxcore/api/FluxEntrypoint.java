package me.quickscythe.fluxcore.api;

import me.quickscythe.fluxcore.FluxInitializer;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FluxEntrypoint {

    private JavaMod mod;
    private final Logger LOGGER = LoggerFactory.getLogger(FluxEntrypoint.class);

    public void run(EntrypointContainer<FluxEntrypoint> entrypoint) {
        ModContainer container = entrypoint.getProvider();
        mod = new JavaMod(container.getMetadata());
        LOGGER.info("Initializing {} v{} ({})...", mod.getName(), mod.getVersion(), mod.getId());
        onInitialize();
//        LOGGER.info("Initializing {} v{} ({})...", mod.getName(), mod.getVersion(), mod.getId());
    }

    public JavaMod getMod() {
        return mod;
    }

    public abstract void onInitialize();

}
