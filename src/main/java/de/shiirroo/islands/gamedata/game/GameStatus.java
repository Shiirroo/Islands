package de.shiirroo.islands.gamedata.game;

import de.shiirroo.islands.gamedata.GameData;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class GameStatus implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private long xp;
    private long money;
    private final long health;
    private final long updateRate;
    private final HashMap<GameBonusTyps, Long> gameBonus = new HashMap<>();


    private final GameData gameData;


    public GameStatus(GameData gameData, GameStatus gameStatus){
        this.xp = gameStatus.getXp();
        this.money = gameStatus.getMoney();
        this.health = gameStatus.getHealth();
        this.updateRate = gameStatus.getUpdateRate();
        this.gameData = gameData;
        this.gameBonus.putAll(gameStatus.getGameBonus());
    }

    public GameStatus(GameData gameData) {
        this.gameData = gameData;
        this.xp = 0;
        this.money = 0;
        this.health = 6;
        this.updateRate = 10;
        for (GameBonusTyps gameBonusTyps : GameBonusTyps.values()) {
            this.gameBonus.put(gameBonusTyps, 0L);
        }
    }

    public void setMoney(long money){
        this.money = money;
    }

    public void addXp(int xp){
        this.xp += xp;
    }

    public int getBonusXP(){
        return Math.toIntExact(this.gameBonus.get(GameBonusTyps.Xp) / 100L);
    }

    public int getBonusHealth(){
        return  Math.toIntExact( this.gameBonus.get(GameBonusTyps.Health) );
    }

    public int getBonusUpdateRates(){
        return  Math.toIntExact(this.gameBonus.get(GameBonusTyps.UpdateRate) / 100L);
    }

    public int getBonusMoney(){
        return  Math.toIntExact(this.gameBonus.get(GameBonusTyps.Money) / 100L);
    }

    public int getBonusDamage(){
        return Math.toIntExact(this.gameBonus.get(GameBonusTyps.Damage) / 100L);
    }



    public HashMap<GameBonusTyps, Long> getGameBonus() {
        return gameBonus;
    }

    public int getUpdateRate() {
        return Math.toIntExact(this.updateRate);
    }

    public long getMoney() {
        return money;
    }

    public GameData getGameData() {
        return gameData;
    }

    public int getHealth() {
       return Math.toIntExact(this.health);
    }

    public int getXp() {
        return Math.toIntExact(this.xp);
    }
}
