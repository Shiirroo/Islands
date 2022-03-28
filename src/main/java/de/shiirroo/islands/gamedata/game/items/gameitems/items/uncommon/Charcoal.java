package de.shiirroo.islands.gamedata.game.items.gameitems.items.uncommon;

import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.items.gameitems.RecipeCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Oak_Log;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemsTyp;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.common.Wheat;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class Charcoal extends ItemCreator {

    @Override
    public Rarity itemRarity() {
        return Rarity.UNCOMMON;
    }

    @Override
    public List<RecipeCreator> itemRecipe() {
        return List.of(new RecipeCreator().addItems(new Oak_Log(), 8));
    }

    @Override
    public int craftItemAmount() {
        return 1;
    }

    @Override
    public ItemStack setItemStack() {
        ItemStack itemStack = new ItemStack(Material.CHARCOAL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Charcoal");
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public ItemsTyp setItemsTyp() {
        return ItemsTyp.FUEL;
    }
}
