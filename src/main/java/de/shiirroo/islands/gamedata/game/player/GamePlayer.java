package de.shiirroo.islands.gamedata.game.player;

import de.shiirroo.islands.gamedata.game.items.GameItems;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class GamePlayer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID uuid;
    private HashMap<Integer, ItemStack> inventory = new HashMap<>();
    private final GameItems gameItems;
    private double health;
    private int food;

    public GamePlayer(GamePlayer gamePlayer) {
        this.uuid = gamePlayer.getUuid();
        this.gameItems = gamePlayer.getGameItems();
        this.inventory.putAll(gamePlayer.getInventory());
        this.health = gamePlayer.getHealth();
        this.food = gamePlayer.getFood();
    }


    public GamePlayer(Player player, GameItems gameItems) {
        this.uuid = player.getUniqueId();
        this.gameItems = gameItems;
        this.inventory = new HashMap<>(savePlayerInventory(player));
        this.health = 6;
        this.food = 20;
    }

    public double getHealth() {
        return health;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public UUID getUuid() {
        return uuid;
    }

    public GameItems getGameItems() {
        return gameItems;
    }

    public HashMap<Integer, ItemStack> getInventory() {
        return inventory;
    }

    public void setInventory(Player player) {
        this.inventory = savePlayerInventory(player);
    }

    private HashMap<Integer, ItemStack> savePlayerInventory(Player player){
        HashMap<Integer, ItemStack> inv = new HashMap<>();

        for (int i = 5; i != player.getInventory().getSize(); i++) {
                ItemStack is = player.getInventory().getItem(i);
                if (is != null && !gameItems.getItemStackList().contains(is)) {
                    inv.put(i, is);
                }
        }
        return inv;
    }
}
