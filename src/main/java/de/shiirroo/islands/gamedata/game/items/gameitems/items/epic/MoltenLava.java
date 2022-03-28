package de.shiirroo.islands.gamedata.game.items.gameitems.items.epic;

import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.items.gameitems.RecipeCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemsTyp;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MoltenLava extends ItemCreator {


    @Override
    public Rarity itemRarity() {
        return Rarity.EPIC;
    }

    @Override
    public int craftItemAmount() {
        return 1;
    }


    @Override
    public List<RecipeCreator> itemRecipe() {
        return null;
    }


    @Override
    public ItemStack setItemStack() {
        ItemStack itemStack = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Molten Lava");
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
        return ItemsTyp.LOOT;
    }
}