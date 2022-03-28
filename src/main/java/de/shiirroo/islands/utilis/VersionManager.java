package de.shiirroo.islands.utilis;

import de.shiirroo.islands.IslandsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class VersionManager {

    public static void checkVersion() {
        Bukkit.getScheduler().runTaskAsynchronously(IslandsPlugin.getPlugin(), () -> {
            int resourceID = 0;
            try (InputStream inputStream = (new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceID)).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    String latest = scanner.next();
                    String current = IslandsPlugin.getPlugin().getDescription().getVersion();
                    if(Integer.parseInt(current.replace(".", "")) < Integer.parseInt(latest.replaceAll("\\.", ""))){
                        Bukkit.getLogger().info(IslandsPlugin.getprefix() +  "There is a newer version available - v"+ latest + ", you are on - v" + current);
                        Bukkit.getLogger().info(IslandsPlugin.getprefix() +   "Please download the latest version - https://www.spigotmc.org/resources/" + resourceID + "\n");
                    } else {
                        Bukkit.getLogger().info(IslandsPlugin.getprefix() +  "You are running the latest version : v" + current);
                    }
                }
            } catch (IOException e) {
                Bukkit.getLogger().info(IslandsPlugin.getprefix() + ChatColor.RED + "Something went wrong while check version");
            }
        });
    }

    public static void updateBStats(){
        int pluginId = 13795;
        Metrics metrics = new Metrics((JavaPlugin) IslandsPlugin.getPlugin(), pluginId);
    }

}
