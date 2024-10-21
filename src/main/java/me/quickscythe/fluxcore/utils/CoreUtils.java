package me.quickscythe.fluxcore.utils;

import me.quickscythe.fluxcore.FabricInitializer;
import me.quickscythe.fluxcore.FluxInitializer;
import me.quickscythe.fluxcore.api.FluxEntrypoint;
import me.quickscythe.fluxcore.api.data.StorageManager;
import me.quickscythe.fluxcore.api.logger.LoggerUtils;
import me.quickscythe.fluxcore.api.sql.SqlDatabase;
import me.quickscythe.fluxcore.api.sql.SqlUtils;

public class CoreUtils {

    private static LoggerUtils loggerUtils;


    public static void init(FabricInitializer initializer){
        loggerUtils = new LoggerUtils();
        loggerUtils.getLogger().info("Starting FluxCore api...");
//        loggerUtils.log("Starting " + initializer.NAME + " v" + initializer.VERSION + " (" + initializer.ID + ")...");
        SqlUtils.createDatabase("core", new SqlDatabase(SqlUtils.SQLDriver.MYSQL, "sql.vanillaflux.com", "vanillaflux", 3306, "sys", "9gGKGqthQJ&!#DGd"));
        SqlUtils.getDatabase("core");
    }

    public static void init(FluxEntrypoint initializer){
        StorageManager.init(initializer.getMod());
    }

    public static LoggerUtils getLoggerUtils() {
        return loggerUtils;
    }
}
