package de.shiirroo.islands.gamedata.game.items;

import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.items.gameitems.RecipeCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public abstract class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected int enchantment = 0;

    public void setEnchantment(int i){
        this.enchantment = i;
    }

    public abstract int craftItemAmount();

    public abstract Rarity itemRarity();

    public abstract List<RecipeCreator> itemRecipe();

    protected abstract ItemStack setItemStack();

    public ItemStack getItemStack(){
        ItemStack itemStack = setItemStack();
        if(itemRarity() != null && itemStack.getItemMeta() != null){
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(itemRarity().getChatColor() + itemMeta.getDisplayName());
            ArrayList<String> lore = new ArrayList<>();
            if(itemMeta.getLore() != null) {
                lore.addAll(itemMeta.getLore());
            }
            lore.addAll(Arrays.asList("", "" + itemRarity().getChatColor()  + ChatColor.BOLD + itemRarity().name()));
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        if(this instanceof ToolCreator tool && item instanceof ToolCreator toolCreator){
            return tool.equals(toolCreator);

        } else if(this instanceof ItemCreator i && item instanceof ItemCreator itemCreator) {
            return i.equals(itemCreator);

        } else if(this instanceof BlockCreator block && item instanceof BlockCreator blockCreator){
            return block.equals(blockCreator);

        }
        return false;
    }

}
