package de.shiirroo.islands.gamedata.game.skills;

import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.gamedata.game.items.Item;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameSkills implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final ArrayList<SkillCreator> skillList = new ArrayList<>();
    private final ArrayList<Item> craftableItems = new ArrayList<>();
    private final GameData gameData;

    public GameSkills(GameData gameData, GameSkills gameSkills){
        this.skillList.addAll(gameSkills.getSkillList());
        this.gameData = gameData;
        this.craftableItems.addAll(gameSkills.getCraftableItems());
    }


    public GameSkills(GameData gameData){
        this.gameData = gameData;
    }

    public ArrayList<Item> getCraftableItems() {
        return craftableItems;
    }

    public void addSkill(SkillCreator skill) {
        if (!this.skillList.contains(skill)) {
            if (skill.skillUnlock() != null && skill.skillUnlock().getUnlockItems() != null) {
                this.craftableItems.addAll(skill.skillUnlock().getUnlockItems());
            }

            if (skill.skillUnlock() != null && skill.skillUnlock().getGameBonusType() != null) {
                for (GameBonusTyps bonusTyps : skill.skillUnlock().getGameBonusType().keySet()) {
                    gameData.getGameStatus().getGameBonus().put(bonusTyps, gameData.getGameStatus().getGameBonus().get(bonusTyps) + skill.skillUnlock().getGameBonusType().get(bonusTyps));
                    if(bonusTyps.equals(GameBonusTyps.Health)){
                        World world = Bukkit.getWorld(gameData.getId().toString());
                        if(world != null){world.getPlayers().forEach(player -> player.setMaxHealth(player.getMaxHealth() + skill.skillUnlock().getGameBonusType().get(bonusTyps)));
                        }
                    }
                }
            }
            this.skillList.add(skill);
        }
    }

    public void removeSkill(SkillCreator skill){
        this.skillList.remove(skill);
    }


    public ArrayList<SkillCreator> getSkillList() {
        return skillList;
    }

}
