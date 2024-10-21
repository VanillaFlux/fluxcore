package me.quickscythe.fluxcore.api;

import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

public class FluxEntrypoint {

    private JavaMod mod;

    public void run(EntrypointContainer<FluxEntrypoint> entrypoint) {
        ModContainer container = entrypoint.getProvider();
        mod = new JavaMod(container.getMetadata());
    }

    public JavaMod getMod() {
        return mod;
    }

}
