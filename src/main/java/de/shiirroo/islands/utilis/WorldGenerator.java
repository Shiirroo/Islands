package de.shiirroo.islands.utilis;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WorldGenerator extends ChunkGenerator {

    public List<BlockPopulator> getDefaultPopulator(World world){
        return new ArrayList<>();
    }


    public ChunkData generateChunkData(World world, Random random, int ChunkX, int ChunkZ, BiomeGrid grid){
        ChunkData data = createChunkData(world);

        for(int x = 0; x < 16; x++){
            for(int z = 0;z < 16; z++){
                    grid.setBiome(x, z , Biome.PLAINS);
                    data.setBlock(x, 0, z, Material.BEDROCK);
                    for(int y = 1; y < 70; y++) {
                        if(y <= 49){
                            data.setBlock(x, y, z, Material.WATER);
                        } else if(y > 51 &&( x == 0 || x == 15 || z == 0 || z == 15)){
                            data.setBlock(x, y, z, Material.BARRIER);
                        }
                    }
                    data.setBlock(x, 49, z, Material.BARRIER);
                    data.setBlock(x, 50, z, Material.WATER);
            }
        }
        return data;
    }
}
