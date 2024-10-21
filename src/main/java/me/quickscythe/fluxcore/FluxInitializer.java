package me.quickscythe.fluxcore;

import me.quickscythe.fluxcore.api.ApiManager;
import me.quickscythe.fluxcore.api.FluxEntrypoint;
import me.quickscythe.fluxcore.api.data.StorageManager;
import me.quickscythe.fluxcore.listeners.ServerListener;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class FluxInitializer extends FluxEntrypoint {
    @Override
    public void onInitialize() {
        ServerListener listener = new ServerListener();
        ServerPlayConnectionEvents.JOIN.register(listener);
        ServerPlayConnectionEvents.DISCONNECT.register(listener);
        StorageManager.init(getMod());
        ApiManager.init();
    }
}
