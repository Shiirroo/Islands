package de.shiirroo.islands.gamedata.game.items.gameitems.items.epic;

import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.items.gameitems.RecipeCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.rare.Raw_Gold_Block;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemsTyp;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.uncommon.Coal;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class Gold_Ingot extends ItemCreator {


    @Override
    public Rarity itemRarity() {
        return Rarity.RARE;
    }

    @Override
    public int craftItemAmount() {
        return 1;
    }


    @Override
    public List<RecipeCreator> itemRecipe() {
        return List.of(new RecipeCreator().addItems(new Raw_Gold_Block(), 1).addItems(new Coal(), 4));
    }


    @Override
    public ItemStack setItemStack() {
        ItemStack itemStack = new ItemStack(Material.GOLD_INGOT);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Gold Ingot");
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
        return ItemsTyp.ORE;
    }
}
