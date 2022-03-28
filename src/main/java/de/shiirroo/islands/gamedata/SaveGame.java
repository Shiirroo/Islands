package de.shiirroo.islands.gamedata;

import de.shiirroo.islands.IslandsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class SaveGame {

    public SaveGame(GameData gameData){
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
