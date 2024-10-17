package me.quickscythe.fluxcore.utils;

import me.quickscythe.fluxcore.Initializer;
import me.quickscythe.fluxcore.utils.data.StorageManager;
import me.quickscythe.fluxcore.utils.logger.LoggerUtils;
import me.quickscythe.fluxcore.utils.sql.SqlDatabase;
import me.quickscythe.fluxcore.utils.sql.SqlUtils;

public class CoreUtils {

    private static LoggerUtils loggerUtils;


    public static void init(Initializer initializer){
        loggerUtils = new LoggerUtils();
        loggerUtils.log("Starting " + initializer.NAME + " v" + initializer.VERSION + " (" + initializer.ID + ")...");
        SqlUtils.createDatabase("core", new SqlDatabase(SqlUtils.SQLDriver.MYSQL, "sql.vanillaflux.com", "vanillaflux", 3306, "sys", "9gGKGqthQJ&!#DGd"));
        SqlUtils.getDatabase("core");
        StorageManager.init();

    }

    public static LoggerUtils getLoggerUtils() {
        return loggerUtils;
    }
}
