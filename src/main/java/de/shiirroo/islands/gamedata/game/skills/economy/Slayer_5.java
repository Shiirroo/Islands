package de.shiirroo.islands.gamedata.game.skills.economy;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.sword.Dragonslayer;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.sword.SwordOnAStick;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.List;

public class Slayer_5 extends SkillCreator {

    @Override
    public String skillName() {
        return "Slayer V";
    }

    @Override
    public String skillDescription() {
        return "Monsters drop\n100% additional gold";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new Dragonslayer())).addGameBonusType(GameBonusTyps.Money, 1000);
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new Slayer_4());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
