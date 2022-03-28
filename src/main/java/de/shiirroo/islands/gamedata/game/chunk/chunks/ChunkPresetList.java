package de.shiirroo.islands.gamedata.game.chunk.chunks;

import de.shiirroo.islands.gamedata.game.chunk.chunks.cosmic.CosmicChunk;
import de.shiirroo.islands.gamedata.game.chunk.chunks.dragon.DragonChunk;
import de.shiirroo.islands.gamedata.game.chunk.chunks.gravel.GraveChunk;
import de.shiirroo.islands.gamedata.game.chunk.chunks.nether.LavaChunk;
import de.shiirroo.islands.gamedata.game.chunk.chunks.nether.NetherChunk;
import de.shiirroo.islands.gamedata.game.chunk.chunks.nether.NetherKingdomChunk;
import de.shiirroo.islands.gamedata.game.chunk.chunks.nylium.NyliumChunk;
import de.shiirroo.islands.gamedata.game.chunk.chunks.ore.DeepslateChunk;
import de.shiirroo.islands.gamedata.game.chunk.chunks.ore.GoldChunk;
import de.shiirroo.islands.gamedata.game.chunk.chunks.ore.OreChunk;
import de.shiirroo.islands.gamedata.game.chunk.chunks.sand.SandChunk;
import de.shiirroo.islands.gamedata.game.chunk.chunks.starting.StartingChunk;
import de.shiirroo.islands.gamedata.game.chunk.chunks.stone.StoneChunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChunkPresetList {

    private final ArrayList<ChunkCreator> chunkPresetList = new ArrayList<>();

    public ChunkPresetList(){
        this.chunkPresetList.add(new StartingChunk());
        this.chunkPresetList.add(new SandChunk());
        this.chunkPresetList.add(new OreChunk());
        this.chunkPresetList.add(new NyliumChunk());
        this.chunkPresetList.add(new CosmicChunk());
        this.chunkPresetList.add(new DeepslateChunk());
        this.chunkPresetList.add(new GoldChunk());
        this.chunkPresetList.add(new StoneChunk());
        this.chunkPresetList.add(new GraveChunk());
        this.chunkPresetList.add(new NetherChunk());
        this.chunkPresetList.add(new NetherKingdomChunk());
        this.chunkPresetList.add(new LavaChunk());
        this.chunkPresetList.add(new DragonChunk());
    }



    public ArrayList<ChunkCreator> getChunkPresetList() {
        return chunkPresetList;
    }
}
