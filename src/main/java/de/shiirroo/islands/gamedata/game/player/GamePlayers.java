package de.shiirroo.islands.gamedata.game.player;

import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.items.GameItems;
import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class GamePlayers implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private HashSet<UUID> players = new HashSet<>();
    private final LinkedHashMap<UUID, GamePlayer> gamePlayer= new LinkedHashMap<>();
    private final UUID creator;
    private final GameData gameData;
    private final GameItems gameItems;

    public GamePlayers(GameData gameData, Player player){
        this.gameData = gameData;
        this.creator = player.getUniqueId();
        this.players.add(player.getUniqueId());
        this.gameItems = new GameItems();
    }

    public GamePlayers(GameData gameData, GamePlayers gamePlayers){
        this.gameData = gameData;
        this.players.addAll(gamePlayers.getPlayers());
        this.creator = gamePlayers.getCreator();
        this.gameItems = new GameItems(gamePlayers.getGameItems());
        this.gamePlayer.putAll(gamePlayers.getGamePlayers());
    }

    public GameItems getGameItems() {
        return gameItems;
    }

    public void addPlayer(Player player){
        this.players.add(player.getUniqueId());
        this.gamePlayer.put(player.getUniqueId(), new GamePlayer(player, getGameItems()));
    }

    public HashSet<UUID> getPlayers() {
        return players;
    }

    public void setPlayers(HashSet<UUID> players) {
        this.players = players;
    }

    public UUID getCreator() {
        return creator;
    }

    public LinkedHashMap<UUID, GamePlayer> getGamePlayers() {
        return this.gamePlayer;
    }

    public GamePlayer getGamePlayer(UUID uuid){
        return getGamePlayers().get(uuid);
    }

    public void updateGamePlayersItem(ToolCreator newItem){
        for (UUID uuid:players) {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null && player.isOnline()){
                this.gameItems.changeGameItem(newItem);
                player.getInventory().setItem(newItem.setToolTyp().getSlot(), newItem.getItemStack());
            }
        }
    }

    public void updateGamePlayers(){
        for (UUID uuid:players) {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null && player.isOnline() && player.getWorld().getName().equalsIgnoreCase(gameData.getId().toString())){
                GamePlayer gamePlayer = this.getGamePlayer(player.getUniqueId());
                if(gamePlayer != null) {
                    gamePlayer.setInventory(player);
                    gamePlayer.setHealth(player.getHealth());
                    gamePlayer.setFood(player.getFoodLevel());
                }
            }
        }
    }

    public void updateGamePlayer(Player player){
        GamePlayer gamePlayer = this.getGamePlayer(player.getUniqueId());
        if(gamePlayer != null){
            gamePlayer.setInventory(player);
            gamePlayer.setHealth(player.getHealth());
            gamePlayer.setFood(player.getFoodLevel());
        }
    }

}
