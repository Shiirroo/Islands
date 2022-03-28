package de.shiirroo.islands.gamedata.game.items.gameitems.items;

import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ItemCreator extends Item implements Serializable {

    public abstract ItemsTyp setItemsTyp();


    public ItemStack getItemStack(){
        ItemStack itemStack = setItemStack();
        if(itemRarity() != null && itemStack.getItemMeta() != null){
            ItemMeta itemMeta = itemStack.getItemMeta();
            StringBuilder s = new StringBuilder();
            if(itemRecipe() != null) s.append("⚒");
            if(setItemsTyp() != null && setItemsTyp().equals(ItemsTyp.FOOD)) s.append("♨");
            if(setItemsTyp() != null && setItemsTyp().equals(ItemsTyp.FUEL)) s.append("🔥");
            if(setItemsTyp() != null && setItemsTyp().equals(ItemsTyp.LOOT)) s.append("⚔");

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
        if (!(o instanceof ItemCreator itemCreator)) return false;
        return this.getItemStack().equals(itemCreator.getItemStack()) && this.setItemsTyp().equals(itemCreator.setItemsTyp());
    }
}
