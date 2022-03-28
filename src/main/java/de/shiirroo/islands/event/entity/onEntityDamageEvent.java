package de.shiirroo.islands.event.entity;

import de.shiirroo.islands.command.subcommands.islandscommands.CreateWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;

public class onEntityDamageEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void EntityDamageEvent(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player player){
            if(player.getWorld().getName().equalsIgnoreCase("world")){
                event.setCancelled(true);

            }
        }
    }
}
