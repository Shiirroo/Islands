package de.shiirroo.islands.event.player;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.gamedata.GameData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class onPlayerDeathEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        GameData gameData = IslandsPlugin.getGameData(event.getPlayer().getUniqueId());
        if(gameData != null){
            event.getDrops().removeAll(gameData.getGamePlayers().getGameItems().getItemStackList());
            player.teleport(player.getWorld().getSpawnLocation());
        }
    }
}
