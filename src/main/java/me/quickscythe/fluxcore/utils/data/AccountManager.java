package me.quickscythe.fluxcore.utils.data;

import json2.JSONObject;
import me.quickscythe.fluxcore.utils.CoreUtils;
import me.quickscythe.fluxcore.utils.data.api.DataManager;
import me.quickscythe.fluxcore.utils.logger.LoggerUtils;
import me.quickscythe.fluxcore.utils.sql.SqlUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.biome.source.BiomeAccess;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;

public class AccountManager extends DataManager {

    private final File playerFolder;

    public AccountManager() {
        super("playerdata");
        playerFolder = new File(StorageManager.getConfigFolder(), getName());
        if (!playerFolder.exists()) {
            CoreUtils.getLoggerUtils().log("Creating " + getName() + " folder: " + playerFolder.mkdir());
            CoreUtils.getLoggerUtils().log("Syncing files.");
            syncFiles();
            CoreUtils.getLoggerUtils().log("Files synced.");
        }
    }

    private void syncFiles() {
        try {
            ResultSet rs = SqlUtils.getDatabase("core").query("SELECT username,uuid FROM users");
            int i = 0;
            while (rs.next()) {
                File file = new File(playerFolder, rs.getString("uuid") + ".json");
                if (!file.exists())
                    CoreUtils.getLoggerUtils().log("File doesn't exist. Creating: " + file.createNewFile());
                JSONObject json = new JSONObject();
                String uuid = rs.getString("uuid");
                String username = rs.getString("username");
                InternalStorage storage = StorageManager.getStorage();

                json.put("username", username);
                json.put("qid", i);

                storage.set(getName() + "." + uuid, json);
                storage.write(getName() + "." + uuid);
                i = i + 1;
            }
        } catch (SQLException e) {

            CoreUtils.getLoggerUtils().log(LoggerUtils.LogLevel.ERROR, "There was an error syncing files.");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(ServerPlayerEntity player, JSONObject json) {
        ResultSet rs = SqlUtils.getDatabase("core").query("SELECT * FROM users WHERE uuid='" + player.getUuid() + "';");
        try {
            if (rs.next()) {
                CoreUtils.getLoggerUtils().log("Record exists. Updating.");
                String sql = "UPDATE users SET username=\"" + player.getName().getString() + "\",last_seen=\"" + new Date().getTime() + "\",json=\"" + SqlUtils.escape(json.toString()) + "\" WHERE uuid=\"" + player.getUuid() + "\";";
                CoreUtils.getLoggerUtils().log("Result: " + SqlUtils.getDatabase("core").update(sql));
            } else {

                CoreUtils.getLoggerUtils().log("No record. Creating.");
                String sql = "INSERT INTO users(uuid,username,discord_key,discord_id,password,last_seen,json) VALUES ('" + player.getUuid() + "','" + player.getName().getString() + "','null','null','null','" + new Date().getTime() + "','" + SqlUtils.escape(json.toString()) + "');";
                CoreUtils.getLoggerUtils().log("Result: " + SqlUtils.getDatabase("core").input(sql));
            }
            rs.close();
            StorageManager.getStorage().load("playerdata." + player.getUuid());
            StorageManager.getStorage().set("playerdata." + player.getUuid() + ".username", player.getName().getString());
        } catch (SQLException e) {
            throw new RuntimeException("There was an error saving " + player.getName() + " data.");
        }

    }

    public File getPlayerFolder() {
        return playerFolder;
    }

    public JSONObject getData(UUID uid) {
        ResultSet rs = SqlUtils.getDatabase("core").query("SELECT * FROM users WHERE UUID='" + uid.toString() + "';");
        try {
            if (rs.next()) {
                JSONObject r = new JSONObject(rs.getString("json"));
                r.put("uuid", uid.toString());
                r.put("username", rs.getString("username"));
                r.put("discord_id", rs.getString("discord_id"));
                r.put("discord_key", rs.getString("discord_key"));
                r.put("qid", getQuickID(uid));
                return new JSONObject(rs.getString("json"));
            }
        } catch (SQLException e) {
            CoreUtils.getLoggerUtils().log(LoggerUtils.LogLevel.ERROR, "There was an error getting data for " + uid);
        }
        return new JSONObject();
    }

    public String getUsername(UUID uuid) {
        if (StorageManager.getStorage().get("fluxdata.playerdata." + uuid) == null) {
            StorageManager.getStorage().load("playerdata." + uuid);
        }
        return StorageManager.getStorage().getString("fluxdata.playerdata." + uuid + ".username");
    }

    public int getQuickID(UUID uuid) {
        if (StorageManager.getStorage().get("fluxdata.playerdata." + uuid + ".qid") == null) {
            StorageManager.getStorage().load("playerdata." + uuid);
        }
        return StorageManager.getStorage().getInt("fluxdata.playerdata." + uuid + ".qid");
    }

    public boolean isAltOrShadow(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        assert server != null;
        if (!server.getPlayerManager().getWhitelist().isAllowed(player.getGameProfile())) {
            CoreUtils.getLoggerUtils().log("Player is not whitelisted. Skipping...");
            return true;
        }
        InternalStorage storage = StorageManager.getStorage();
        JSONObject alts = (JSONObject) storage.load("data.alts");
        for (String key : alts.keySet()) {
            if (alts.getString(key).equalsIgnoreCase(player.getName().getString())) {
                CoreUtils.getLoggerUtils().log("Player is an alt of " + ((AccountManager) StorageManager.getDataManager("playerdata")).getUsername(UUID.fromString(key)) + ". Skipping...");
                return true;
            }
        }
        return false;
    }

    public Map<String, JSONObject> getAccounts() {
        Map<String, JSONObject> accounts = new HashMap<>();
        for(File file : playerFolder.listFiles()) {
            if(file.getName().endsWith(".json")) {
                String name = file.getName().replace(".json", "");
                JSONObject json = (JSONObject) StorageManager.getStorage().load("playerdata." + name);
                accounts.put(name, json);
            }
        }
        return accounts;
    }
}
