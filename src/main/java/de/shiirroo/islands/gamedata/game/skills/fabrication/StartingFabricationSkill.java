package de.shiirroo.islands.gamedata.game.skills.fabrication;

import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Stone;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartingFabricationSkill extends SkillCreator {

    @Override
    public String skillName() {
        return "Fabrication";
    }

    @Override
    public String skillDescription() {
        return "Unlocks the \nFabrication skill tree";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().addUnlockItems(new Stone());
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
