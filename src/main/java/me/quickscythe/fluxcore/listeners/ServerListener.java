package me.quickscythe.fluxcore.listeners;

import me.quickscythe.fluxcore.api.logger.LoggerUtils;
import org.json.JSONObject;
import me.quickscythe.fluxcore.api.data.AccountManager;
import me.quickscythe.fluxcore.api.data.StorageManager;
import me.quickscythe.fluxcore.api.sql.SqlUtils;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

import java.io.File;
import java.util.Date;

public class ServerListener implements ServerPlayConnectionEvents.Join, ServerPlayConnectionEvents.Disconnect {

    @Override
    public void onPlayDisconnect(ServerPlayNetworkHandler handler, MinecraftServer server) {
        AccountManager accountManager = (AccountManager) StorageManager.getDataManager("playerdata");
        if (accountManager.isAltOrShadow(handler.getPlayer())) {
            return;
        }
        LoggerUtils.getLogger().info("Player disconnected: {}", handler.player.getName().getString());
//        accountManager.save(handler.player, accountManager.getData(handler.player));
//        SessionUtils.saveSession(handler.player);
//        DataManager.getMapManager().updateMapMarkers(handler.getPlayer());
    }

    @Override
    public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        AccountManager accountManager = (AccountManager) StorageManager.getDataManager("playerdata");
        if (accountManager.isAltOrShadow(handler.player)) {
            return;
        }
        if (!new File(accountManager.getPlayerFolder(), handler.player.getUuid().toString() + ".json").exists()) {
            String sql = "INSERT INTO users(uuid,username,discord_key,discord_id,password,last_seen,json) VALUES ('" + handler.player.getUuid() + "','" + handler.player.getName().getString() + "','null','null','null','" + new Date().getTime() + "','{}');";
            SqlUtils.getDatabase("core").input(sql);
            JSONObject data = new JSONObject();
            data.put("username", handler.player.getName().getString());
            data.put("qid", accountManager.getAccounts().keySet().size());
            StorageManager.getStorage().set("playerdata." + handler.player.getUuid(), data);
            StorageManager.getStorage().saveAndRemove("playerdata." + handler.player.getUuid());

        }
    }
}
