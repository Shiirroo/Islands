package de.shiirroo.islands.worker;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.SaveGameData;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class IslandSave implements Runnable{

    @Override
    public void run() {
        for (GameData gameData : IslandsPlugin.getGameData()) {
            World w = Bukkit.getWorld(gameData.getId().toString());
            if (w != null && w.getPlayers().size() > 0) {
                SaveGameData.save(gameData);
            }
        }
    }
}
