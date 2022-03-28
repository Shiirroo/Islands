package de.shiirroo.islands.gamedata.game.items.etc;

import de.shiirroo.islands.gamedata.game.Rarity;

import de.shiirroo.islands.gamedata.game.items.gameitems.RecipeCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolMaterial;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolTyp;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuItem extends ToolCreator implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public double attackDamage() {
        return 0;
    }

    @Override
    public ToolMaterial getToolMaterial() {
        return null;
    }


    @Override
    public void useTool(Player player) {

    }

    @Override
    public ToolTyp setToolTyp() {
        return ToolTyp.MENU;
    }

    @Override
    public int craftItemAmount() {
        return 1;
    }

    @Override
    public Rarity itemRarity() {
        return null;
    }


    @Override
    public List<RecipeCreator> itemRecipe() {
        return null;
    }


    @Override
    public ItemStack setItemStack() {
        ItemStack itemStack = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Menu");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setLore(new ArrayList<>());
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
