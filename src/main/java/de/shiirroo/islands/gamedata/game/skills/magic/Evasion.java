package de.shiirroo.islands.gamedata.game.skills.magic;

import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.List;

public class Evasion extends SkillCreator {

    @Override
    public String skillName() {
        return "Evasion";
    }

    @Override
    public String skillDescription() {
        return "Increases damage \nreduction by 10%";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return null;
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
