package de.shiirroo.islands.gamedata.game.skills.economy;

import de.shiirroo.islands.gamedata.game.items.gameitems.tools.sword.SwordOnAStick;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.List;

public class GoldenMonster extends SkillCreator {

    @Override
    public String skillName() {
        return "Golden Monster";
    }

    @Override
    public String skillDescription() {
        return "Monsters have a\nchance to drop Gold";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new SwordOnAStick()));
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new StartingEconomySkill());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
