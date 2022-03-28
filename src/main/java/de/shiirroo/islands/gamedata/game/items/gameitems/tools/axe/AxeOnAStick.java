package de.shiirroo.islands.gamedata.game.items.gameitems.tools.axe;

import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.items.gameitems.RecipeCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Oak_Log;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolMaterial;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolTyp;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AxeOnAStick extends ToolCreator implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public double attackDamage() {
        return 0;
    }

    @Override
    public ToolMaterial getToolMaterial() {
        return ToolMaterial.WOOD;
    }


    @Override
    public void useTool(Player player) {

    }

    @Override
    public ToolTyp setToolTyp() {
        return ToolTyp.AXE;
    }

    @Override
    public Rarity itemRarity() {
        return Rarity.COMMON;
    }

    @Override
    public int craftItemAmount() {
        return 1;
    }


    @Override
    public List<RecipeCreator> itemRecipe() {
        return List.of(new RecipeCreator().addItems(new StartAxe(), 1).addItems(new Oak_Log(), 32));
    }

    @Override
    public ItemStack setItemStack() {
        ItemStack itemStack = new ItemStack(Material.WOODEN_AXE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setUnbreakable(true);
        meta.setDisplayName("Axe On A Stick");
        meta.addEnchant(Enchantment.DIG_SPEED,1,true);
        meta.setCanDestroy(canDestory());
        meta.setLore(new ArrayList<>());
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
