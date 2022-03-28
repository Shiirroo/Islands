package de.shiirroo.islands.gamedata.game.items.gameitems.tools;

import java.util.List;

public enum ToolTyp {
    SWORD(0),
    SHOVEL(3),
    PICKAXE(1),
    AXE(2),
    MENU(8),
    ALL(null);

    private static Integer setSlot;

    ToolTyp(Integer slot){
        this.slot = slot;
    }

    private final Integer slot;

    public Integer getSlot() {
        return slot;
    }

}

