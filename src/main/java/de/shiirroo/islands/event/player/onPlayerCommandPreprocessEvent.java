package de.shiirroo.islands.event.player;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.SaveGameData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class onPlayerCommandPreprocessEvent implements Listener{

    @EventHandler(priority = EventPriority.HIGH)
    public void PlayerCommandPreprocessEvent (PlayerCommandPreprocessEvent event) {
        if(event.getPlayer().isOp()){
            ArrayList<String> chars = new ArrayList<>(Arrays.asList(event.getMessage().split(" ")));
            boolean eventBool = opPlayer(chars, event.getPlayer().getName(), event.getPlayer());
            if (eventBool) event.setCancelled(true);
            else if((chars.size() == 1 && chars.get(0).equalsIgnoreCase("/reload"))||
                    (chars.size() == 2 && chars.get(0).equalsIgnoreCase("/reload") && chars.get(0).equalsIgnoreCase("confirm"))){
                    event.setCancelled(true);
                    for (GameData gameData : IslandsPlugin.getGameData()) {
                        gameData.getGamePlayers().updateGamePlayers();
                        SaveGameData.save(gameData);
                    }
                    Bukkit.getServer().reload();
            }
            else if((chars.size() == 1 && chars.get(0).equalsIgnoreCase("/stop")) || (chars.size() == 1 && chars.get(0).equalsIgnoreCase("stop"))
                    ||(chars.size() == 1 && chars.get(0).equalsIgnoreCase("/restart")) || (chars.size() == 1 && chars.get(0).equalsIgnoreCase("restart"))){
                event.setCancelled(true);
                for (GameData gameData : IslandsPlugin.getGameData()) {
                    gameData.getGamePlayers().updateGamePlayers();
                    SaveGameData.save(gameData);
                }
                Bukkit.spigot().restart();
            }
        }
    }

    public static boolean opPlayer(ArrayList<String> chars, String PlayerName, Player player) {
        if (chars.size() == 2 && (chars.get(0).equalsIgnoreCase("/op") || chars.get(0).equalsIgnoreCase("/deop") || chars.get(0).equalsIgnoreCase("op") || chars.get(0).equalsIgnoreCase("deop"))) {
            Player UpdatePlayer = Bukkit.getPlayer(chars.get(1));
            if (UpdatePlayer != null && UpdatePlayer.isOnline()) {
                if (UpdatePlayer.isOp()) {
                    UpdatePlayer.setOp(false);
                    onPlayerJoin.changePlayerName(UpdatePlayer);
                    if (player != null) player.sendMessage(IslandsPlugin.getprefix() + ChatColor.GOLD + chars.get(1) + ChatColor.GRAY + " has been removed from the operator.");
                    if (!UpdatePlayer.getName().equalsIgnoreCase(PlayerName))
                        UpdatePlayer.sendMessage(IslandsPlugin.getprefix() + "Your operator has been removed");
                } else {
                    UpdatePlayer.setOp(true);
                    onPlayerJoin.changePlayerName(UpdatePlayer);
                    if (player != null)  player.sendMessage(IslandsPlugin.getprefix() + ChatColor.GOLD + chars.get(1) + ChatColor.GRAY + " has been promoted to operator and can now execute Admin Commands.");
                    if (!UpdatePlayer.getName().equalsIgnoreCase(PlayerName))
                        UpdatePlayer.sendMessage(IslandsPlugin.getprefix() + "You became promoted to operator and can now execute Admin commands.");
                }
                return true;
            }
        }
        return false;
    }
}
