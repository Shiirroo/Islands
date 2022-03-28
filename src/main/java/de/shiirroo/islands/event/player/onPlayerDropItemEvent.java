package de.shiirroo.islands.event.player;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.event.menu.menus.items.CraftingListMenu;
import de.shiirroo.islands.gamedata.GameData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class onPlayerDropItemEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        GameData gameData = IslandsPlugin.getGameData(event.getPlayer().getUniqueId());
        if(gameData != null){
            if (gameData.getGamePlayers().getGamePlayer(player.getUniqueId()) != null) {
                if(gameData.getGamePlayers().getGameItems().getItemStackList().contains(event.getItemDrop().getItemStack())){
                    if (!player.getGameMode().equals(GameMode.CREATIVE)) event.setCancelled(true);
                }
                if(CraftingListMenu.craftingMenu.containsKey(event.getPlayer().getUniqueId())){
                    Bukkit.getScheduler().scheduleSyncDelayedTask(IslandsPlugin.getPlugin(), () -> {
                        CraftingListMenu.refreshCraftingMenu(gameData.getGamePlayers(), CraftingListMenu.craftingMenu.get(event.getPlayer().getUniqueId()).getLastMenu().getItem() , true);
                    }, 1L);

                }
            }
        }
    }
}
