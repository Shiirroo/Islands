package de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common;

import de.shiirroo.islands.gamedata.game.Rarity;

import de.shiirroo.islands.gamedata.game.items.gameitems.RecipeCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolMaterial;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolTyp;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class Oak_Leaves extends BlockCreator {
    @Override
    public Rarity itemRarity() {
        return Rarity.COMMON;
    }

    @Override
    public List<RecipeCreator> itemRecipe() {
        return null;
    }


    @Override
    public ToolTyp breakableTool() {
        return ToolTyp.AXE;
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
    protected ItemStack setItemStack() {
        ItemStack itemStack = new ItemStack(Material.OAK_LEAVES);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Oak Leaves");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
