package de.shiirroo.islands.event.menu;

import de.shiirroo.islands.IslandsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;


public class MenuListener implements Listener{

    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        InventoryHolder inventoryHolder = null;
        try {
            inventoryHolder = (InventoryHolder) MenuManager.getPlayerMenuUtility(e.getWhoClicked().getUniqueId()).getData(e.getWhoClicked().getUniqueId().toString());
            InventoryHolder holder = e.getInventory().getHolder();
            if(holder instanceof Menu)
                MenuListeners(holder, e);
            else if(inventoryHolder instanceof Menu)
                MenuListeners(inventoryHolder, e);
            } catch (MenuManagerNotSetupException | MenuManagerException ex) {
            Bukkit.getLogger().info(IslandsPlugin.getprefix() + ChatColor.RED + "Something went wrong while menu click");
        }
    }

    private void MenuListeners(InventoryHolder iv, InventoryClickEvent e) throws MenuManagerException, MenuManagerNotSetupException {
            if (e.getCurrentItem() == null) return;
            Menu menu = (Menu) iv;
            if (menu.cancelAllClicks()) e.setCancelled(true);
            menu.handleMenuClickEvent(e);

    }
}