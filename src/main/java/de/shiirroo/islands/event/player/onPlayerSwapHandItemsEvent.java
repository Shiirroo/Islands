package de.shiirroo.islands.event.player;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class onPlayerSwapHandItemsEvent implements Listener{

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        event.setCancelled(true);
    }
}
