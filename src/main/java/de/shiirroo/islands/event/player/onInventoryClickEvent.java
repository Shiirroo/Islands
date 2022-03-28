package de.shiirroo.islands.event.player;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.event.menu.Menu;
import de.shiirroo.islands.event.menu.MenuManager;
import de.shiirroo.islands.event.menu.MenuManagerException;
import de.shiirroo.islands.event.menu.MenuManagerNotSetupException;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemCreator;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class onInventoryClickEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void InventoryClickEvent(InventoryClickEvent event) {
        if(event.getClickedInventory() != null) if(event.getClickedInventory().getHolder() instanceof Menu) return;
                Player player = (Player) event.getWhoClicked();
                if (player.getGameMode().equals(GameMode.CREATIVE)) return;
                Optional<GameData> optionalGameData = IslandsPlugin.getGameData().stream().filter(gameData -> gameData.getId().toString().equalsIgnoreCase(player.getWorld().getName())).findFirst();
                if (optionalGameData.isPresent()) {
                    ItemStack item;
                    if (event.getAction().name().contains("HOTBAR"))
                        item = event.getView().getBottomInventory().getItem(event.getHotbarButton());
                    else item = event.getCurrentItem();
                    GameData gameData = optionalGameData.get();
                    if(gameData.getGamePlayers().getGameItems().getItemStackList().contains(item)){
                            event.setCancelled(true);
                    }
                }
        }
}

