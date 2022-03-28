package de.shiirroo.islands.worker;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

public class GameBlockSpawner implements Runnable{

    @Override
    public void run() {
        for(GameData gameData: IslandsPlugin.getGameData()){
            World w = Bukkit.getWorld(gameData.getId().toString());
                    if (w != null && w.getPlayers().size() > 0) {
                        for (GameChunk gameChunk: gameData.getGameArea().getGameChunks()){
                            if (gameChunk.getGameStructureList().size() < gameChunk.getChunkCreator().ChunkPlaceSize()) {
                                Chunk c = w.getChunkAt(gameChunk.getCenterChunkLocation());
                                int update = Utilis.generateRandomInt(gameData.getGameStatus().getUpdateRate());
                                if (update == 0) {
                                    int x = Utilis.generateRandomInt(15);
                                    int z = Utilis.generateRandomInt(15);
                                    gameChunk.getChunkCreator().checkChunkBlock(x, z, gameChunk, c);
                        }
                    }
                }
            }
        }
    }
}
