package de.shiirroo.islands.gamedata.game.skills.farming;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.pickaxe.GoldDigger;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.pickaxe.SkullPickaxe;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;
import de.shiirroo.islands.gamedata.game.skills.fabrication.Smelting;

import java.util.List;

public class Mining_3 extends SkillCreator {

    @Override
    public String skillName() {
        return "Mining III";
    }

    @Override
    public String skillDescription() {
        return "Rocks drop 30% additional gold";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new GoldDigger())).addGameBonusType(GameBonusTyps.Money, 3000);
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new Mining_2(), new Smelting());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
