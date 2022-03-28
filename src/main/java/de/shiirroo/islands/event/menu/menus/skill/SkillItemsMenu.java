package de.shiirroo.islands.event.menu.menus.skill;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.event.menu.*;
import de.shiirroo.islands.event.menu.menus.skill.SkillMenu;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

public class SkillItemsMenu extends Menu {
    public SkillItemsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLACK + "Skill Item: " + ChatColor.GREEN+ name;
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
        return true;
    }

    @Override
    public void handleMenuClickEvent(InventoryClickEvent e) throws MenuManagerException, MenuManagerNotSetupException {
        if(Objects.equals(e.getCurrentItem(), BACK_ITEM)){
            if(SkillMenu.skillUpgradeMenu.containsKey(uuid))
                MenuManager.getPlayerMenuUtility(uuid).PopMenu();
                SkillMenu.skillUpgradeMenu.get(uuid).setBack(true).setName(getBacKName()).open();
        } else if(Objects.equals(e.getCurrentItem(), CLOSE_ITEM)){
            close();
        }  else if (Objects.equals(e.getCurrentItem(), getBackPage())) {
            if (site >= 1) {
                site--;
                setMenuItems();
            }
        } else if (Objects.equals(e.getCurrentItem(), getNextPage())) {
            if (site < skillUnlock.getUnlockItems().size() / 24) {
                site++;
                setMenuItems();
            }
        }
    }



    private int site = 0;

    @Override
    public void setMenuItems() {
        if(skillUnlock.getUnlockItems().size() == 0) close();
        Player p = Bukkit.getPlayer(uuid);
        if(p != null) {
            GameData gameData = IslandsPlugin.getGameData(uuid);
            if(gameData != null){
                if(site > 0)  inventory.setItem(45,getBackPage());
                else inventory.setItem(45,null);

                int slot = 0;
                inventory.setItem(4, getSitePage());

                    for (int i = 28 * site; i != 28 * (site + 1); i++) {
                        if(i >= skillUnlock.getUnlockItems().size()) break;
                        if (i != 28 * site && i % 7 == 0) slot += 2;
                        Item itemMeta = skillUnlock.getUnlockItems().get(i);
                        ItemStack itemStack = itemMeta.getItemStack();
                        inventory.setItem(slot + 10, itemStack);
                        slot++;

                }
                if (skillUnlock.getUnlockItems().size() > (28 * (site +1))) inventory.setItem(53, getNextPage());
                else inventory.setItem(53,null);

            }
        }

        if(hasBack){
            inventory.setItem(49, BACK_ITEM);
        } else {
            inventory.setItem(49, CLOSE_ITEM);
        }

        setFillerGlass(true);
    }




    public ItemStack getSitePage(){
        ItemStack itemStack = new ItemStack(Material.BOOK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + String.valueOf(site + 1 ) + ChatColor.GRAY +" | " + ChatColor.GOLD + (1 + skillUnlock.getUnlockItems().size()/28));
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(site+1);
        return itemStack;
    }


    public ItemStack getBackPage(){
        ItemStack playHead = Utilis.getPlayHead();
        SkullMeta im = (SkullMeta) playHead.getItemMeta();
        im.setDisplayName(ChatColor.GOLD + "Page: " + ChatColor.GREEN + (site + 1));
        im.setOwner("MHF_ArrowLeft");
        playHead.setItemMeta(im);
        return playHead;
    }

    public ItemStack getNextPage(){
        ItemStack playHead = Utilis.getPlayHead();
        SkullMeta im = (SkullMeta) playHead.getItemMeta();
        im.setDisplayName(ChatColor.GOLD +"Page: " + ChatColor.GREEN + (site + 2));
        im.setOwner("MHF_ArrowRight");
        playHead.setItemMeta(im);
        return playHead;
    }


}
