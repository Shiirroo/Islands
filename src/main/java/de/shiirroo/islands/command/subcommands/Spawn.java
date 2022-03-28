package de.shiirroo.islands.command.subcommands;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.SaveGameData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Spawn implements CommandExecutor, TabExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(sender instanceof Player p){
            if(args.length == 0){
                    GameData gameData = IslandsPlugin.getGameData(p.getUniqueId());
                    Spawn(p, gameData);
                    return true;
                }
                p.sendMessage(IslandsPlugin.getprefix() + "Command not found!");
            }
        return false;
        }

    @Override
    public @Nullable ArrayList<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return new ArrayList<>();
    }

    public static void Spawn(Player player, GameData gameData){
        World world = Bukkit.getWorld("world");
        if (world != null) {
        if(gameData != null) {
            gameData.getGamePlayers().updateGamePlayer(player);
            SaveGameData.save(gameData);

        }
        player.resetMaxHealth();
        player.setLevel(0);
        player.setExp(0);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.setPlayerListFooter("");
        player.teleport(world.getSpawnLocation());

        }
    }
}

