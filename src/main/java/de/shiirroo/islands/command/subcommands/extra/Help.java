package de.shiirroo.islands.command.subcommands.extra;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.CommandBuilder;
import de.shiirroo.islands.command.SubCommand;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Help extends SubCommand {

    private static ArrayList<SubCommand> getSubCommands;

    public Help(ArrayList<SubCommand> getSubCommands) {
        Help.getSubCommands = getSubCommands;
    }


    @Override
    public String getName() {
        return "Help";
    }

    @Override
    public String getDescription() {
        return "It will show your information about the plugin";
    }

    @Override
    public String getSyntax() {
        return "/"+ IslandsPlugin.getPlugin().getName() + " Help";
    }

    @Override
    public Boolean getNeedOp() {
        return false;
    }


    @Override
    public CommandBuilder getSubCommandsArgs(String[] args, Player player)  {
        CommandBuilder help = new CommandBuilder("Help");
        for (int i = 0; i != ((getSubCommands.size()) /5); i++) {
            help.addSubCommandBuilder(new CommandBuilder(String.valueOf(i + 1)));
        }
        return help;
    }


    @Override
    public void perform(Player p, String[] args) {
        if(args.length == 1){
            p.sendMessage(ChatColor.WHITE +"--- "+ChatColor.AQUA+"Information about "+ IslandsPlugin.getPlugin().getName()+ " "+ChatColor.GOLD+ "v" + IslandsPlugin.getPlugin().getDescription().getVersion() +ChatColor.WHITE +" - "+ChatColor.GREEN +"Page " +ChatColor.GOLD+ (1)+ChatColor.WHITE+ " | "  +ChatColor.GOLD+  ((getSubCommands.size()) / Math.min(getSubCommands.size(), 5)) +ChatColor.WHITE+" ---");
            for(int i = 0; i<=(Math.min(getSubCommands.size() -1, 4)); i++){
                p.sendMessage(ChatColor.GOLD + getSubCommands.get(i).getSyntax() + ": " + ChatColor.GRAY + getSubCommands.get(i).getDescription());
            }
        } else if(args.length == 2 && Utilis.isNumeric(args[1]) && !args[1].equalsIgnoreCase("0") ){
            int page = Integer.parseInt(args[1]) - 1;
            if((5 * page) < getSubCommands.size()) {
                int CommandSize = 4 + (5 * page);
                if(CommandSize >= getSubCommands.size()){
                    CommandSize = getSubCommands.size() - 1;
                }

                p.sendMessage(ChatColor.WHITE +"--- "+ChatColor.AQUA+"Information about ManHunt "+ChatColor.GOLD+ "v" + IslandsPlugin.getPlugin().getDescription().getVersion() +ChatColor.WHITE +" - "+ChatColor.GREEN +"Page " +ChatColor.GOLD+ (page + 1)+ChatColor.WHITE+ " | "  +ChatColor.GOLD+  ((getSubCommands.size()) / Math.min(getSubCommands.size(), 5)) +ChatColor.WHITE+" ---");
                for (int i = ((5 * page)); i <= CommandSize; i++) {
                    p.sendMessage(ChatColor.GOLD + getSubCommands.get(i).getSyntax() + ": " + ChatColor.GRAY + getSubCommands.get(i).getDescription());
                }
            }
        }
    }
}
