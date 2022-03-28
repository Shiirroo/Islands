package de.shiirroo.islands.gamedata.game.items.gameitems.tools;

import java.util.ArrayList;
import java.util.List;

public enum ToolMaterial {
    WOOD(1,new ArrayList<>(List.of())),
    STONE(2, new ArrayList<>(List.of(WOOD))),
    IRON(4, new ArrayList<>(List.of(WOOD, STONE))),
    GOLD(12, new ArrayList<>(List.of(WOOD, STONE, IRON))),
    COSMIC(8, new ArrayList<>(List.of(WOOD, STONE, IRON, GOLD))),
    DRAGON(9, new ArrayList<>(List.of(WOOD, STONE, IRON, GOLD,COSMIC)));

    private final Integer muli;
    private final ArrayList<ToolMaterial> breakable;

    ToolMaterial(Integer muli, ArrayList<ToolMaterial> materials){
        this.muli = muli;
        this.breakable = materials;
    }

    public ArrayList<ToolMaterial> getBreakable() {
        return breakable;
    }

    public Integer getMuli() {
        return muli;
    }
}
