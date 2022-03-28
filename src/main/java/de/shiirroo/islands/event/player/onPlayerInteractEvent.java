package de.shiirroo.islands.event.player;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.event.menu.Menu;
import de.shiirroo.islands.event.menu.MenuManager;
import de.shiirroo.islands.event.menu.MenuManagerException;
import de.shiirroo.islands.event.menu.MenuManagerNotSetupException;
import de.shiirroo.islands.event.menu.menus.island.IslandMenu;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.items.etc.MenuItem;
import de.shiirroo.islands.gamedata.game.player.GamePlayers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class onPlayerInteractEvent implements Listener {

    public static HashMap<UUID, Menu> islandMenuHashMap = new HashMap<>();

    UUID uuid = UUID.randomUUID();

    @EventHandler(priority = EventPriority.HIGH)
    private void PlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        GameData gameData = IslandsPlugin.getGameData(event.getPlayer().getUniqueId());
        if(gameData != null){
            if (gameData.getGamePlayers().getGamePlayer(player.getUniqueId()) != null) {
                Item item = new MenuItem();

                if(event.getAction().isRightClick() && Objects.equals(event.getItem(), item.getItemStack())){
                    if(islandMenuHashMap.containsKey(event.getPlayer().getUniqueId())){
                        islandMenuHashMap.get(event.getPlayer().getUniqueId()).open();
                    } else {
                        try {
                            islandMenuHashMap.put(event.getPlayer().getUniqueId(), MenuManager.getMenu(IslandMenu.class, event.getPlayer().getUniqueId()).open());
                        } catch (MenuManagerException | MenuManagerNotSetupException e) {
                            e.printStackTrace();
                        }
                    }
                    }
                }
            }
        }
    public static void refreshIslandMenu(GamePlayers gamePlayers){
        for (UUID uuid:gamePlayers.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null) {
                if (islandMenuHashMap.containsKey(uuid)) {
                    Menu m = islandMenuHashMap.get(uuid);
                    if (player.getOpenInventory().getTopInventory().equals(m.getInventory())) {
                        m.setMenuItems();
                    }
                }
            }
        }
    }
}
