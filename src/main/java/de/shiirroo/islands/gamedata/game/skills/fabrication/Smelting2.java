package de.shiirroo.islands.gamedata.game.skills.fabrication;

import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.epic.CosmicShard;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.rare.MediumCosmicShard;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.SmallCosmicShard;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.epic.Gold_Ingot;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.legendary.CosmicIngot;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.rare.Iron_Ingot;
import de.shiirroo.islands.gamedata.game.skills.SkillCreator;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;
import de.shiirroo.islands.gamedata.game.skills.farming.Wood_Coin;

import java.util.List;

public class Smelting2 extends SkillCreator {

    @Override
    public String skillName() {
        return "Smelting II";
    }

    @Override
    public String skillDescription() {
        return "Unlock Cosmic Ores";
    }

    @Override
    public SkillUnlock skillUnlock() {
        return new SkillUnlock().setUnlockItems(List.of(new CosmicIngot(),new MediumCosmicShard(),new CosmicShard()));
    }

    @Override
    public List<SkillCreator> requiredSkill() {
        return List.of(new Smelting());
    }

    @Override
    public void skillEffect() {
    }

    @Override
    public boolean startSkill() {
        return false;
    }
}
