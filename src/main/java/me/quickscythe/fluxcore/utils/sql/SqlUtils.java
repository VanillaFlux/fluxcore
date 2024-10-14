package me.quickscythe.fluxcore.utils.sql;

import java.util.HashMap;
import java.util.Map;

public class SqlUtils {

    private static final Map<String, SqlDatabase> databases = new HashMap<>();

    public static void createDatabase(String name, SqlDatabase db) {
        if (!db.init()) {
            System.out.println("There was an error registering database: " + name);
            return;
        }
        databases.put(name, db);
    }

    public static SqlDatabase getDatabase(String name) {
        return databases.getOrDefault(name, null);
    }

    public static String escape(String str) {

        String data = null;
        if (str != null && !str.isEmpty()) {
            str = str.replace("\\", "\\\\");
            str = str.replace("'", "\\'");
            str = str.replace("\0", "\\0");
            str = str.replace("\n", "\\n");
            str = str.replace("\r", "\\r");
            str = str.replace("\"", "\\\"");
            str = str.replace("\\x1a", "\\Z");
            data = str;
        }
        return data;

    }

    public enum SQLDriver {

        SQLITE("sqlite"),
        MYSQL("mysql");

        final String name;

        SQLDriver(String name) {
            this.name = name;
        }

    }

}