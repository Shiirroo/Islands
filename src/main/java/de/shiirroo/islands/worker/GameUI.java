package de.shiirroo.islands.worker;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import de.shiirroo.islands.utilis.Utilis;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class GameUI implements Runnable{

    @Override
    public void run() {
        for (GameData gameData : IslandsPlugin.getGameData()) {
            World w = Bukkit.getWorld(gameData.getId().toString());
            if (w != null && w.getPlayers().size() > 0) {
                for(UUID uuid: gameData.getGamePlayers().getPlayers()) {
                    Player p = Bukkit.getPlayer(uuid);
                    if(p != null && w.getPlayers().contains(p)){
                        Optional<GameChunk> optionalGameChunk = gameData.getGameArea().getGameChunks().stream().filter(gameChunk -> gameChunk.sameChunk(p.getChunk().getX(), p.getChunk().getZ())).findFirst();
                        if(optionalGameChunk.isPresent()){
                            GameChunk gameChunk = optionalGameChunk.get();
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GOLD + Utilis.numberToFormat(gameData.getGameStatus().getMoney()) + " Gold     "+ gameChunk.getGameStructureList().size() + " | " +gameChunk.getChunkCreator().ChunkPlaceSize() ));

                        }
                    }
                }
            }
        }
    }
}
