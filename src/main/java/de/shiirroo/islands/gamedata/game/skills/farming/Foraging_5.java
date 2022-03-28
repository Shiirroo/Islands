package de.shiirroo.islands.gamedata.game.skills.farming;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.axe.ChopperOfDragonsouls;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.axe.StoneDicer;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;
import de.shiirroo.islands.gamedata.game.skills.fabrication.Smelting3;

import java.util.List;

public class Foraging_5 extends SkillCreator {

    @Override
    public String skillName() {
        return "Foraging V";
    }

    @Override
    public String skillDescription() {
        return "Trees and Leaves \ndrop 100% additional gold";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new ChopperOfDragonsouls())).addGameBonusType(GameBonusTyps.Money, 10000);
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new Foraging_4(), new Smelting3());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
