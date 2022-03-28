package de.shiirroo.islands.command.subcommands.islandscommands;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.CommandBuilder;
import de.shiirroo.islands.command.SubCommand;
import de.shiirroo.islands.event.player.onPlayerInteractEntityEvent;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.SaveGameData;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class AddPlayer extends SubCommand {

    @Override
    public String getName() {
        return "AddPlayer";
    }

    @Override
    public String getDescription() {
        return "Add Player world";
    }

    @Override
    public String getSyntax() {
        return "/"+ IslandsPlugin.getPlugin().getName() + " AddPlayer <GameID> <Player>" ;
    }

    @Override
    public Boolean getNeedOp() {
        return true;
    }

    @Override
    public CommandBuilder getSubCommandsArgs(String[] args, Player player) {
        ArrayList<GameData> gameDataList = IslandsPlugin.getGameData().stream().filter(gameData -> gameData.getGamePlayers().getCreator().equals(player.getUniqueId())).collect(Collectors
                .toCollection(ArrayList::new));
        CommandBuilder commandBuilder = new CommandBuilder("AddPlayer");
        for(GameData gameData:gameDataList){
            CommandBuilder gameDatCommandBuilder = new CommandBuilder(Long.toString(gameData.getId().getMostSignificantBits()));
            for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
                if(!gameData.getGamePlayers().getPlayers().contains(onlinePlayer.getUniqueId())){
                    if(IslandsPlugin.getGameData().stream().filter(gd -> gd.getGamePlayers().getPlayers().contains(onlinePlayer.getUniqueId())).count() <= 4) {
                        gameDatCommandBuilder.addSubCommandBuilder(new CommandBuilder(onlinePlayer.getName()));
                    }
                }
            }
            if(gameDatCommandBuilder.hasSubCommands()) commandBuilder.addSubCommandBuilder(gameDatCommandBuilder);
        }
        return commandBuilder;
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length == 3){
                Optional<GameData> optionalGameData = IslandsPlugin.getGameData().stream().filter(gameData -> gameData.getId().getMostSignificantBits() == Long.parseLong(args[1]) && gameData.getGamePlayers().getCreator().equals(player.getUniqueId())).findFirst();
                if(optionalGameData.isPresent()){
                    GameData gameData = optionalGameData.get();
                    Player addPlayer = Bukkit.getPlayer(args[2]);
                    if(addPlayer != null){
                        if(IslandsPlugin.getGameData().stream().filter(gd -> gd.getGamePlayers().getPlayers().contains(addPlayer.getUniqueId())).count() <= 4) {
                            if (!gameData.getGamePlayers().getPlayers().contains(addPlayer.getUniqueId())) {
                                player.sendMessage(IslandsPlugin.getprefix() + addPlayer.getName() + " was added to the island");
                                addPlayer.sendMessage(IslandsPlugin.getprefix() + "You would be added to the island");
                                gameData.getGamePlayers().addPlayer(addPlayer);
                                onPlayerInteractEntityEvent.refreshWorldCreatorMenu(gameData.getGamePlayers());
                            } else player.sendMessage(IslandsPlugin.getprefix() + ChatColor.RED + "Player has already been added to the island.");
                        } else player.sendMessage(IslandsPlugin.getprefix() + ChatColor.RED + "The player has reached his maximum number of islands.");
                } else player.sendMessage(IslandsPlugin.getprefix() + ChatColor.RED + "Player not found.");
            } else player.sendMessage(IslandsPlugin.getprefix() + ChatColor.RED + "You don't have an island of your own.");
        } else player.sendMessage(getSyntax());

    }
}