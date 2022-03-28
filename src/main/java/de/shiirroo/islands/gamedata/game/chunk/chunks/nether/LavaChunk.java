package de.shiirroo.islands.gamedata.game.chunk.chunks.nether;

import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.blocks.BlockDataCreator;
import de.shiirroo.islands.gamedata.game.chunk.GameBlock;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import de.shiirroo.islands.gamedata.game.chunk.GameStructure;
import de.shiirroo.islands.gamedata.game.chunk.chunks.ChunkCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.epic.LavaCauldron;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.rare.Netherrack;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.rare.Netherrack_Gold;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.epic.MoltenLava;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.uncommon.BrokenGold;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LavaChunk extends ChunkCreator implements Serializable {

    @Override
    public ArrayList<BlockDataCreator> getChunkMaterialList() {
        return new ArrayList<>(List.of(
                new BlockDataCreator(new LavaCauldron()).addDropItems(new MoltenLava())));

    }

    @Override
    public String chunkFileName() {
        int r = Utilis.generateRandomInt(20);
        return "randomArea_" + r;
    }

    @Override
    public ItemStack chunkDisplayItem() {
        ItemStack itemStack = new ItemStack(Material.CAULDRON);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Lava Island");
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
    public List<BlockCreator> placedOn() {
        BlockCreator blockCreator = new Netherrack();
        return List.of(blockCreator);
    }

    @Override
    public Rarity rarity() {
        return Rarity.EPIC;
    }

    @Override
    public int ChunkPlaceSize() {
        return 2;
    }

    @Override
    public GameStructure getGameStructure(int x, int z, GameChunk gameChunk) {
        return oreSpawner(x, z, gameChunk);
    }

    @Override
    protected boolean checkPlaceChunk(int x, int z, Chunk c) {
        return (placedOn().stream().anyMatch(blockCreator -> blockCreator.getItemStack().getType().equals(c.getBlock(x,50,z).getType()))
                && (c.getWorld().getPlayers().stream().noneMatch(player -> player.getLocation().getX() == x && player.getLocation().getZ() == z)));
    }


    private GameStructure oreSpawner(int x, int z, GameChunk gameChunk){
        GameBlock gameBlock = new GameBlock(getChunkMaterialList().get(0).getBlockDataString(), x, 51, z);
        System.out.println(gameBlock);


        ArrayList<GameBlock> chunkBlockList = new ArrayList<>();
        if(Utilis.generateRandomInt(5) == 0) chunkBlockList.add(new GameBlock(getChunkMaterialList().get(Utilis.generateRandomInt(2)).getBlockDataString(), x, 51, z));
        return new GameStructure(chunkBlockList, gameChunk, Utilis.generateRandomInt(50) + 1);
    }

}
