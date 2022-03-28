package de.shiirroo.islands.gamedata.game.skills.farming;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.pickaxe.CosmicPickaxe;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.pickaxe.Obliterator;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;
import de.shiirroo.islands.gamedata.game.skills.fabrication.Smelting3;

import java.util.List;

public class Mining_5 extends SkillCreator {

    @Override
    public String skillName() {
        return "Mining V";
    }

    @Override
    public String skillDescription() {
        return "Rocks drop 100% additional gold";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new Obliterator())).addGameBonusType(GameBonusTyps.Money, 100000);
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new Mining_4(), new Smelting3());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
