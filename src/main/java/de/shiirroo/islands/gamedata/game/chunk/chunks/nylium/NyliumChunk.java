package de.shiirroo.islands.gamedata.game.chunk.chunks.nylium;

import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.chunk.GameBlock;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import de.shiirroo.islands.gamedata.game.chunk.GameStructure;
import de.shiirroo.islands.gamedata.game.blocks.BlockDataCreator;
import de.shiirroo.islands.gamedata.game.chunk.chunks.ChunkCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Warped_Nylium;
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

public class NyliumChunk extends ChunkCreator implements Serializable {
    @Serial
    private static final long serialVersionUID = 6871948464050295099L;


    @Override
    public ArrayList<BlockDataCreator> getChunkMaterialList() {
        return new ArrayList<>(Arrays.asList(
                new BlockDataCreator(new Warped_Stem()).addDropItems(new Warped_Hyphae()),
                new BlockDataCreator(new Warped_Hyphae())));
    }

    @Override
    public String chunkFileName() {
        int r = Utilis.generateRandomInt(20);
        return "randomArea_" + r;
    }

    @Override
    public ItemStack chunkDisplayItem() {
        ItemStack itemStack = new ItemStack(Material.WARPED_NYLIUM);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Nylium Island");
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
        return new ArrayList<>(List.of(new Warped_Nylium()));
    }

    @Override
    public Rarity rarity() {
        return Rarity.EPIC;
    }

    @Override
    public int ChunkPlaceSize() {
        return 5;
    }

    @Override
    public GameStructure getGameStructure(int x, int z, GameChunk gameChunk) {
        return createTree(x, z, gameChunk);
    }

    @Override
    protected boolean checkPlaceChunk(int x, int z, Chunk c) {
        return (placedOn().stream().anyMatch(blockCreator -> blockCreator.getItemStack().getType().equals(c.getBlock(x,50,z).getType()))
                && (c.getWorld().getPlayers().stream().noneMatch(player -> player.getLocation().getX() == x && player.getLocation().getZ() == z)));
    }


    private GameStructure createTree(int x, int z, GameChunk gameChunk){
        ArrayList<GameBlock> chunkBlockList = new ArrayList<>();
        int xp = Utilis.generateRandomInt(5) + 1;
        for (int i = 51; i < 55; i++) {
            chunkBlockList.add(new GameBlock(getChunkMaterialList().get(0).getBlockDataString(), x, i, z));
        }
        for(int xi = -1;xi < 2 ;xi++){
            for(int zi = -1;zi < 2 ;zi++){
                if(!(xi == 0 && zi == 0)){
                    chunkBlockList.add(new GameBlock(getChunkMaterialList().get(1).getBlockDataString(), x + xi, 54, z + zi));
                }
            }
        }
        chunkBlockList.add(new GameBlock(getChunkMaterialList().get(1).getBlockDataString(), x, 55, z));
        return new GameStructure(chunkBlockList, gameChunk, xp);
    }

}
