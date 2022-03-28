package de.shiirroo.islands.gamedata.game.skills.economy;

import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Stone;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;

import java.util.List;

public class StartingEconomySkill extends SkillCreator {

    @Override
    public String skillName() {
        return "Economy";
    }

    @Override
    public String skillDescription() {
        return "Unlocks the \nEconomy skill tree";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().addGameBonusType(GameBonusTyps.Damage, 1000).addGameBonusType(GameBonusTyps.Money, 1000);
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return null;
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return true;
    }
}
