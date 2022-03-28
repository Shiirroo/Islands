package de.shiirroo.islands.gamedata.game.items.gameitems.tools.pickaxe;

import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.items.gameitems.RecipeCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.legendary.CosmicIngot;
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

public class CosmicPickaxe extends ToolCreator implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public double attackDamage() {
        return 3;
    }

    @Override
    public ToolMaterial getToolMaterial() {
        return ToolMaterial.COSMIC;
    }


    @Override
    public void useTool(Player player) {

    }

    @Override
    public ToolTyp setToolTyp() {
        return ToolTyp.PICKAXE;
    }

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
        return List.of(new RecipeCreator()
                .addItems(new GoldDigger(), 1)
                .addItems(new CosmicIngot(), 4));
    }

    @Override
    public ItemStack setItemStack() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setUnbreakable(true);
        meta.setDisplayName("Cosmic Pickaxe");
        meta.addEnchant(Enchantment.DIG_SPEED,5,true);
        meta.setCanDestroy(canDestory());
        meta.setLore(new ArrayList<>());
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
