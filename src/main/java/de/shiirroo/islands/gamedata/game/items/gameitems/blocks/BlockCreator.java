package de.shiirroo.islands.gamedata.game.items.gameitems.blocks;

import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolMaterial;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolTyp;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BlockCreator extends Item implements Serializable {

    public abstract ToolTyp breakableTool();

    public abstract ToolMaterial breakableToolMaterial();

    public abstract double setBlockChanceMultiplayer();


    public ItemStack getItemStack(){
        ItemStack itemStack = setItemStack();
        if(itemRarity() != null && itemStack.getItemMeta() != null){
            ItemMeta itemMeta = itemStack.getItemMeta();
            StringBuilder s = new StringBuilder();
            if(itemRecipe() != null) s.append("⚒");
            if(breakableTool() != null && breakableTool().equals(ToolTyp.AXE)) s.append("🪓");
            if(breakableTool() != null && breakableTool().equals(ToolTyp.PICKAXE))  s.append("⛏");

            itemMeta.setDisplayName(itemRarity().getChatColor() + itemMeta.getDisplayName());
            ArrayList<String> lore = new ArrayList<>();
            if(itemMeta.getLore() != null) {
                lore.addAll(itemMeta.getLore());
            }
            lore.addAll(Arrays.asList("", "" + itemRarity().getChatColor()  + ChatColor.BOLD + itemRarity().name() +  ChatColor.GRAY + " " +s));
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockCreator blockCreator)) return false;
        return this.getItemStack().equals(blockCreator.getItemStack()) && this.breakableToolMaterial().equals(blockCreator.breakableToolMaterial()) && this.breakableTool().equals(blockCreator.breakableTool());


    }

}
