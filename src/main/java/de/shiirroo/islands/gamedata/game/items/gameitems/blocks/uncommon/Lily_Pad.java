package de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon;

import de.shiirroo.islands.gamedata.game.Rarity;

import de.shiirroo.islands.gamedata.game.items.gameitems.RecipeCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Oak_Leaves;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolMaterial;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolTyp;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Lily_Pad extends BlockCreator {

    @Override
    public Rarity itemRarity() {
        return Rarity.UNCOMMON;
    }

    @Override
    public List<RecipeCreator> itemRecipe() {
        return List.of(new RecipeCreator().addItems(new Oak_Leaves(), 10));
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
        ItemStack itemStack = new ItemStack(Material.LILY_PAD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Lily Pad");
        ArrayList<String> lore = new ArrayList<>(Arrays.asList("", ChatColor.GRAY + "Place the lily pad on ", ChatColor.GRAY + "the water to walk over it."));
        HashSet<Material> place = new HashSet<>();
        place.add(Material.WATER);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        HashSet<Material> destory = new HashSet<>();
        destory.add(Material.LILY_PAD);
        itemMeta.setCanDestroy(destory);
        itemMeta.setCanPlaceOn(place);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
