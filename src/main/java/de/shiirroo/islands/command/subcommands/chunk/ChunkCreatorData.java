package de.shiirroo.islands.command.subcommands.chunk;

import de.shiirroo.islands.gamedata.game.chunk.GameBlock;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.UUID;

public class ChunkCreatorData implements Serializable {
    @Serial
    private static final long serialVersionUID = 6871948464050295099L;


    private final UUID uuid;
    private final HashSet<GameBlock> gameBlocks = new HashSet<>();

    public ChunkCreatorData(){
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void addGameBlock(GameBlock gameBlock){
        this.gameBlocks.add(gameBlock);
    }

    public HashSet<GameBlock> getGameBlocks() {
        return gameBlocks;
    }
}
