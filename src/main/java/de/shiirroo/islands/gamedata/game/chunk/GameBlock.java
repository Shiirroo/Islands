package de.shiirroo.islands.gamedata.game.chunk;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import java.io.Serial;
import java.io.Serializable;

public class GameBlock implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String blockDataString;
    private final int x;
    private final int y;
    private final int z;


    public GameBlock(GameBlock gameBlock){
        this.x = gameBlock.getX();
        this.y = gameBlock.getY();
        this.z = gameBlock.getZ();
        this.blockDataString = gameBlock.getBlockDataString();
    }

    public GameBlock(String blockDataString, int x, int y, int z){
        this.blockDataString =blockDataString;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getBlockDataString() {
        return blockDataString;
    }

    public BlockData getBlockData(){
        return Bukkit.createBlockData(getBlockDataString());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public boolean sameCoordinates(GameBlock gameBlock){
        return gameBlock.getX() == getX() && gameBlock.getY() == getY() && gameBlock.getZ() == getZ();
    }

    public void setBlock(Chunk chunk, boolean rotate){
        if(!rotate) {
            if(getBlockData().getMaterial().equals(Material.WATER)) {
                for (int y = getY() + 2; y < 70; y++) chunk.getBlock(getX(), y, getZ()).setType(Material.BARRIER);
            } else chunk.getBlock(getX(), getY(), getZ()).setBlockData(getBlockData());
        } else {
            if (getBlockData().getMaterial().equals(Material.WATER)) {
                for (int y = getY() + 2; y < 70; y++) chunk.getBlock(getZ(), y, getX()).setType(Material.BARRIER);
            } else chunk.getBlock(getZ(), getY(), getX()).setBlockData(getBlockData());
        }
    }
}
