package de.shiirroo.islands.command;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.subcommands.Upgrade;
import de.shiirroo.islands.command.subcommands.chunk.AddChunk;
import de.shiirroo.islands.command.subcommands.extra.Help;
import de.shiirroo.islands.command.subcommands.extra.TPS;
import de.shiirroo.islands.command.subcommands.extra.Worlds;
import de.shiirroo.islands.command.subcommands.islandscommands.*;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CommandManager implements TabExecutor {

    private static ArrayList<SubCommand> subcommands;
    public static ArrayList<String> tomanyargs = new ArrayList<>();
    public static ArrayList<String> Commandnotfound = new ArrayList<>();

    public CommandManager(String command){
        if(command.equalsIgnoreCase(IslandsPlugin.getPlugin().getName())) {
            subcommands = new ArrayList<>();
            getSubCommands().add(new TPS());
            getSubCommands().add(new Help(getSubCommands()));
            getSubCommands().add(new CreateWorld());
            getSubCommands().add(new Upgrade());
            getSubCommands().add(new DeleteWorld());
            getSubCommands().add(new Worlds());
            getSubCommands().add(new AddChunk());
            getSubCommands().add(new AddPlayer());
            getSubCommands().add(new SpawnTeleporter());
            getSubCommands().add(new Skills());
            getSubCommands().add(new Items());
            getSubCommands().add(new Give());
            getSubCommands().add(new Islands());

            tomanyargs.add("❌❌❌");
            Commandnotfound.add("❌❌❌");
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(sender instanceof Player p){
            if(args.length == 0){
                Help help = new Help(getSubCommands());
                help.perform(p, args);
            } else {
                Optional<SubCommand> optionalSubCommand = getSubCommands().stream().filter(subCommand -> subCommand.getName().equalsIgnoreCase(args[0])).findFirst();
                if(optionalSubCommand.isPresent()){
                        optionalSubCommand.get().perform(p, args);
                        return true;
                }
                p.sendMessage(IslandsPlugin.getprefix() + "Command not found!");
            }
        }
        return true;
    }



    public static ArrayList<SubCommand> getSubCommands(){
        return subcommands;
    }

    @Override
    public ArrayList<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
    CommandBuilder manHunt = new CommandBuilder("ManHunt");
        Player player = (Player) sender;

        for(SubCommand subCommand:getSubCommands()){
            CommandBuilder sub = subCommand.getSubCommandsArgs(args, player);
            manHunt.addSubCommandBuilder(Objects.requireNonNullElseGet(sub, () -> new CommandBuilder(subCommand.getName(), subCommand.getNeedOp())));
        }

        ArrayList<String> list = getSubComanndsList(args,manHunt, sender.isOp());
        String input = args[args.length-1].toLowerCase();
        ArrayList<String> completions = null;
        if(list == null) return Commandnotfound;
        if(list.size() == 1 && list.get(0).equalsIgnoreCase("customInput")) return new ArrayList<>();
        for(String s: list){
            if(s.toLowerCase().startsWith(input)){
                if(completions ==null)
                    completions = new ArrayList<>();
                completions.add(s.substring(0, 1).toUpperCase() + s.substring(1));
            }
        }

        if(Utilis.isNumeric(input)) return null;
        if(completions == null) return Commandnotfound;
        Collections.sort(completions);
        return completions;
    }


    public static ArrayList<String> getSubComanndsList(String[] args, CommandBuilder cmd, Boolean isOP){
        ArrayList<String> commandList = new ArrayList<>();
        CommandBuilder cB = getSubCommandSearch(args,cmd, 0, isOP);
        if(cB != null){
            if(cB.hasSubCommands())
                commandList = cB.getSubCommandListAsString(isOP);
            else
                commandList.add(cB.getCommandName());
        } else {
               commandList = tomanyargs;
        }
        return commandList;
    }

    public static CommandBuilder getSubCommandSearch(String[] args, CommandBuilder cmd, Integer run, Boolean isOP){
        CommandBuilder command = null;
        if(args.length == 1) return cmd;
        else if (cmd.hasSubCommands()) {
            if(args.length == run) return null;
            CommandBuilder newMainCommand = cmd.getSubCommand(args[run], isOP);
            if (newMainCommand != null){
                if(newMainCommand.isCustomInput()) return new CommandBuilder("customInput");
                if (args[run].equalsIgnoreCase(newMainCommand.getCommandName()))
                    command = getSubCommandSearch(args, newMainCommand, run + 1, isOP);
                else {
                    return null;
                }
            }
            else if (args[args.length - 2].equalsIgnoreCase(cmd.getCommandName()))
                command = cmd;
        }
        return command;
    }

}
