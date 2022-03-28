package de.shiirroo.islands.event.player;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.event.menu.Menu;
import de.shiirroo.islands.event.menu.MenuManager;
import de.shiirroo.islands.event.menu.MenuManagerException;
import de.shiirroo.islands.event.menu.MenuManagerNotSetupException;
import de.shiirroo.islands.event.menu.menus.WorldCreatorMenu;
import de.shiirroo.islands.gamedata.game.player.GamePlayers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.HashMap;
import java.util.UUID;

public class onPlayerInteractEntityEvent implements Listener {

    public static HashMap<UUID, Menu> worldCreatorMenuHashMap = new HashMap<>();


    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        if(event.getPlayer().getWorld().getName().equalsIgnoreCase("world")
                && event.getRightClicked().getType().equals(EntityType.VILLAGER)
                && event.getRightClicked().getCustomName() != null
                && event.getRightClicked().getCustomName().equalsIgnoreCase(ChatColor.GOLD + "Teleport to "+ IslandsPlugin.getPlugin().getName() + " World")){

            if(worldCreatorMenuHashMap.containsKey(event.getPlayer().getUniqueId())){
                worldCreatorMenuHashMap.get(event.getPlayer().getUniqueId()).open();
            } else {
                try {
                    worldCreatorMenuHashMap.put(event.getPlayer().getUniqueId(), MenuManager.getMenu(WorldCreatorMenu.class, event.getPlayer().getUniqueId()).open());
                } catch (MenuManagerException | MenuManagerNotSetupException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void refreshWorldCreatorMenu(GamePlayers gamePlayers){
        for (UUID uuid:gamePlayers.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null) {
                if (worldCreatorMenuHashMap.containsKey(uuid)) {
                    Menu m = worldCreatorMenuHashMap.get(uuid);
                    if (player.getOpenInventory().getTopInventory().equals(m.getInventory())) {
                        m.setMenuItems();
                    }
                }
            }
        }
    }
}
