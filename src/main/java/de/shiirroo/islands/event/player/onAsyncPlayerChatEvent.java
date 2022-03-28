package de.shiirroo.islands.event.player;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onAsyncPlayerChatEvent implements Listener{


    @EventHandler(priority = EventPriority.HIGH)
    public void AsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        String displayname = event.getPlayer().getDisplayName();
        String message = ChatColor.GRAY +""+ event.getMessage();
        event.setFormat(displayname + ChatColor.GOLD + " >>> " + "" + message);
    }
}
