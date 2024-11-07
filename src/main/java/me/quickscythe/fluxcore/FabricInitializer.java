package me.quickscythe.fluxcore;

import me.quickscythe.fluxcore.api.ApiManager;
import me.quickscythe.fluxcore.api.FluxEntrypoint;
import me.quickscythe.fluxcore.api.data.StorageManager;
import me.quickscythe.fluxcore.api.logger.LoggerUtils;
import me.quickscythe.fluxcore.api.sql.SqlDatabase;
import me.quickscythe.fluxcore.api.sql.SqlUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class FabricInitializer implements ModInitializer {

    @Override
    public void onInitialize() {
        SqlUtils.createDatabase("core", new SqlDatabase(SqlUtils.SQLDriver.MYSQL, "sql.vanillaflux.com", "vanillaflux", 3306, "sys", "9gGKGqthQJ&!#DGd"));
        SqlUtils.getDatabase("core");
        StorageManager.init();

        LoggerUtils.getLogger().info("FluxCore api is now initiating...");
        FabricLoader.getInstance().getEntrypointContainers("fluxcore", FluxEntrypoint.class).forEach(obj -> obj.getEntrypoint().run(obj));
    }
}
