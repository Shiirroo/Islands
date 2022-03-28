package de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon;

import de.shiirroo.islands.gamedata.game.Rarity;

import de.shiirroo.islands.gamedata.game.items.gameitems.RecipeCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolMaterial;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolTyp;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Cactus extends BlockCreator {


    @Override
    public Rarity itemRarity() {
        return Rarity.UNCOMMON;
    }


    @Override
    public List<RecipeCreator> itemRecipe() {return null;
    }

    @Override
    public ToolTyp breakableTool() {
        return ToolTyp.ALL;
    }

    @Override
    public int craftItemAmount() {
        return 1;
    }

    @Override
    public ToolMaterial breakableToolMaterial() {
        return ToolMaterial.WOOD;
    }

    @Override
    public double setBlockChanceMultiplayer() {
        return 0;
    }

    @Override
    public ItemStack setItemStack() {
        ItemStack itemStack = new ItemStack(Material.CACTUS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Cactus");
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
