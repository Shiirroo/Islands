package de.shiirroo.islands.gamedata.game.blocks;

import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BlockDataCreator {

    private final BlockCreator item;
    private final HashMap<ItemStack, Integer> dropItems = new HashMap<>();
    private String BlockDataString;

    public BlockDataCreator setBlockDataString(String blockDataString){
        this.BlockDataString = blockDataString;
        return this;
    };


    public BlockDataCreator(BlockCreator item){
        this.item = item;
    }


    public BlockDataCreator addDropItems(Item dropItem){
        ItemStack itemStack = dropItem.getItemStack();
        itemStack.setAmount(1);
        this.dropItems.put(itemStack, 100);
        return this;
    }


    public BlockDataCreator addDropItems(Item dropItem, int size){
        if(size == 0) return this;
        ItemStack itemStack = dropItem.getItemStack();
        itemStack.setAmount(size);
        this.dropItems.put(itemStack, 100);
        return this;
    }

    public BlockDataCreator addDropItems(Item dropItem, int min, int max){
        int r = Utilis.generateRandomInt(max - min) + min;
        ItemStack itemStack = dropItem.getItemStack();
        itemStack.setAmount(r);
        this.dropItems.put(itemStack, 100);
        return this;
    }

    public BlockDataCreator addDropWithChance(Item dropItem, int size,int chance){
        ItemStack itemStack = dropItem.getItemStack();
        itemStack.setAmount(size);
        this.dropItems.put(itemStack, chance);
        return this;
    }


    public Material getMaterial(){
        return item.getItemStack().getType();
    }

    public BlockCreator getBlockCreator() {
        return item;
    }

    public List<ItemStack> getDropItems(Item item) {
        if(this.dropItems.size() == 0){
            return new ArrayList<>(List.of(this.item.getItemStack()));
        }
        List<ItemStack> itemStackArrayList = new ArrayList<>();
        for(ItemStack itemStack: dropItems.keySet()){
            int r = Utilis.generateRandomInt(100) + 1;
            if(r <= dropItems.get(itemStack)) {
                itemStackArrayList.add(itemStack);
            }
        }
        return itemStackArrayList;
    }

    public String getBlockDataString(){
        if(BlockDataString != null) return BlockDataString;
        return Bukkit.createBlockData(item.getItemStack().getType()).getAsString();
    }

    public ItemStack getItemStack(){
        return item.getItemStack();
    }

    protected BlockData getBlockData(){
        return Bukkit.createBlockData(item.getItemStack().getType());
    }
}
