package de.shiirroo.islands.command.subcommands.islandscommands;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.CommandBuilder;
import de.shiirroo.islands.command.SubCommand;
import de.shiirroo.islands.event.menu.MenuManager;
import de.shiirroo.islands.event.menu.MenuManagerException;
import de.shiirroo.islands.event.menu.MenuManagerNotSetupException;
import de.shiirroo.islands.event.menu.menus.items.ItemListMenu;
import org.bukkit.entity.Player;

public class Items extends SubCommand {
    @Override
    public String getName() {
        return "Items";
    }

    @Override
    public String getDescription() {
        return "Show all ingame Items";
    }

    @Override
    public String getSyntax() {
        return "/" + IslandsPlugin.getPlugin().getName() + " Items";
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
            MenuManager.getMenu(ItemListMenu.class, player.getUniqueId()).open();
        } catch (MenuManagerException | MenuManagerNotSetupException e) {
            e.printStackTrace();
        }

    }
}
