package de.shiirroo.islands.event.player;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.subcommands.islandscommands.CreateWorld;
import de.shiirroo.islands.gamedata.GameData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;

public class onPlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void PlayerJoin(PlayerJoinEvent event) {
        changePlayerName(event.getPlayer());
        event.setJoinMessage(ChatColor.GRAY+ "["+ChatColor.GREEN +"+"+ ChatColor.GRAY + "] " + event.getPlayer().getDisplayName());


        if(event.getPlayer().getWorld().getName().equalsIgnoreCase("world")){
            World world = Bukkit.getWorld("world");
            if(world != null){
                event.getPlayer().getInventory().clear();
                event.getPlayer().teleport(world.getSpawnLocation());
            }
        } else {
            if(event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
            Player player = event.getPlayer();
            GameData gameData = IslandsPlugin.getGameData(event.getPlayer().getUniqueId());
            if(gameData != null){
                if (gameData.getGamePlayers().getGamePlayer(player.getUniqueId()) != null) {
                    player.setGameMode(GameMode.ADVENTURE);
                    CreateWorld.setUPPlayer(gameData, player);
                }
            }
        }
    }

    public static void changePlayerName(Player player){
        player.setPlayerListHeader("\n" + IslandsPlugin.getprefix());
        player.setPlayerListFooter("");
        if(player.isOp()) {
            player.setDisplayName(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED +"ADMIN"+ChatColor.DARK_GRAY+ "] " +  ChatColor.GRAY + player.getName()+ ChatColor.GRAY);
        } else {
            player.setDisplayName(ChatColor.GREEN + player.getName() + ChatColor.RESET + ChatColor.GRAY);
        }

    }
}
