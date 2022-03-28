package de.shiirroo.islands.gamedata.game.skills.farming;

import de.shiirroo.islands.gamedata.game.items.gameitems.tools.axe.AxeOnAStick;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.pickaxe.PickOnAStick;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.List;

public class Wood_Coin extends SkillCreator {

    @Override
    public String skillName() {
        return "Golden Wood";
    }

    @Override
    public String skillDescription() {
        return "Trees and Leaves have a\nchance to drop Gold";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new AxeOnAStick()));
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
