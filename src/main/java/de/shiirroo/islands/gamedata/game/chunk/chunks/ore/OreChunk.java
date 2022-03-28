package de.shiirroo.islands.gamedata.game.chunk.chunks.ore;

import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.chunk.GameBlock;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import de.shiirroo.islands.gamedata.game.chunk.GameStructure;
import de.shiirroo.islands.gamedata.game.blocks.BlockDataCreator;
import de.shiirroo.islands.gamedata.game.chunk.chunks.ChunkCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Coal_Ore;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Copper_Ore;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Granite;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Gravel;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.rare.Gold_Ore;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Iron_Ore;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Stone;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.common.Broken_Iron;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.epic.Gold_Ingot;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.uncommon.Coal;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OreChunk extends ChunkCreator implements Serializable {
    @Serial
    private static final long serialVersionUID = 6871948464050295099L;

    @Override
    public ArrayList<BlockDataCreator> getChunkMaterialList() {
        return new ArrayList<>(List.of(
                new BlockDataCreator(new Iron_Ore()).addDropItems(new Broken_Iron()),
                new BlockDataCreator(new Coal_Ore()).addDropItems(new Coal())));
    }

    @Override
    public String chunkFileName() {
        int r = Utilis.generateRandomInt(20);
        return "randomArea_" + r;
    }

    @Override
    public ItemStack chunkDisplayItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_ORE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Ore Island");
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
    public int ChunkPlaceSize() {
        return 5;
    }

    @Override
    public List<BlockCreator> placedOn() {
        return Arrays.asList(new Stone(), new Gravel(), new Granite());
    }

    @Override
    public Rarity rarity() {
        return Rarity.UNCOMMON;
    }

    @Override
    public GameStructure getGameStructure(int x, int z, GameChunk gameChunk) {
        ArrayList<GameBlock> chunkBlockList = new ArrayList<>();
        chunkBlockList.add(new GameBlock(getChunkMaterialList().get(Utilis.generateRandomInt(2)).getBlockDataString(), x, 51, z));
        return new GameStructure(chunkBlockList, gameChunk, Utilis.generateRandomInt(10) + 1);
    }

    @Override
    protected boolean checkPlaceChunk(int x, int z, Chunk c) {
        return (placedOn().stream().anyMatch(blockCreator -> blockCreator.getItemStack().getType().equals(c.getBlock(x,50,z).getType()))
                && (c.getWorld().getPlayers().stream().noneMatch(player -> player.getLocation().getX() == x && player.getLocation().getZ() == z)));
    }
}
