package de.shiirroo.islands.gamedata.game.skills.farming;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.pickaxe.CosmicPickaxe;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.pickaxe.GoldDigger;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;
import de.shiirroo.islands.gamedata.game.skills.fabrication.Smelting2;
import de.shiirroo.islands.gamedata.game.skills.fabrication.Smelting3;

import java.util.List;

public class Mining_4 extends SkillCreator {

    @Override
    public String skillName() {
        return "Mining IV";
    }

    @Override
    public String skillDescription() {
        return "Rocks drop 60% additional gold";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new CosmicPickaxe())).addGameBonusType(GameBonusTyps.Money, 6000);
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new Mining_3(), new Smelting2());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
