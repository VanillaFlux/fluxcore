package me.quickscythe.fluxcore;

import me.quickscythe.fluxcore.api.FluxEntrypoint;
import me.quickscythe.fluxcore.listeners.ServerListener;
import me.quickscythe.fluxcore.utils.CoreUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;

public class Initializer extends FluxInitializer {

    @Override
    public void onInitialize() {
        this.NAME = "FluxCore";
        this.ID = "fluxcore";
        super.onInitialize();

        CoreUtils.init(this);
        FabricLoader.getInstance().getEntrypointContainers("fluxcore", FluxEntrypoint.class).forEach(obj -> obj.getEntrypoint().run(obj));
//        FabricLoader.getEntrypointContainers("coolname", MyEntrypoint.class).forEach(MyEntrypoint::run);


        ServerListener listener = new ServerListener();
        ServerPlayConnectionEvents.JOIN.register(listener);
        ServerPlayConnectionEvents.DISCONNECT.register(listener);
    }
}
