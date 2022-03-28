package de.shiirroo.islands.gamedata.game.skills.magic;

import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.ArrayList;

public class MagicStartSkill extends SkillCreator {

    @Override
    public String skillName() {
        return "Magic";
    }

    @Override
    public String skillDescription() {
        return "Unlocks the magic \nskill tree";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return null;
    }

    @Override
    public ArrayList<SkillCreator> requiredSkill() {
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
