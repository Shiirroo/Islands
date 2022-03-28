package de.shiirroo.islands.gamedata.game.skills;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SkillUnlock {

    private List<Item> unlockItems =  new ArrayList<>();
    private HashMap<GameBonusTyps, Long> gameBonusType = new HashMap<>();

    public SkillUnlock setUnlockItems(List<Item> unlockItems){
        this.unlockItems = unlockItems;
        return this;
    }

    public SkillUnlock setGameBonusType(HashMap<GameBonusTyps, Long> gameBonusType){
        this.gameBonusType = gameBonusType;
        return this;
    }

    public SkillUnlock addUnlockItems(Item item){
        this.unlockItems.add(item);
        return this;
    }


    public SkillUnlock addGameBonusType(GameBonusTyps gameBonusTyps, long bonus){
        this.gameBonusType.put(gameBonusTyps, bonus);
        return this;
    }

    public HashMap<GameBonusTyps, Long> getGameBonusType() {
        return gameBonusType;
    }

    public List<Item> getUnlockItems() {
        return unlockItems;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkillUnlock that)) return false;
        return Objects.equals(getUnlockItems(), that.getUnlockItems()) && Objects.equals(getGameBonusType(), that.getGameBonusType());
    }

}
