package de.shiirroo.islands.event.player;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.SaveGameData;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.Serializable;
import java.util.Optional;

public class onPlayerLeave implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage(ChatColor.GRAY+ "["+ChatColor.RED +"-"+ ChatColor.GRAY + "] " + event.getPlayer().getDisplayName());
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        GameData gameData = IslandsPlugin.getGameData(event.getPlayer().getUniqueId());
        if(gameData != null) {
            gameData.getGamePlayers().updateGamePlayer(event.getPlayer());
            SaveGameData.save(gameData);

        }
    }
}
