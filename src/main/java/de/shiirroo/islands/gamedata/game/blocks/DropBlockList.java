package de.shiirroo.islands.gamedata.game.blocks;

import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Lily_Pad;

import java.util.ArrayList;
import java.util.List;

public class DropBlockList {

    private final ArrayList<BlockDataCreator> dropBlockList = new ArrayList<>();


    public DropBlockList(){
        this.dropBlockList.add(new BlockDataCreator(new Lily_Pad()));





    }


    public ArrayList<BlockDataCreator> getDropBlockList() {
        return dropBlockList;
    }
}
