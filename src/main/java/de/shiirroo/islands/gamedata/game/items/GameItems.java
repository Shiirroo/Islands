package de.shiirroo.islands.gamedata.game.items;

import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.axe.StartAxe;
import de.shiirroo.islands.gamedata.game.items.etc.ActionFillItem;
import de.shiirroo.islands.gamedata.game.items.etc.MenuItem;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.pickaxe.StartPickAxe;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameItems implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private final HashSet<ToolCreator> items = new HashSet<>();

    public GameItems(GameItems gameItems){
        this.items.addAll(gameItems.getItemCreators());
    }


    public GameItems(){
        this.items.add(new StartPickAxe());
        this.items.add(new StartAxe());
        setFillerItems();
        this.items.add(new MenuItem());
    }

    public HashSet<ToolCreator> getItemCreators() {
        return items;
    }

    public List<ItemStack> getItemStackList(){
        return items.stream().map(ToolCreator::getItemStack).collect(Collectors.toList());

    }

    public void changeGameItem(ToolCreator newItem){
        this.items.removeIf(itemCreator -> itemCreator.setToolTyp() != null && Objects.equals(itemCreator.setToolTyp().getSlot(), newItem.setToolTyp().getSlot()));
        this.items.add(newItem);
    }


    public void setFillerItems(){
        for(int i = 0; i <5;i++){
            int finalI = i;
            if(items.stream().noneMatch(itemCreator -> (itemCreator.setToolTyp() != null && itemCreator.setToolTyp().getSlot() == finalI))){
                this.items.add(new ActionFillItem().setSlot(i));
            }
        }
    }
}
