package de.shiirroo.islands.gamedata.game.skills.fabrication;

import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.rare.Raw_Gold_Block;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.Raw_Iron_Block;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.epic.Gold_Ingot;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.rare.Iron_Ingot;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.axe.StoneDicer;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;
import de.shiirroo.islands.gamedata.game.skills.farming.Wood_Coin;

import java.util.List;

public class Smelting extends SkillCreator {

    @Override
    public String skillName() {
        return "Smelting I";
    }

    @Override
    public String skillDescription() {
        return "Unlock to Smelt Ores";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new Iron_Ingot(),new Raw_Iron_Block(),new Gold_Ingot(), new Raw_Gold_Block()));
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new StartingFabricationSkill());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
