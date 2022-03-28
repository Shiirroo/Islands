package de.shiirroo.islands.command.subcommands.extra;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.CommandBuilder;
import de.shiirroo.islands.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Worlds extends SubCommand {

    @Override
    public String getName() {
        return "Worlds";
    }

    @Override
    public String getDescription() {
        return "Give load world Size";
    }

    @Override
    public String getSyntax() {
        return "/"+ IslandsPlugin.getPlugin().getName() + " Worlds";
    }

    @Override
    public Boolean getNeedOp() {
        return false;
    }


    @Override
    public CommandBuilder getSubCommandsArgs(String[] args, Player player)  {
        return null;
    }

    private final ArrayList<String> worldName =new ArrayList<>(Arrays.asList("world", "world_nether", "world_the_end"));


    @Override
    public void perform(Player player, String[] args) {
       player.sendMessage(IslandsPlugin.getprefix() + "There are " + ChatColor.GREEN + Bukkit.getWorlds().stream().filter(world -> !worldName.contains(world.getName())).count() + ChatColor.GRAY+" worlds loaded." );


    }

}
