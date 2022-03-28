package de.shiirroo.islands.gamedata.game.skills.magic;

import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.List;

public class Lifesteal extends SkillCreator {

    @Override
    public String skillName() {
        return "Lifesteal";
    }

    @Override
    public String skillDescription() {
        return "Recover heal when \nkilling enemies";
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
