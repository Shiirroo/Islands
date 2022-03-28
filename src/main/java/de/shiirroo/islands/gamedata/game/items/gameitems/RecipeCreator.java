package de.shiirroo.islands.gamedata.game.items.gameitems;

import de.shiirroo.islands.gamedata.game.items.Item;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecipeCreator {

    private LinkedHashMap<Item, Integer> items = new LinkedHashMap<>();


    public RecipeCreator addItems(Item item, Integer size){
        this.items.put(item, size);
        return this;
    }


    public RecipeCreator setItems(LinkedHashMap<Item, Integer> items) {
        this.items = items;
        return this;
    }

    public Map<ItemStack, Item> getItemStacks(){
        return items.keySet().parallelStream().collect(Collectors.toMap(Item::getItemStack, Function.identity()));
    }


    public HashMap<Item, Integer> getItems() {
        return items;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeCreator that)) return false;
        return Objects.equals(getItems(), that.getItems());
    }



}
