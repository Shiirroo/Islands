package de.shiirroo.islands.command.subcommands.extra;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.CommandBuilder;
import de.shiirroo.islands.command.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TPS extends SubCommand {

    @Override
    public String getName() {
        return "TPS";
    }

    @Override
    public String getDescription() {
        return "Show minecraft TPS";
    }

    @Override
    public String getSyntax() {
        return "/"+ IslandsPlugin.getPlugin().getName() + " TPS";
    }

    @Override
    public Boolean getNeedOp() {
        return false;
    }


    @Override
    public CommandBuilder getSubCommandsArgs(String[] args, Player player)  {
        return null;
    }


    @Override
    public void perform(Player player, String[] args) {
        player.sendMessage(IslandsPlugin.getprefix() + ChatColor.GRAY + "TPS from last : [ "+ChatColor.GOLD+"1m"+ChatColor.GRAY+" | "+ChatColor.GOLD+"5m"+ChatColor.GRAY+" | "+ChatColor.GOLD+"15m"+ChatColor.GRAY+" ] : " + goodValue(Bukkit.getTPS()[0])  + ChatColor.GRAY+ " | " + goodValue(Bukkit.getTPS()[1]) + ChatColor.GRAY  + " | " + goodValue(Bukkit.getTPS()[2]));
    }


    public String goodValue(double value){
        if(value >=15) return ""+ChatColor.GREEN + (int) value;
        else if(value >=8) return ""+ChatColor.YELLOW + (int) value;
        else return ""+ChatColor.RED + (int) value;
    }
}
