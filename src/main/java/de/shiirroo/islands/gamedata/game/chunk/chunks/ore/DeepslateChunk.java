package de.shiirroo.islands.gamedata.game.chunk.chunks.ore;

import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.blocks.BlockDataCreator;
import de.shiirroo.islands.gamedata.game.chunk.GameBlock;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import de.shiirroo.islands.gamedata.game.chunk.GameStructure;
import de.shiirroo.islands.gamedata.game.chunk.chunks.ChunkCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Coal_Ore;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Copper_Ore;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Granite;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Gravel;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.rare.Deepslate;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.rare.DeepslateCoal_Ore;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.rare.DeepslateIron;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Iron_Ore;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Stone;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.common.Broken_Iron;
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

public class DeepslateChunk extends ChunkCreator implements Serializable {
    @Serial
    private static final long serialVersionUID = 6871948464050295099L;

    @Override
    public ArrayList<BlockDataCreator> getChunkMaterialList() {
        return new ArrayList<>(Arrays.asList(
                new BlockDataCreator(new DeepslateIron()).addDropWithChance(new Broken_Iron(), 4, 100),
                new BlockDataCreator(new DeepslateCoal_Ore()).addDropWithChance(new Coal_Ore(), 4, 100)));
    }

    @Override
    public String chunkFileName() {
        int r = Utilis.generateRandomInt(20);
        return "randomArea_" + r;
    }

    @Override
    public ItemStack chunkDisplayItem() {
        ItemStack itemStack = new ItemStack(Material.DEEPSLATE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Deepslate Island");
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
        return 3;
    }

    @Override
    public ArrayList<BlockCreator> placedOn() {
        BlockCreator blockCreator = new Deepslate();

        return new ArrayList<>(Arrays.asList(blockCreator,blockCreator, new DeepslateIron()));
    }

    @Override
    public Rarity rarity() {
        return Rarity.EPIC;
    }

    @Override
    public GameStructure getGameStructure(int x, int z, GameChunk gameChunk) {
        ArrayList<GameBlock> chunkBlockList = new ArrayList<>();
        chunkBlockList.add(new GameBlock(getChunkMaterialList().get(Utilis.generateRandomInt(2)).getBlockDataString(), x, 51, z));
        return new GameStructure(chunkBlockList, gameChunk, Utilis.generateRandomInt(20) + 1);
    }

    @Override
    protected boolean checkPlaceChunk(int x, int z, Chunk c) {
        return (placedOn().stream().anyMatch(blockCreator -> blockCreator.getItemStack().getType().equals(c.getBlock(x,50,z).getType()))
                && (c.getWorld().getPlayers().stream().noneMatch(player -> player.getLocation().getX() == x && player.getLocation().getZ() == z)));
    }
}
