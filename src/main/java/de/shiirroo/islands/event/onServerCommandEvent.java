package de.shiirroo.islands.event;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.event.menu.PlayerMenuUtility;
import de.shiirroo.islands.event.player.onPlayerCommandPreprocessEvent;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.SaveGame;
import de.shiirroo.islands.gamedata.SaveGameData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class onServerCommandEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void ServerCommandEvent(ServerCommandEvent event) {
            ArrayList<String> chars = new ArrayList<>(Arrays.asList(event.getCommand().split(" ")));
            if(onPlayerCommandPreprocessEvent.opPlayer(chars, ChatColor.RED  + "SERVER", null)) event.setCancelled(true);
            else if((chars.size() == 1 && chars.get(0).equalsIgnoreCase("/reload"))|| (chars.size() == 1 && chars.get(0).equalsIgnoreCase("reload")) ||
                    (chars.size() == 2 && chars.get(0).equalsIgnoreCase("/reload") && chars.get(1).equalsIgnoreCase("confirm"))
                    || (chars.size() == 2 && chars.get(0).equalsIgnoreCase("reload") && chars.get(1).equalsIgnoreCase("confirm")) ){
                    for (GameData gameData : IslandsPlugin.getGameData()) {
                        gameData.getGamePlayers().updateGamePlayers();
                        save(gameData);
                    }

        } else if((chars.size() == 1 && chars.get(0).equalsIgnoreCase("/stop")) || (chars.size() == 1 && chars.get(0).equalsIgnoreCase("stop"))
          ||(chars.size() == 1 && chars.get(0).equalsIgnoreCase("/restart")) || (chars.size() == 1 && chars.get(0).equalsIgnoreCase("restart"))){
            event.setCancelled(true);
            for (GameData gameData : IslandsPlugin.getGameData()) {
                gameData.getGamePlayers().updateGamePlayers();
                SaveGameData.save(gameData);
            }
            Bukkit.spigot().restart();
        }
    }


    public void  save(GameData gameData){
        long saveUPTime = Calendar.getInstance().getTime().getTime();
        File folder = new File("plugins//islands");
        if(!folder.exists()){
            folder.mkdir();
        }
        if(!IslandsPlugin.worldFolder.exists())
            IslandsPlugin.worldFolder.mkdir();
        File saveFile =  new File(IslandsPlugin.worldFolder.getPath() + "//" + gameData.getId() + ".ser");
        try {

            FileOutputStream outputStream = new FileOutputStream(saveFile);
            BukkitObjectOutputStream oos = new BukkitObjectOutputStream(outputStream);
            GameData newGameData = new GameData(gameData);
            oos.writeObject(newGameData);
            oos.flush();
        } catch (IOException e) {
            Bukkit.getLogger().info(IslandsPlugin.getprefix() + ChatColor.RED +"gameData save failed");
        }
        Bukkit.getLogger().info(IslandsPlugin.getprefix() + gameData.getId() + " was saved. ( " +  (Calendar.getInstance().getTime().getTime() - saveUPTime) + " ms )");
    }

}