package de.shiirroo.islands.gamedata.game.skills.magic;

import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.List;

public class Destruction extends SkillCreator {

    @Override
    public String skillName() {
        return "Destruction";
    }

    @Override
    public String skillDescription() {
        return "Deal 25% increased\ndamage to Bosses";
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
