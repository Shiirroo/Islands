package de.shiirroo.islands.event.menu.menus;

import de.shiirroo.islands.gamedata.game.chunk.GameBlock;
import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Iron_Ore;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Furnace {

    private final GameBlock gameBlock;
    private ArrayList<Item> fuel = new ArrayList<>();
    private ArrayList<Item> smelting = new ArrayList<>();
    private ArrayList<Item> product = new ArrayList<>();



    public Furnace(GameBlock gameBlock){
        this.gameBlock = gameBlock;
    }

    public GameBlock getGameBlock() {
        return gameBlock;
    }

    public ArrayList<Item> getFuel() {
        return fuel;
    }

    public ArrayList<Item> getProduct() {
        return product;
    }

    public ArrayList<Item> getSmelting() {
        return smelting;
    }


    public boolean addFuel(Item item, int amount){
        if(item.getItemStack().getType().equals(Material.COAL)){
            item.getItemStack().setAmount(amount);
            this.fuel.add(item);
            return true;
        }
        return false;
    }

    public boolean addSmelting(Item item, int amount){
        if(item.equals(new Iron_Ore())){
            item.getItemStack().setAmount(amount);
            this.smelting.add(item);
            return true;
        }
        return false;
    }

    public void addProdukt(Item item, int amount){
            item.getItemStack().setAmount(amount);
            this.product.add(item);
    }








}
