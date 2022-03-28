package de.shiirroo.islands.gamedata.game.skills;

import java.util.ArrayList;

public class SkillNotCreated extends SkillCreator {

    @Override
    public String skillName() {
        return null;
    }

    @Override
    public String skillDescription() {
        return null;
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
        return false;
    }
}
