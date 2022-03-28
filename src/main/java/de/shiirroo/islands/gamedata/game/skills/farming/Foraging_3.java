package de.shiirroo.islands.gamedata.game.skills.farming;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.axe.GoldChopper;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.axe.StoneDicer;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;
import de.shiirroo.islands.gamedata.game.skills.fabrication.Smelting;
import de.shiirroo.islands.gamedata.game.skills.fabrication.Smelting3;

import java.util.List;

public class Foraging_3 extends SkillCreator {

    @Override
    public String skillName() {
        return "Foraging I";
    }

    @Override
    public String skillDescription() {
        return "Trees and Leaves\ndrop 30% additional gold";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new GoldChopper())).addGameBonusType(GameBonusTyps.Money, 3000);
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new Foraging_2(), new Smelting());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
