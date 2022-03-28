package de.shiirroo.islands.gamedata.game.skills.economy;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.List;

public class Shop extends SkillCreator {

    @Override
    public String skillName() {
        return "Shop";
    }

    @Override
    public String skillDescription() {
        return "Unlocks shop menu";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return null;
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return null;
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return true;
    }
}
