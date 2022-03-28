package de.shiirroo.islands.gamedata.game.chunk;

import de.shiirroo.islands.gamedata.game.chunk.chunks.ChunkCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameChunk implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private final String worldName;
    private final ArrayList<GameStructure> gameStructureList = new ArrayList<>();
    private final List<GameEntities> gameEntitiesList = new ArrayList<>();
    private final ChunkCreator chunkCreator;
    private final int x;
    private final int z;

    public GameChunk(GameChunk gameChunk){
        this.z = gameChunk.getZ();
        this.x = gameChunk.getX();
        this.worldName = gameChunk.getWorldName();
        this.gameStructureList.addAll(gameChunk.getGameStructureList());
        this.chunkCreator = gameChunk.getChunkCreator();
        if(gameChunk.getGameEntitiesList() != null) this.gameEntitiesList.addAll(gameChunk.getGameEntitiesList());
    }

    public GameChunk(int x,int z, String worldName, ChunkCreator chunkCreator){
        this.x = x;
        this.z = z;
        this.worldName = worldName;
        this.chunkCreator = chunkCreator;
    }

    public List<GameEntities> getGameEntitiesList() {
        return gameEntitiesList;
    }

    public ArrayList<GameStructure> getGameStructureList() {
        return gameStructureList;
    }

    public ChunkCreator getChunkCreator() {
        return chunkCreator;
    }

    public String getWorldName() {
        return worldName;
    }

    public void addGameGameStructure(GameStructure gameStructure){
        this.gameStructureList.add(gameStructure);
    }

    public GameStructure removeStructure(int x, int y, int z, Material material){
        Optional<GameStructure> optionalGameStructure = getGameStructureList().stream().filter(gameStructure ->
                gameStructure.getGameBlocks().stream().anyMatch(gameBlock -> gameBlock.getX() == x && gameBlock.getY() == y && gameBlock.getZ() == z)).findFirst();
        if(optionalGameStructure.isPresent()) {
            GameStructure gameStructure = optionalGameStructure.get();
            GameStructure copyGameStructure = new GameStructure(gameStructure);
            if(gameStructure.removeBlock(x, y, z, material) && gameStructure.getGameBlocks().size() == 0){
                this.gameStructureList.remove(gameStructure);
                return copyGameStructure;
            }
        }
        return null;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public Location getCenterChunkLocation(){
        Location center = new Location(Bukkit.getWorld(worldName), getX() << 4, 64, getZ() << 4).add(8, 0, 8);
        center.setY(center.getWorld().getHighestBlockYAt(center) + 1);
        return center;
    }

    public boolean sameChunk(Integer x, Integer z){
        return this.x == x && this.z == z;
    }

    public Location getEast(){
        Location center = getCenterChunkLocation();
        return center.clone().add(16, 0, 0);
    }

    public Location getWest(){
        Location center = getCenterChunkLocation();
        return center.clone().add(-16, 0, 0);
    }

    public Location getSouth(){
        Location center = getCenterChunkLocation();
        return center.clone().add(0, 0, 16);
    }

    public Location getNorth(){
        Location center = getCenterChunkLocation();
        return center.clone().add(0, 0, -16);
    }

    public GameChunk getGameChunk(){
        return this;
    }

}
