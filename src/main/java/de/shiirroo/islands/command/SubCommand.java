package de.shiirroo.islands.command;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract Boolean getNeedOp() ;

    public abstract CommandBuilder getSubCommandsArgs(String[] args, Player player);


    public abstract void perform(Player player, String[] args);


}
