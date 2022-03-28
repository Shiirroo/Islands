package de.shiirroo.islands.gamedata.game.items.gameitems.tools;

import de.shiirroo.islands.gamedata.game.blocks.DropBlockList;
import de.shiirroo.islands.gamedata.game.chunk.chunks.ChunkPresetList;
import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public abstract class ToolCreator extends Item implements Serializable {

    public abstract double attackDamage();


    public abstract ToolMaterial getToolMaterial();


    public abstract void useTool(Player player);

    public abstract ToolTyp setToolTyp();

    public Integer slot;

    public ToolCreator setSlot(Integer slot) {
        this.slot = slot;
        return this;
    }

    public ItemStack getItemStack(){
        Long l = Calendar.getInstance().getTime().getTime();
        ItemStack itemStack = setItemStack();
        if(itemRarity() != null && itemStack.getItemMeta() != null){
            ItemMeta itemMeta = itemStack.getItemMeta();
            String s = "";
            if(itemRecipe() != null) s = "⚒";
            itemMeta.setDisplayName(itemRarity().getChatColor() + itemMeta.getDisplayName());
            ArrayList<String> lore = new ArrayList<>();
            if(itemMeta.getLore() != null) {
                lore.addAll(itemMeta.getLore());
            }
            lore.add("");

            if(setToolTyp() != null && getToolAttackDamage() > 0){
                lore.addAll(List.of(ChatColor.RED + "Attack Damage: " + ChatColor.DARK_RED + getToolAttackDamage()));
            }
            if(setToolTyp() != null && !setToolTyp().equals(ToolTyp.SWORD) && getToolItemSpeed() > 0){
                lore.addAll(List.of(ChatColor.YELLOW + "Mining Speed: " + ChatColor.GREEN + getToolItemSpeed()));
            }if(setToolTyp() != null && !setToolTyp().equals(ToolTyp.SWORD)){
                lore.addAll(List.of(ChatColor.BLUE + "Mining Level: " + ChatColor.AQUA + getToolMaterial()));
            }


            lore.addAll(Arrays.asList("","" + itemRarity().getChatColor()  + ChatColor.BOLD + itemRarity().name()  +  ChatColor.GRAY + " " +s));

            itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        }
        //System.out.println("getItemStack: " +(Calendar.getInstance().getTime().getTime() - l) + this);

        return itemStack;
    }

    public double getToolAttackDamage(){
        double attack = 0;
        if(getToolMaterial() != null){
            attack += getToolMaterial().getMuli();
            attack += attackDamage();
            if(setItemStack() != null){
                if(setItemStack().getEnchantments().get(Enchantment.DAMAGE_ALL) != null){
                    attack += 1 + Math.pow(setItemStack().getEnchantments().get(Enchantment.DAMAGE_ALL),2);
                }
            }
        }
        return attack;
    }


    private Integer getToolItemSpeed(){
        int speed = 0;
        if(getToolMaterial() != null){
            speed += getToolMaterial().getMuli();
            if(setItemStack() != null){
                if(setItemStack().getEnchantments().get(Enchantment.DIG_SPEED) != null){
                    speed += 1 + Math.pow(setItemStack().getEnchantments().get(Enchantment.DIG_SPEED),2);
                }
            }
        }
        return speed;
    }



    protected HashSet<Material> canDestory() {
        HashSet<Material> destory = new HashSet<>();

        ChunkPresetList chunkPresetList = null;
        try {
            chunkPresetList = ChunkPresetList.class.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (chunkPresetList != null){
           chunkPresetList.getChunkPresetList().forEach(chunkCreator ->
                   chunkCreator.getChunkMaterialList().forEach(blockCreator -> {
                       if (blockCreator.getBlockCreator().breakableTool() !=  null && (blockCreator.getBlockCreator().breakableTool().equals(setToolTyp()) || blockCreator.getBlockCreator().breakableTool().equals(ToolTyp.ALL))) {
                           if(getToolMaterial() == blockCreator.getBlockCreator().breakableToolMaterial() || getToolMaterial().getBreakable().contains(blockCreator.getBlockCreator().breakableToolMaterial()))
                           destory.add(blockCreator.getItemStack().getType());
                       }
                   }));

            DropBlockList blockList = new DropBlockList();
            blockList.getDropBlockList().forEach(blockDataCreator ->{
                if(getToolMaterial() == blockDataCreator.getBlockCreator().breakableToolMaterial() || getToolMaterial().getBreakable().contains(blockDataCreator.getBlockCreator().breakableToolMaterial()))  destory.add(blockDataCreator.getMaterial());
            });
        }
        return destory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToolCreator toolCreator)) return false;
        return this.setToolTyp().equals(toolCreator.setToolTyp())
                && this.getToolItemSpeed().equals(toolCreator.getToolItemSpeed())
                && this.getToolMaterial().equals(toolCreator.getToolMaterial());
    }

}
