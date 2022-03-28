package de.shiirroo.islands.gamedata.game.skills.farming;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.pickaxe.StoneSlicer;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.List;

public class Mining_1 extends SkillCreator {

    @Override
    public String skillName() {
        return "Mining I";
    }

    @Override
    public String skillDescription() {
        return "Rocks drop 10% additional gold";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new StoneSlicer())).addGameBonusType(GameBonusTyps.Money, 1000);
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new Stone_Coin());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
