package de.shiirroo.islands.gamedata.game.skills;

import de.shiirroo.islands.gamedata.game.skills.economy.*;
import de.shiirroo.islands.gamedata.game.skills.fabrication.*;
import de.shiirroo.islands.gamedata.game.skills.farming.*;
import de.shiirroo.islands.gamedata.game.skills.magic.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum SkillTyp {
    Magic(magicItemStacK(), getMagicSkillList(),ChatColor.DARK_PURPLE),
    Fabrication(fabricationItemStacK(), getFabricationSkillList(),ChatColor.DARK_GRAY),
    Farming(farmingItemStacK(), getFarmingSkillList(),ChatColor.GREEN),
    Economy(economyItemStacK(), getEconomySkillList(),ChatColor.GOLD);

    private final ItemStack itemStack;
    private final List<SkillCreator> skillCreatorList;
    private final ChatColor chatColor;

    SkillTyp(ItemStack itemStack, List<SkillCreator> skillCreatorList, ChatColor chatColor){
        this.itemStack = itemStack;
        this.skillCreatorList = skillCreatorList;
        this.chatColor = chatColor;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public List<SkillCreator> getSkillCreatorList() {
        return skillCreatorList;
    }

    private static ItemStack magicItemStacK(){
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(ChatColor.RED + "" +ChatColor.DARK_PURPLE +"Magic");
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(im);
        return itemStack;
    }

    private static ItemStack farmingItemStacK(){
        ItemStack itemStack = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(ChatColor.RED + "" +ChatColor.GREEN +"Farming");
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(im);
        return itemStack;
    }


    private static ItemStack economyItemStacK(){
        ItemStack itemStack = new ItemStack(Material.RAW_GOLD);
        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(ChatColor.RED + "" +ChatColor.GOLD +"Economy");
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(im);
        return itemStack;
    }


    private static ItemStack fabricationItemStacK(){
        ItemStack itemStack = new ItemStack(Material.FURNACE);
        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(ChatColor.RED + "" +ChatColor.DARK_GRAY +"Fabrication");
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(im);
        return itemStack;
    }


    private static List<SkillCreator> getFabricationSkillList() {
        return Arrays.asList(
                new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new Smelting3(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),null,null,
                new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new Smelting2(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),null,null,
                new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new Smelting(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),null,null,
                new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new StartingFabricationSkill(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated());
    }

    private static List<SkillCreator> getFarmingSkillList() {
        return Arrays.asList(
                new Mining_5(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new Foraging_5(),null,null,
                new Mining_4(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new Foraging_4(),null,null,
                new Mining_3(),new SkillNotCreated(),new SkillNotCreated(),new Geology(),new SkillNotCreated(),new SkillNotCreated(),new Foraging_3(),null,null,
                new Mining_2(),new Mining_1(),new Stone_Coin(),new StartingFarmingSkill(),new Wood_Coin(),new Foraging_1(),new Foraging_2());
    }

    private static List<SkillCreator> getMagicSkillList() {
        return Arrays.asList(
                new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),null,null,
                new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),null,null,
                new SkillNotCreated(),new SkillNotCreated(),new Evasion(),new Destruction(),new Lifesteal(),new SkillNotCreated(),new SkillNotCreated(),null,null,
                new SkillNotCreated(),new SkillNotCreated(),new MagicalBonus(),new MagicStartSkill(),new LifeBlessing(),new SkillNotCreated(),new SkillNotCreated());
    }


    private static List<SkillCreator> getEconomySkillList() {
        return Arrays.asList(
                new Slayer_5(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),null,null,
                new Slayer_4(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),null,null,
                new Slayer_3(),new SkillNotCreated(),new SkillNotCreated(),new Shop(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated(),null,null,
                new Slayer_2(),new Slayer_1(),new GoldenMonster(),new StartingEconomySkill(),new SkillNotCreated(),new SkillNotCreated(),new SkillNotCreated());
    }

}
