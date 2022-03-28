package de.shiirroo.islands.command.subcommands.islandscommands;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.CommandBuilder;
import de.shiirroo.islands.command.SubCommand;
import de.shiirroo.islands.event.menu.MenuManager;
import de.shiirroo.islands.event.menu.MenuManagerException;
import de.shiirroo.islands.event.menu.MenuManagerNotSetupException;
import de.shiirroo.islands.event.menu.menus.FurnaceMenu;
import org.bukkit.entity.Player;

public class Skills extends SubCommand {

    @Override
    public String getName() {
        return "Skills";
    }

    @Override
    public String getDescription() {
        return "Skills Player world";
    }

    @Override
    public String getSyntax() {
        return "/"+ IslandsPlugin.getPlugin().getName() + " Skills";
    }

    @Override
    public Boolean getNeedOp() {
        return false;
    }

    @Override
    public CommandBuilder getSubCommandsArgs(String[] args, Player player) {
        return new CommandBuilder("Skills");
    }

    @Override
    public void perform(Player player, String[] args) {
        try {
            MenuManager.getMenu(FurnaceMenu.class, player.getUniqueId()).setBack(false).open();
        } catch (MenuManagerException | MenuManagerNotSetupException e) {
            e.printStackTrace();
        }
    }
}