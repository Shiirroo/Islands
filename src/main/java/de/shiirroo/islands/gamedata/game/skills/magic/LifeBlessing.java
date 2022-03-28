package de.shiirroo.islands.gamedata.game.skills.magic;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.List;

public class LifeBlessing extends SkillCreator {

    @Override
    public String skillName() {
        return "Life blessing";
    }

    @Override
    public String skillDescription() {
        return "Gain 2 extra Health";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().addGameBonusType(GameBonusTyps.Health, 2);
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
