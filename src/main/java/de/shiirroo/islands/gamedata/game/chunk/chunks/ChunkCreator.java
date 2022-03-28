package de.shiirroo.islands.gamedata.game.chunk.chunks;

import de.shiirroo.islands.command.subcommands.chunk.ChunkCreatorData;
import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.blocks.BlockDataCreator;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import de.shiirroo.islands.gamedata.game.chunk.GameStructure;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public abstract class ChunkCreator implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    public abstract ArrayList<BlockDataCreator> getChunkMaterialList();

    protected abstract String chunkFileName();

    public abstract List<BlockCreator> placedOn();

    public abstract Rarity rarity();

    public abstract int ChunkPlaceSize();

    public abstract ItemStack chunkDisplayItem();

    public abstract GameStructure getGameStructure(int x, int z, GameChunk gameChunk);

    public HashSet<Material> getChunkMaterialHashSet(){
        HashSet<Material> hashSet = new HashSet<>();
        getChunkMaterialList().forEach(blockDataCreator -> hashSet.add(blockDataCreator.getMaterial()));
        return hashSet;
    }

    public HashSet<BlockCreator> getPlacedOnHashSet(){
        return new HashSet<>(placedOn());
    }

    public ChunkCreatorData getChunkCreatorData(){
        if(chunkFileName() != null) {
            String steam = "/ser/" + chunkFileName() + ".ser";
            InputStream ioStream = this.getClass().getResourceAsStream(steam);
            if (ioStream != null) {
                BukkitObjectInputStream oos = null;
                try {
                    oos = new BukkitObjectInputStream(ioStream);
                    ChunkCreatorData foragerChunk = (ChunkCreatorData) oos.readObject();
                    oos.close();
                    return foragerChunk;
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    protected abstract boolean checkPlaceChunk(int x, int z,Chunk c);


    public void checkChunkBlock(int x, int z, GameChunk gameChunk, Chunk c){
        if(checkPlaceChunk(x, z, c)) setCheckBlockList(getGameStructure(x,z,gameChunk), gameChunk, c);
    }

    protected void setCheckBlockList(GameStructure gameStructure, GameChunk gameChunk, Chunk c){
        if (gameStructure != null){
                if(gameStructure.getGameBlocks().stream().noneMatch(gameBlock -> gameBlock.getX() < 0 || gameBlock.getX() > 15 || gameBlock.getZ() < 0 || gameBlock.getZ() > 15)){
                    if(gameStructure.getGameBlocks().stream().allMatch(gameBlock -> c.getBlock(gameBlock.getX(), gameBlock.getY(), gameBlock.getZ()).getType().equals(Material.AIR)) &&
                    gameChunk.getGameStructureList().stream().noneMatch(g -> g.hasStructureCoordinates(gameStructure))) {
                        if(gameStructure.setStructure()) {
                            gameChunk.addGameGameStructure(gameStructure);
                        }
                }
            }
        }
    }

}
