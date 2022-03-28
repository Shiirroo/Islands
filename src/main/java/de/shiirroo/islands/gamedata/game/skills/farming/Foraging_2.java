package de.shiirroo.islands.gamedata.game.skills.farming;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.axe.SkullAxe;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.axe.StoneDicer;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;
import de.shiirroo.islands.gamedata.game.skills.fabrication.Smelting;

import java.util.List;

public class Foraging_2 extends SkillCreator {

    @Override
    public String skillName() {
        return "Foraging I";
    }

    @Override
    public String skillDescription() {
        return "Trees and Leaves \ndrop 20% additional gold";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new SkullAxe())).addGameBonusType(GameBonusTyps.Money, 2000);
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new Foraging_1(), new Smelting());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
