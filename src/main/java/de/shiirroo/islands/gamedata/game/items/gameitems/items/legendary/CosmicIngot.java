package de.shiirroo.islands.gamedata.game.items.gameitems.items.legendary;

import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.items.gameitems.RecipeCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.epic.CosmicShard;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemsTyp;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.epic.MoltenLava;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class CosmicIngot extends ItemCreator {


    @Override
    public Rarity itemRarity() {
        return Rarity.LEGENDARY;
    }

    @Override
    public int craftItemAmount() {
        return 1;
    }


    @Override
    public List<RecipeCreator> itemRecipe() {
        return List.of(new RecipeCreator().addItems(new CosmicShard(), 2).addItems(new MoltenLava(), 1));
    }


    @Override
    public ItemStack setItemStack() {
        ItemStack itemStack = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Cosmic Ingot");
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
