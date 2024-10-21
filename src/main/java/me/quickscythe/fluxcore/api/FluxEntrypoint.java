package me.quickscythe.fluxcore.api;

import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

public class FluxEntrypoint {

    public static void run(EntrypointContainer<FluxEntrypoint> entrypoint) {
        ModContainer mod = entrypoint.getProvider();
        System.out.println("Hello from " + mod.getMetadata().getId() + "!");
    }

}
