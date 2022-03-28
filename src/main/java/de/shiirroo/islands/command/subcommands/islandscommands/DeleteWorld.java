package de.shiirroo.islands.command.subcommands.islandscommands;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.CommandBuilder;
import de.shiirroo.islands.command.SubCommand;
import de.shiirroo.islands.event.player.onPlayerInteractEntityEvent;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.SaveGameData;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

public class DeleteWorld extends SubCommand {
    @Override
    public String getName() {
        return "DeleteWorld";
    }

    @Override
    public String getDescription() {
        return "Delete world";
    }

    @Override
    public String getSyntax() {
        return "/"+ IslandsPlugin.getPlugin().getName() + " DeleteWorld";
    }

    @Override
    public Boolean getNeedOp() {
        return true;
    }

    @Override
    public CommandBuilder getSubCommandsArgs(String[] args, Player player) {
        CommandBuilder commandBuilder = new CommandBuilder("DeleteWorld");
        for(GameData data:IslandsPlugin.getGameData()){
            commandBuilder.addSubCommandBuilder(new CommandBuilder(Long.toString(data.getId().getMostSignificantBits())));
        }
        return commandBuilder;
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 2){
            if(IslandsPlugin.getGameData().stream().anyMatch(gameData -> gameData.getId().getMostSignificantBits() == Long.parseLong(args[1]))){
                GameData gameData = IslandsPlugin.getGameData(player.getUniqueId());
                if(gameData != null) {
                    World world = Bukkit.getWorld(gameData.getId().toString());
                    if(world != null){
                        world.getPlayers().forEach(p ->{
                                p.getInventory().clear();
                                p.setLevel(0);
                                p.setExp(0);
                                p.resetMaxHealth();
                                p.setHealth(20);
                                p.setFoodLevel(20);
                                p.teleport(Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation());
                                }
                        );
                    }
                    onPlayerInteractEntityEvent.refreshWorldCreatorMenu(gameData.getGamePlayers());
                    Bukkit.unloadWorld(gameData.getId().toString(), false);
                    Utilis.deleteRecursively(new File(gameData.getId().toString()), true);
                    SaveGameData.deleteSave(gameData);
                    IslandsPlugin.getGameData().remove(gameData);

                }
            }
        }
    }
}
