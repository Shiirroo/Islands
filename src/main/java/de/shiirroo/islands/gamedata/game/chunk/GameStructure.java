package de.shiirroo.islands.gamedata.game.chunk;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GameStructure implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private final UUID uuid;
    private final ArrayList<GameBlock> gameBlocks = new ArrayList<>();
    private final GameChunk gameChunk;
    private final int xp;

    public GameStructure(GameStructure gameStructure){
        this.uuid = gameStructure.getUuid();
        this.gameBlocks.addAll(gameStructure.getGameBlocks());
        this.gameChunk = gameStructure.getGameChunk();
        this.xp = gameStructure.getXp();
    }

    public GameStructure(ArrayList<GameBlock> gameBlockList, GameChunk gameChunk, int xp){
        this.gameBlocks.addAll(gameBlockList);
        this.gameChunk = gameChunk;
        this.uuid = UUID.randomUUID();
        this.xp = xp;
    }

    public int getXp() {
        return this.xp;
    }

    public boolean removeBlock(int x, int y, int z, Material material){
        Optional<GameBlock> optionalGameBlock = this.gameBlocks.stream().filter(gameBlock -> gameBlock.getX() == x && gameBlock.getY() == y && gameBlock.getZ() == z && gameBlock.getBlockData().getMaterial().equals(material)).findFirst();
        if(optionalGameBlock.isPresent()){
            this.gameBlocks.remove(optionalGameBlock.get());
            return true;
        }
        return false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ArrayList<GameBlock> getGameBlocks() {
        return gameBlocks;
    }

    public GameChunk getGameChunk() {
        return gameChunk;
    }

    public boolean hasStructureCoordinates(GameStructure gameStructure){
        for(GameBlock gameBlock:getGameBlocks())
            if(gameStructure.getGameBlocks().stream().anyMatch(g -> g.sameCoordinates(gameBlock))) {
                return true;
            }
            return false;
    }

    public boolean setStructure(){
        World w = Bukkit.getWorld(gameChunk.getWorldName());
        if(w != null){
            for(GameBlock gameBlock : getGameBlocks()) {
                Chunk chunk = w.getChunkAt(gameChunk.getX(), gameChunk.getZ());
                gameBlock.setBlock(chunk, false);
            }
            return true;
        }
        return false;
    }
}
