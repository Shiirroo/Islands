package de.shiirroo.islands.event.player;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.event.menu.menus.items.CraftingListMenu;
import de.shiirroo.islands.gamedata.GameData;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class onPlayerPickupItemEvent implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void PlayerPickupItemEvent(PlayerPickupItemEvent event) {
        GameData gameData = IslandsPlugin.getGameData(event.getPlayer().getUniqueId());
        if(gameData != null && event.getItem().canPlayerPickup()){
            if(CraftingListMenu.craftingMenu.containsKey(event.getPlayer().getUniqueId())){
                Bukkit.getScheduler().scheduleSyncDelayedTask(IslandsPlugin.getPlugin(), () -> {
                    CraftingListMenu.refreshCraftingMenu(gameData.getGamePlayers(), CraftingListMenu.craftingMenu.get(event.getPlayer().getUniqueId()).getLastMenu().getItem(), true);
                }, 1L);

            }
        }
    }
}