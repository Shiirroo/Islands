package de.shiirroo.islands.gamedata;

import de.shiirroo.islands.gamedata.game.chunk.GameArea;
import de.shiirroo.islands.gamedata.game.player.GamePlayers;
import de.shiirroo.islands.gamedata.game.GameStatus;
import de.shiirroo.islands.gamedata.game.skills.GameSkills;
import org.bukkit.entity.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class GameData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final GamePlayers gamePlayers;
    private final GameStatus gameStatus;
    private final GameArea gameArea;
    private final GameSkills gameSkills;


    public GameData(GameData gameData){
        this.id = gameData.getId();
        this.gameStatus = new GameStatus(this, gameData.getGameStatus());
        this.gamePlayers = new GamePlayers(this, gameData.getGamePlayers());
        this.gameArea = new GameArea(this, gameData.getGameArea());
        this.gameSkills = new GameSkills(this, gameData.getGameSkills());
    }


    public GameData(Player player){
        this.id = UUID.randomUUID();
        this.gameStatus = new GameStatus(this);
        this.gamePlayers = new GamePlayers(this,player);
        this.gameArea = new GameArea(this);
        this.gameSkills = new GameSkills(this);

    }

    public GameSkills getGameSkills() {
        return gameSkills;
    }

    public UUID getId() {
        return id;
    }


    public GamePlayers getGamePlayers() {
        return gamePlayers;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public GameArea getGameArea() {
        return gameArea;
    }
}
