package de.shiirroo.islands.gamedata.game.chunk.chunks.starting;

import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.chunk.GameBlock;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import de.shiirroo.islands.gamedata.game.chunk.GameStructure;
import de.shiirroo.islands.gamedata.game.blocks.BlockDataCreator;
import de.shiirroo.islands.gamedata.game.chunk.chunks.ChunkCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.*;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Stone;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.common.Melon_Slice;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.common.Wheat;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.uncommon.Apple;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartingChunk extends ChunkCreator implements Serializable {
    @Serial
    private static final long serialVersionUID = 6871948464050295099L;

    @Override
    public ArrayList<BlockDataCreator> getChunkMaterialList() {
        return new ArrayList<>(Arrays.asList(new BlockDataCreator(new Cobblestone()),
                new BlockDataCreator(new Hay_Block()).addDropItems(new Wheat(), 1,3),
                new BlockDataCreator(new Melon_Block()).addDropItems(new Melon_Slice(), 1,4),
                new BlockDataCreator(new Oak_Log()),
                new BlockDataCreator(new Oak_Leaves()).addDropWithChance(new Oak_Leaves(),1, 30).addDropWithChance(new Apple(), 1,5)));
    }

    @Override
    public String chunkFileName() {
        int r = Utilis.generateRandomInt(20);
        return "randomArea_" + r;
    }


    @Override
    public int ChunkPlaceSize() {
        return 10;
    }

    @Override
    public ItemStack chunkDisplayItem() {
        ItemStack itemStack = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Starting Island");
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
    public List<BlockCreator> placedOn() {
        return Arrays.asList(new Grass(), new Dirt());
    }

    @Override
    public Rarity rarity() {
        return Rarity.COMMON;
    }

    @Override
    public GameStructure getGameStructure(int x, int z, GameChunk gameChunk) {
        if (Utilis.generateRandomInt(2) == 1) {
            return createOther(x, z, gameChunk);
        }
        return createTree(x, z, gameChunk);
    }

    @Override
    protected boolean checkPlaceChunk(int x, int z, Chunk c) {
        return (placedOn().stream().anyMatch(blockCreator -> blockCreator.getItemStack().getType().equals(c.getBlock(x,50,z).getType()))
                && (c.getWorld().getPlayers().stream().noneMatch(player -> player.getLocation().getX() == x && player.getLocation().getZ() == z)));
    }

    private GameStructure createOther(int x, int z, GameChunk gameChunk) {
        ArrayList<GameBlock> chunkBlockList = new ArrayList<>();
        int randomOther = Utilis.generateRandomInt(3);
        chunkBlockList.add(new GameBlock(getChunkMaterialList().get(randomOther).getBlockDataString(), x, 51, z));
        return new GameStructure(chunkBlockList, gameChunk, 1);
    }


    private GameStructure createTree(int x, int z, GameChunk gameChunk){
        ArrayList<GameBlock> chunkBlockList = new ArrayList<>();
        int xp = Utilis.generateRandomInt(3) + 1;

        for (int i = 51; i < 55; i++) {
            chunkBlockList.add(new GameBlock(getChunkMaterialList().get(3).getBlockDataString(), x, i, z));
        }
        for(int xi = -1;xi < 2 ;xi++) {
            for (int zi = -1; zi < 2; zi++) {
                if (!(xi == 0 && zi == 0)) {
                    chunkBlockList.add(new GameBlock(getChunkMaterialList().get(4).getBlockDataString(), x + xi, 54, z + zi));
                }
            }
        }
        chunkBlockList.add(new GameBlock(getChunkMaterialList().get(4).getBlockDataString(), x , 55,  z));
        return new GameStructure(chunkBlockList, gameChunk, xp);
    }
}
