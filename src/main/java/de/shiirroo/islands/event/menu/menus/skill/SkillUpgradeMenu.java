package de.shiirroo.islands.event.menu.menus.skill;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.event.menu.*;
import de.shiirroo.islands.event.player.onPlayerInteractEvent;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.player.GamePlayers;
import de.shiirroo.islands.gamedata.game.skills.GameSkills;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillTyp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class SkillUpgradeMenu extends Menu {
    public SkillUpgradeMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLACK + "Skill: " + SkillTyp.valueOf(name).getChatColor() + name;
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
            close();
        } else {
            GameData gameData = IslandsPlugin.getGameData(uuid);
            if (gameData != null) {
                if(creatorSkillHashMap.containsKey(e.getCurrentItem())){
                    SkillCreator skill = creatorSkillHashMap.get(e.getCurrentItem());
                        if(e.getClick().isRightClick()) {
                            if(skill.skillUnlock() != null && skill.skillUnlock().getUnlockItems().size() > 0) MenuManager.getMenu(SkillItemsMenu.class, uuid).setSkillUnlock(skill.skillUnlock()).setBack(true).setName(skill.skillName()).setBackName(getName()).open();
                        } else if (!gameData.getGameSkills().getSkillList().contains(skill) && (skill.requiredSkill() == null ||gameData.getGameSkills().getSkillList().containsAll(skill.requiredSkill())) && getPlayer().getLevel() >= gameData.getGameSkills().getSkillList().size() +1) {
                                gameData.getGameSkills().addSkill(skill);
                                refreshSkillMenu(gameData.getGamePlayers());
                                onPlayerInteractEvent.refreshIslandMenu(gameData.getGamePlayers());
                    }
                }
            }
        }
    }



    public static void refreshSkillMenu(GamePlayers gamePlayers){
        for (UUID uuid:gamePlayers.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null) {
                if (SkillMenu.skillUpgradeMenu.containsKey(uuid)) {
                    Menu m = SkillMenu.skillUpgradeMenu.get(uuid);
                    m.setMenuItems();

                }
            }
        }
    }

    private final HashMap<ItemStack, SkillCreator> creatorSkillHashMap = new HashMap<>();


    @Override
    public void setMenuItems() {
        Player p = Bukkit.getPlayer(uuid);
        if(p != null) {
            GameData gameData = IslandsPlugin.getGameData(uuid);
            if(gameData != null){
                creatorSkillHashMap.clear();
                inventory.setItem(4, getPointsLeft(p.getLevel() ,gameData.getGameSkills()));
                List<SkillCreator> skillCreatorList = SkillTyp.valueOf(name).getSkillCreatorList();
                    for(int i = 0; i != skillCreatorList.size() ; i++){
                        SkillCreator skill = skillCreatorList.get(i);
                        if(skill != null) {
                            ItemStack itemStack;
                            if(skill.skillName() != null) {
                                    if (gameData.getGameSkills().getSkillList().contains(skill)){
                                        itemStack = getHasSkillItem(gameData,skill);
                                        creatorSkillHashMap.put(itemStack, skill);
                                    }
                                    else itemStack = getCanOwnSkill(skill, gameData, i);
                            } else {
                                itemStack = getSkillNotCreated(i);
                            }
                            inventory.setItem(i + 10, itemStack);
                        }
                }
            }
        }

        if(hasBack){
            inventory.setItem(49, BACK_ITEM);
        } else {
            inventory.setItem(49, CLOSE_ITEM);
        }

        setFillerGlass(true);
    }

    public ItemStack getCanOwnSkill(SkillCreator skill, GameData gameData, int i) {
        List<SkillCreator> skillCreatorList = SkillTyp.valueOf(name).getSkillCreatorList();
        ItemStack itemStack;
        if (skill.startSkill() || ((skillCreatorList.size() > i + 9 && skillCreatorList.get(i + 9) != null && gameData.getGameSkills().getSkillList().contains(skillCreatorList.get(i + 9)) ||
                (skillCreatorList.size() > i + 1 && skillCreatorList.get(i + 1) != null && gameData.getGameSkills().getSkillList().contains(skillCreatorList.get(i + 1)))) ||
                (i - 9 >= 0 && skillCreatorList.get(i - 9) != null && gameData.getGameSkills().getSkillList().contains(skillCreatorList.get(i - 9))) ||
                (i - 1 >= 0 && skillCreatorList.get(i - 1) != null && gameData.getGameSkills().getSkillList().contains(skillCreatorList.get(i - 1))))) {
            if(skill.requiredSkill() == null || (gameData.getGameSkills().getSkillList().containsAll(skill.requiredSkill()))){
                itemStack = getCanOwnSkillItem(skill,gameData, i);
            } else {
                itemStack = getBlockSkill(skill, gameData,i);
            }
            creatorSkillHashMap.put(itemStack, skill);
            return itemStack;
        }
        return getNotOwnSkill(skill, i);
    }



    protected ItemStack getHasSkillItem(GameData gameData,SkillCreator skillCreator){
        ItemStack itemStack = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + skillCreator.skillName());
        itemMeta.setLore(getLore(skillCreator, false,gameData, false));
        itemMeta.addEnchant(Enchantment.MENDING, 1 , true);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


    protected ItemStack getCanOwnSkillItem(SkillCreator skillCreator,GameData gameData, int i ){
        ItemStack itemStack = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + skillCreator.skillName());
        itemMeta.setLore(getLore(skillCreator, true,gameData, true));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    protected ItemStack getBlockSkill(SkillCreator skillCreator, GameData gameData, int i ){
        ItemStack itemStack = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + skillCreator.skillName());
        itemMeta.setLore(getLore(skillCreator, true,gameData, false));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public List<String> getLore(SkillCreator skillCreator, boolean requiredSkill, GameData gameData,boolean learn){
        List<String> lore = new ArrayList<>(List.of(""));
        List<String > botton = new ArrayList<>(List.of(""));
        if(learn) botton.add(ChatColor.GRAY + "Left-Click to learn");

        if(skillCreator.skillDescription() != null){
            lore.add(ChatColor.YELLOW  + "Description: " );
            String[] des = skillCreator.skillDescription().split("\n");
            for(String s : des) lore.add(ChatColor.GREEN + s);
        }


        if(skillCreator.requiredSkill() != null && requiredSkill){
            boolean needed = false;
            List<String> reqString = new ArrayList<>(List.of(" ", ChatColor.GOLD + "Skill required: " + (skillCreator.requiredSkill() == null ? " none" : "")));
            for (SkillCreator req: skillCreator.requiredSkill()) {
                if(gameData.getGameSkills().getSkillList().contains(req)){
                    reqString.add(ChatColor.GREEN +req.skillName());
                } else {
                    reqString.add(ChatColor.RED + req.skillName());
                    needed = true;
                }
            }
            if(needed) lore.addAll(reqString);

        }

        if(skillCreator.skillUnlock() != null && skillCreator.skillUnlock().getUnlockItems() != null && skillCreator.skillUnlock().getUnlockItems().size() > 0){
            lore.add("");
            lore.add(ChatColor.AQUA + "" +ChatColor.BOLD + "Items:");
            botton.add(ChatColor.GRAY + "Right-Click to View Items");

            for(Item item : skillCreator.skillUnlock().getUnlockItems()){
                lore.add(item.getItemStack().getItemMeta().getDisplayName());
            }
        }

        if(skillCreator.skillUnlock() != null && skillCreator.skillUnlock().getGameBonusType() != null && skillCreator.skillUnlock().getGameBonusType().size() > 0){
            lore.add("");
            lore.add(ChatColor.BLUE + "" +ChatColor.BOLD + "Bonus:");
            for(GameBonusTyps gameBonusTyps: skillCreator.skillUnlock().getGameBonusType().keySet()){
                lore.add(ChatColor.GOLD + gameBonusTyps.name() + ": " + ChatColor.GREEN + (skillCreator.skillUnlock().getGameBonusType().get(gameBonusTyps) / gameBonusTyps.getDeduction()) + " " + gameBonusTyps.getAddon());

            }
        }
        if(botton.size() > 0 ) lore.addAll(botton);

        return lore;
    }



    protected ItemStack getSkillNotCreated(int i){
        ItemStack itemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.DARK_GRAY +""+ ChatColor.BOLD + ChatColor.UNDERLINE+ "Skill not created : " + i);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    protected ItemStack getNotOwnSkill(SkillCreator skillCreator, int i){
        ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.DARK_GRAY +"UNKNOWN");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getPointsLeft(int i, GameSkills gameSkills){
        ItemStack itemStack = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN +""+ ChatColor.BOLD+ "Points left: " + ChatColor.DARK_PURPLE + Math.max(i  - (gameSkills.getSkillList() != null? gameSkills.getSkillList().size() : 0), 0));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
