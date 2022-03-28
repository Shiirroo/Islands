package de.shiirroo.islands.event.block;

import de.shiirroo.islands.CraftCustomZombie;
import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.gamedata.GameData;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class onBlockPlace implements Listener{

    @EventHandler(priority = EventPriority.HIGH)
    private void BlockPlace(BlockPlaceEvent event) {
        //NPCUtil.spawnNPC(event.getPlayer());

        CraftCustomZombie cZombie = new CraftCustomZombie(event.getBlock().getLocation());

        cZombie.setPos(event.getBlock().getX(), event.getBlock().getY(),event.getBlock().getZ());


        GameData gameData = IslandsPlugin.getGameData(event.getPlayer().getUniqueId());
        if(gameData != null) {
            Chunk blockChunk = event.getBlock().getChunk();
            if(gameData.getGameArea().getGameChunks().stream().anyMatch(gameChunk -> gameChunk.sameChunk(blockChunk.getX(), blockChunk.getZ()))){
                if(event.getBlock().getType().equals(Material.LILY_PAD)) {
                    for (int y = 52; y < 80; y++) blockChunk.getBlock(event.getBlock().getX() & 0x000F, y, event.getBlock().getZ() & 0x000F).setType(Material.AIR);
                        return;
                    }
                }else {
                event.getPlayer().getInventory().setItemInMainHand(event.getItemInHand());
                event.getPlayer().getInventory().setItemInOffHand(event.getPlayer().getInventory().getItemInOffHand());
            }
        }
        if(!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }
}
