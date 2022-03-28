package de.shiirroo.islands.event.player;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.SaveGameData;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

public class onPlayerMove implements Listener {


    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;



        GameData gameData = IslandsPlugin.getGameData(event.getPlayer().getUniqueId());
        if(gameData != null){
            Material m = event.getPlayer().getLocation().getBlock().getType();
            Optional<GameChunk> optionalGameChunk = gameData.getGameArea().getGameChunks().stream().filter(gameChunk -> gameChunk.sameChunk(event.getPlayer().getChunk().getX(), event.getPlayer().getChunk().getZ())).findFirst();
            if(optionalGameChunk.isPresent()) {
                GameChunk gameChunk = optionalGameChunk.get();
                if (m == Material.WATER) {
                    if(!event.getPlayer().getWorld().getHighestBlockAt(gameChunk.getCenterChunkLocation()).getType().equals(Material.WATER) && !event.getPlayer().getWorld().getHighestBlockAt(gameChunk.getCenterChunkLocation()).getType().equals(Material.BARRIER)) {
                        event.getPlayer().teleport(gameChunk.getCenterChunkLocation());
                    } else {
                        Location location = event.getPlayer().getWorld().getSpawnLocation();
                        if(location.getBlock().getType().equals(Material.AIR)){
                            event.getPlayer().teleport(location);
                        } else {
                            event.getPlayer().teleport(location.add(1,0,1));
                        }
                    }
                }
            } else {
                if (m == Material.WATER) {
                    Location location = event.getPlayer().getWorld().getSpawnLocation();
                    if(location.getBlock().getType().equals(Material.AIR)){
                        event.getPlayer().teleport(location);
                    } else {
                        event.getPlayer().teleport(location.add(1,0,1));
                    }


                }
            }
        }
    }
}
