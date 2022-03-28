package de.shiirroo.islands.gamedata.game.skills.farming;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.axe.CosmicAxe;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.axe.StoneDicer;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;
import de.shiirroo.islands.gamedata.game.skills.fabrication.Smelting2;
import de.shiirroo.islands.gamedata.game.skills.fabrication.Smelting3;

import java.util.List;

public class Foraging_4 extends SkillCreator {

    @Override
    public String skillName() {
        return "Foraging IV";
    }

    @Override
    public String skillDescription() {
        return "Trees and Leaves \ndrop 60% additional gold";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new CosmicAxe())).addGameBonusType(GameBonusTyps.Money, 6000);
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new Foraging_3(), new Smelting2());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
