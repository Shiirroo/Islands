package de.shiirroo.islands.event.player;

import de.shiirroo.islands.event.menu.MenuManager;
import de.shiirroo.islands.event.menu.MenuManagerException;
import de.shiirroo.islands.event.menu.MenuManagerNotSetupException;
import de.shiirroo.islands.event.menu.menus.FurnaceMenu;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onInventoryOpenEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void InventoryOpenEvent(InventoryOpenEvent event) {
        if(event.getInventory().getType().equals(InventoryType.FURNACE)){
            try {
                event.setCancelled(true);
                MenuManager.getMenu(FurnaceMenu.class, event.getPlayer().getUniqueId()).setBack(false).open();
            } catch (MenuManagerException | MenuManagerNotSetupException e) {
                e.printStackTrace();
            }

        }
    }
}