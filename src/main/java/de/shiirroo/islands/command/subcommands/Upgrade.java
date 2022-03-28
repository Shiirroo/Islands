package de.shiirroo.islands.command.subcommands;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.CommandBuilder;
import de.shiirroo.islands.command.SubCommand;
import de.shiirroo.islands.event.menu.MenuManager;
import de.shiirroo.islands.event.menu.MenuManagerException;
import de.shiirroo.islands.event.menu.MenuManagerNotSetupException;
import de.shiirroo.islands.event.menu.menus.land.MapUpgradeMenu;
import org.bukkit.entity.Player;

public class Upgrade extends SubCommand {
    @Override
    public String getName() {
        return "Upgrade";
    }

    @Override
    public String getDescription() {
        return "Upgrade mal Size";
    }

    @Override
    public String getSyntax() {
        return "/"+ IslandsPlugin.getPlugin().getName() +" Upgrade";
    }

    @Override
    public Boolean getNeedOp() {
        return false;
    }

    @Override
    public CommandBuilder getSubCommandsArgs(String[] args, Player player) {
        return null;
    }

    @Override
    public void perform(Player player, String[] args) {
        try {
            MenuManager.getMenu(MapUpgradeMenu.class, player.getUniqueId()).open();
        } catch (MenuManagerException | MenuManagerNotSetupException e) {
            e.printStackTrace();
        }

    }
}
