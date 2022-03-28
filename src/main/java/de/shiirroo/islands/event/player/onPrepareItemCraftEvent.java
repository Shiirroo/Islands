package de.shiirroo.islands.event.player;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.SaveGameData;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.Serializable;

public class onPrepareItemCraftEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void PrepareItemCraftEvent(PrepareItemCraftEvent event) {
        event.getInventory().setResult(null);
    }
}