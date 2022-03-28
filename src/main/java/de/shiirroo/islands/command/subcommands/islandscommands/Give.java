package de.shiirroo.islands.command.subcommands.islandscommands;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.CommandBuilder;
import de.shiirroo.islands.command.SubCommand;
import de.shiirroo.islands.event.player.onPlayerInteractEntityEvent;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.items.ItemTyp;
import de.shiirroo.islands.gamedata.game.items.gameitems.GameItemList;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.C;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Give extends SubCommand {

    @Override
    public String getName() {
        return "Give";
    }

    @Override
    public String getDescription() {
        return "Give Item world";
    }

    @Override
    public String getSyntax() {
        return "/"+ IslandsPlugin.getPlugin().getName() + " Give <ItemTyp> <Item>" ;
    }

    @Override
    public Boolean getNeedOp() {
        return true;
    }

    @Override
    public CommandBuilder getSubCommandsArgs(String[] args, Player player) {
        CommandBuilder give = new CommandBuilder("Give");
        give.addSubCommandBuilder(IslandsPlugin.getGameItemList().getItemsCommand());
        give.addSubCommandBuilder(IslandsPlugin.getGameItemList().getBlocks());
        give.addSubCommandBuilder(IslandsPlugin.getGameItemList().getTools());
        CommandBuilder stats = new CommandBuilder("Stats");
        stats.addSubCommandBuilder(new CommandBuilder("XP").setCustomInput());
        stats.addSubCommandBuilder(new CommandBuilder("Money").setCustomInput());
        give.addSubCommandBuilder(stats);
        return give;
    }

    @Override
    public void perform(Player player, String[] args) {
        if(args.length > 2) {
            GameData gameData = IslandsPlugin.getGameData(player.getUniqueId());
            if(gameData == null) return;
            if(args[2].equalsIgnoreCase("XP") && Utilis.isNumeric(args[3])){
                gameData.getGameStatus().addXp(Integer.parseInt(args[3]));
            } else if(args[2].equalsIgnoreCase("Money") && Utilis.isNumeric(args[3])) {
                gameData.getGameStatus().setMoney(gameData.getGameStatus().getMoney() + Integer.parseInt(args[3]));
            } else {
                Optional<Item> optionalItem = IslandsPlugin.getGameItemList().getItems().stream().filter(item -> ChatColor.stripColor(item.getItemStack().getItemMeta().getDisplayName().replace(" ", "_")).equalsIgnoreCase(args[2])).findFirst();
                if (optionalItem.isPresent()) {
                    Item item = optionalItem.get();
                    ItemStack itemStack = item.getItemStack();
                    if (args[1].equalsIgnoreCase("Tool")) {
                        if (optionalItem.get() instanceof ToolCreator toolCreator)
                            gameData.getGamePlayers().updateGamePlayersItem(toolCreator);
                    } else {

                        if (args.length == 4 && Utilis.isNumeric(args[3]))
                            itemStack.setAmount(Integer.parseInt(args[3]));
                        player.getInventory().addItem(itemStack);
                    }
                }
            }
        }
    }
}