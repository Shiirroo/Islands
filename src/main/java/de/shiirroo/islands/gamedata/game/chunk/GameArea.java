package de.shiirroo.islands.gamedata.game.chunk;

import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.chunk.chunks.starting.StartingChunk;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class GameArea implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private final GameChunk startChunk;
    private final ArrayList<GameChunk> gameChunks = new ArrayList<>();
    private final GameData gameData;

    public GameArea(GameData gameData, GameArea gameArea){
        this.gameData = gameData;
        this.startChunk = gameArea.getStartChunk();
        for(GameChunk gameChunk: gameArea.getGameChunks()){
            this.gameChunks.add(new GameChunk(gameChunk));
        }

    }

    public GameArea(GameData gameData){
        this.gameData = gameData;
        GameChunk gameChunk = new GameChunk(0,0,gameData.getId().toString(), new StartingChunk());
        this.startChunk = gameChunk;
        this.gameChunks.add(gameChunk);
    }

    public ArrayList<GameChunk> getGameChunks() {
        return gameChunks;
    }

    public GameChunk getStartChunk() {
        return startChunk;
    }

}



