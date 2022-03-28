package de.shiirroo.islands.event.menu.menus;

import de.shiirroo.islands.event.menu.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class FurnaceMenu extends Menu {
    public FurnaceMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }


    @Override
    public String getMenuName() {
        return ChatColor.DARK_GRAY +"BURN!!";
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.CHEST;
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public boolean cancelAllClicks() {
        return false;
    }

    @Override
    public void handleMenuClickEvent(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {

        if(e.getClickedInventory() != null && e.getClickedInventory().equals(inventory) && e.getCurrentItem() != null && e.getCurrentItem().getType().equals(FILLER_GLASS.getType())) {

            e.setCancelled(true);
        }
    }


    @Override
    public void setMenuItems() {
        setFillerGlass(true);

        inventory.setItem(10,null);
        inventory.setItem(11,null);
        inventory.setItem(12,null);

        inventory.setItem(14,new ItemStack(Material.CLOCK));

        inventory.setItem(16,null);

        inventory.setItem(29,null);
        inventory.setItem(38,new ItemStack(Material.SOUL_CAMPFIRE));

    }



}
