package de.shiirroo.islands.event.menu.menus.skill;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.event.menu.*;
import de.shiirroo.islands.event.menu.menus.skill.SkillUpgradeMenu;
import de.shiirroo.islands.gamedata.game.skills.SkillTyp;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class SkillMenu extends Menu {
    public SkillMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public static HashMap<UUID, Menu> skillUpgradeMenu = new HashMap<>();

    @Override
    public String getMenuName() {
        return ChatColor.AQUA  + "Skill";
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
            back();
        } else if(Objects.equals(e.getCurrentItem(), CLOSE_ITEM)){
            inventory.close();
        } else {
            for (SkillTyp skillTyp : SkillTyp.values()) {
                if (Objects.equals(e.getCurrentItem(), skillTyp.getItemStack())){
                    if(skillUpgradeMenu.containsKey(uuid)) skillUpgradeMenu.get(uuid).setBack(true).setName(skillTyp.name()).open();
                    else skillUpgradeMenu.put(uuid, MenuManager.getMenu(SkillUpgradeMenu.class, uuid).setBack(true).setName(skillTyp.name()).open());
                }
            }
        }




    }

    @Override
    public void setMenuItems() {

        inventory.setItem(11, SkillTyp.Fabrication.getItemStack());

        inventory.setItem(15, SkillTyp.Economy.getItemStack());

        inventory.setItem(29, SkillTyp.Magic.getItemStack());

        inventory.setItem(33, SkillTyp.Farming.getItemStack());

        inventory.setItem(22, SkillUpgradeMenu.getPointsLeft(getPlayer().getLevel(), Objects.requireNonNull(IslandsPlugin.getGameData(uuid)).getGameSkills()));


        if(hasBack){
            inventory.setItem(49, BACK_ITEM);
        } else {
            inventory.setItem(49, CLOSE_ITEM);
        }
        setFillerGlass(false);
    }


    @Override
    public @NotNull Inventory getInventory() {
        return super.getInventory();
    }
}
