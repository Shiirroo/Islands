package de.shiirroo.islands.gamedata.game.chunk.chunks.cosmic;

import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.blocks.BlockDataCreator;
import de.shiirroo.islands.gamedata.game.chunk.GameBlock;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import de.shiirroo.islands.gamedata.game.chunk.GameStructure;
import de.shiirroo.islands.gamedata.game.chunk.chunks.ChunkCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Warped_Nylium;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.epic.CosmicShard;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.epic.CryingObsidian;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.epic.Obsidian;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.rare.MediumCosmicShard;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.SmallCosmicShard;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Warped_Hyphae;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Warped_Stem;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CosmicChunk extends ChunkCreator implements Serializable {

    @Override
    public ArrayList<BlockDataCreator> getChunkMaterialList() {
        return new ArrayList<>(Arrays.asList(
                new BlockDataCreator(new SmallCosmicShard()),
                new BlockDataCreator(new MediumCosmicShard()),
                new BlockDataCreator(new CosmicShard())));

    }

    @Override
    public String chunkFileName() {
        int r = Utilis.generateRandomInt(20);
        return "randomArea_" + r;
    }

    @Override
    public ItemStack chunkDisplayItem() {
        ItemStack itemStack = new ItemStack(Material.CRYING_OBSIDIAN);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Cosmic Island");
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
    public ArrayList<BlockCreator> placedOn() {
        return new ArrayList<>(List.of(new CryingObsidian(), new Obsidian()));
    }

    @Override
    public Rarity rarity() {
        return Rarity.LEGENDARY;
    }

    @Override
    public int ChunkPlaceSize() {
        return 5;
    }

    @Override
    public GameStructure getGameStructure(int x, int z, GameChunk gameChunk) {
        return cosmicOreSpawner(x, z, gameChunk);
    }

    @Override
    protected boolean checkPlaceChunk(int x, int z, Chunk c) {
        return (placedOn().stream().anyMatch(blockCreator -> blockCreator.getItemStack().getType().equals(c.getBlock(x,50,z).getType()))
                && (c.getWorld().getPlayers().stream().noneMatch(player -> player.getLocation().getX() == x && player.getLocation().getZ() == z)));
    }


    private GameStructure cosmicOreSpawner(int x, int z, GameChunk gameChunk){
        ArrayList<GameBlock> chunkBlockList = new ArrayList<>();
        int xp = Utilis.generateRandomInt(20) + 1;
        int r = Utilis.generateRandomInt(20);
        if(r == 0) chunkBlockList.add(new GameBlock(getChunkMaterialList().get(2).getBlockDataString(), x, 51,z));
        else if(r > 18) chunkBlockList.add(new GameBlock(getChunkMaterialList().get(1).getBlockDataString(), x, 51,z));
        else chunkBlockList.add(new GameBlock(getChunkMaterialList().get(0).getBlockDataString(), x, 51,z));
        return new GameStructure(chunkBlockList, gameChunk, xp);
    }

}
