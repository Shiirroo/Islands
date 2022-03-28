package de.shiirroo.islands.gamedata.game.skills.magic;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.ArrayList;
import java.util.List;

public class MagicalBonus extends SkillCreator {

    @Override
    public String skillName() {
        return "Magical Bonus";
    }

    @Override
    public String skillDescription() {
        return "Gain 10% more XP";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().addGameBonusType(GameBonusTyps.Xp, 1000);
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new MagicStartSkill());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
