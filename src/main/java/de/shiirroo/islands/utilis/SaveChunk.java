package de.shiirroo.islands.utilis;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.subcommands.chunk.ChunkCreatorData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class SaveChunk {


    public static long save(ChunkCreatorData chunkCreatorData){
        long saveUPTime = Calendar.getInstance().getTime().getTime();
        File folder = new File("plugins//islands");
        if(!folder.exists()){
            folder.mkdir();
        }
        if(!IslandsPlugin.chunkFolder.exists())
            IslandsPlugin.chunkFolder.mkdir();
        File saveFile =  new File(IslandsPlugin.chunkFolder.getPath() + "//" + chunkCreatorData.getUuid() + ".ser");
        try {

            FileOutputStream outputStream = new FileOutputStream(saveFile);
            BukkitObjectOutputStream oos = new BukkitObjectOutputStream(outputStream);
            oos.writeObject(chunkCreatorData);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().info(IslandsPlugin.getprefix() + ChatColor.RED +"chunk save failed");
        }
        Bukkit.getLogger().info(IslandsPlugin.getprefix() + chunkCreatorData.getUuid() + " was saved. ( " +  (Calendar.getInstance().getTime().getTime() - saveUPTime) + " ms )");
        return (Calendar.getInstance().getTime().getTime() - saveUPTime);
    }

}