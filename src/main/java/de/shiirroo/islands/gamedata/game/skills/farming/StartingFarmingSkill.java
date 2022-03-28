package de.shiirroo.islands.gamedata.game.skills.farming;

import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Lily_Pad;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.uncommon.Bread;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.List;

public class StartingFarmingSkill extends SkillCreator {

    @Override
    public String skillName() {
        return "Farming";
    }

    @Override
    public String skillDescription() {
        return "Unlocks the Farming \nskill tree";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new Bread(),(new Lily_Pad())));
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
