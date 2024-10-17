package me.quickscythe.fluxcore;

import me.quickscythe.fluxcore.listeners.ServerListener;
import me.quickscythe.fluxcore.utils.CoreUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class Initializer extends FluxInitializer {

    @Override
    public void onInitialize() {
        this.NAME = "FluxCore";
        this.ID = "fluxcore";
        CoreUtils.init(this);

        ServerListener listener = new ServerListener();
        ServerPlayConnectionEvents.JOIN.register(listener);
        ServerPlayConnectionEvents.DISCONNECT.register(listener);
    }
}
