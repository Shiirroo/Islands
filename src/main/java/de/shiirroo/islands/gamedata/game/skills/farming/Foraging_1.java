package de.shiirroo.islands.gamedata.game.skills.farming;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.axe.StoneDicer;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.pickaxe.StoneSlicer;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.sword.StoneCutter;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.List;

public class Foraging_1 extends SkillCreator {

    @Override
    public String skillName() {
        return "Foraging I";
    }

    @Override
    public String skillDescription() {
        return "Trees and Leaves \ndrop 10% additional gold";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new StoneDicer())).addGameBonusType(GameBonusTyps.Money, 1000);
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new Wood_Coin());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
