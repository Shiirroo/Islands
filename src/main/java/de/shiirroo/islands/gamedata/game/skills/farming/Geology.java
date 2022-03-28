package de.shiirroo.islands.gamedata.game.skills.farming;

import de.shiirroo.islands.gamedata.game.items.gameitems.items.uncommon.Charcoal;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.ArrayList;
import java.util.List;

public class Geology extends SkillCreator {

    @Override
    public String skillName() {
        return "Geology";
    }

    @Override
    public String skillDescription() {
        return "Rocks and Ores can drop Coal";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().addUnlockItems(new Charcoal());
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new StartingFarmingSkill());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
