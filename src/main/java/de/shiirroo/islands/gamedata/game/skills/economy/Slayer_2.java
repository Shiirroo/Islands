package de.shiirroo.islands.gamedata.game.skills.economy;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.sword.SwordOnAStick;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.List;

public class Slayer_2 extends SkillCreator {

    @Override
    public String skillName() {
        return "Slayer II";
    }

    @Override
    public String skillDescription() {
        return "Monsters drop\n20% additional gold";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new SwordOnAStick())).addGameBonusType(GameBonusTyps.Money, 2000);
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new Slayer_1());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
