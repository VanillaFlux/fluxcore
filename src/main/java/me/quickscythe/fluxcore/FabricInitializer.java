package me.quickscythe.fluxcore;

import me.quickscythe.fluxcore.api.FluxEntrypoint;
import me.quickscythe.fluxcore.utils.CoreUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class FabricInitializer implements ModInitializer {

    @Override
    public void onInitialize() {
        CoreUtils.init(this);
        FabricLoader.getInstance().getEntrypointContainers("fluxcore", FluxEntrypoint.class).forEach(obj -> obj.getEntrypoint().run(obj));
//        this.NAME = "FluxCore";
//        this.ID = "fluxcore";
//        super.onInitialize();
//
//
//        FabricLoader.getEntrypointContainers("coolname", MyEntrypoint.class).forEach(MyEntrypoint::run);
//
//
//        ServerListener listener = new ServerListener();
//        ServerPlayConnectionEvents.JOIN.register(listener);
//        ServerPlayConnectionEvents.DISCONNECT.register(listener);
    }
}
