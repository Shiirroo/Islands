package de.shiirroo.islands.gamedata.game.chunk.chunks.gravel;

import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.blocks.BlockDataCreator;
import de.shiirroo.islands.gamedata.game.chunk.GameBlock;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import de.shiirroo.islands.gamedata.game.chunk.GameStructure;
import de.shiirroo.islands.gamedata.game.chunk.chunks.ChunkCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Cobblestone;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.Gravel;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Stone;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Skull;
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

public class GraveChunk extends ChunkCreator implements Serializable {
    @Serial
    private static final long serialVersionUID = 6871948464050295099L;

    @Override
    public ArrayList<BlockDataCreator> getChunkMaterialList() {
        return new ArrayList<>(List.of(
                new BlockDataCreator(new Skull())));
    }


    @Override
    public String chunkFileName() {
        int r = Utilis.generateRandomInt(20);
        return "randomArea_" + r;
    }

    @Override
    public ItemStack chunkDisplayItem() {
        ItemStack itemStack = new ItemStack(Material.SKELETON_SKULL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("Grave Island");
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
        return List.of(new Stone(), new Gravel());
    }

    @Override
    public Rarity rarity() {
        return Rarity.UNCOMMON;
    }

    @Override
    public int ChunkPlaceSize() {
        return 2;
    }

    @Override
    public GameStructure getGameStructure(int x, int z, GameChunk gameChunk) {
        ArrayList<GameBlock> chunkBlockList = new ArrayList<>();
        chunkBlockList.add(new GameBlock(getChunkMaterialList().get(Utilis.generateRandomInt(1)).getBlockDataString(), x, 51, z));
        return new GameStructure(chunkBlockList, gameChunk, Utilis.generateRandomInt(10) + 1);
    }

    @Override
    protected boolean checkPlaceChunk(int x, int z, Chunk c) {
        return (placedOn().stream().anyMatch(blockCreator -> blockCreator.getItemStack().getType().equals(c.getBlock(x, 50, z).getType()))
                && (c.getWorld().getPlayers().stream().noneMatch(player -> player.getLocation().getX() == x && player.getLocation().getZ() == z)));
    }
}
