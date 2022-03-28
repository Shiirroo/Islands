package de.shiirroo.islands.gamedata.game.skills.economy;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.sword.CosmicSword;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.sword.SwordOnAStick;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.List;

public class Slayer_4 extends SkillCreator {

    @Override
    public String skillName() {
        return "Slayer IV";
    }

    @Override
    public String skillDescription() {
        return "Monsters drop\n60% additional gold";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new CosmicSword())).addGameBonusType(GameBonusTyps.Money, 6000);
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new Slayer_3());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
