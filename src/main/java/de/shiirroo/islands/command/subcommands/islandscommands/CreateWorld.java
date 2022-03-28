package de.shiirroo.islands.command.subcommands.islandscommands;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.CommandBuilder;
import de.shiirroo.islands.command.SubCommand;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import de.shiirroo.islands.utilis.WorldGenerator;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.SaveGameData;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import de.shiirroo.islands.gamedata.game.items.GameItems;
import de.shiirroo.islands.utilis.Utilis;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.UUID;

public class CreateWorld extends SubCommand {
    @Override
    public String getName() {
        return "CreateWorld";
    }

    @Override
    public String getDescription() {
        return "Create a "+ IslandsPlugin.getPlugin().getName() + " World";
    }

    @Override
    public String getSyntax() {
        return "/"+ IslandsPlugin.getPlugin().getName() + " WorldName";
    }
    @Override
    public Boolean getNeedOp() {
        return true;
    }

    @Override
    public CommandBuilder getSubCommandsArgs(String[] args, Player player) {
        return null;
    }

    @Override
    public void perform(Player player, String[] args) {
        createWorld(player);
    }

    public static HashSet<UUID> createWorld = new HashSet<>();

    public static void createWorld(Player player){
        if(!createWorld.contains(player.getUniqueId())) {
            createWorld.add(player.getUniqueId());
            GameData gameData = new GameData(player);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GOLD + "World is creating..."));
            IslandsPlugin.getGameData().add(gameData);
            new BukkitRunnable() {

                @Override
                public void run() {
                WorldCreator worldCreator = WorldCreator.name(gameData.getId().toString()).generator(new WorldGenerator());
                worldCreator.generateStructures(false);
                worldCreator.environment(World.Environment.NORMAL);
                worldCreator.createWorld();
                World w = Bukkit.createWorld(worldCreator);
                if(w !=null) {

                    w.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
                    w.setGameRule(GameRule.DO_MOB_SPAWNING, false);
                    w.setGameRule(GameRule.SPAWN_RADIUS, 0);
                    w.setDifficulty(Difficulty.HARD);
                    w.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);


                    GameChunk gameChunk = gameData.getGameArea().getStartChunk();
                    w.setSpawnLocation(gameChunk.getCenterChunkLocation());
                    Chunk chunk = w.getChunkAt(gameChunk.getX(), gameChunk.getZ());


                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            int random = Utilis.generateRandomInt(2);
                            for (int y = 49; y < 70; y++) {
                                if (random == 1 && ((z > 10 || z < 4) && (x > 10 || x < 4))) {
                                    if (y > 51) chunk.getBlock(x, y, z).setType(Material.BARRIER);
                                } else {
                                    if (y > 51) chunk.getBlock(x, y, z).setType(Material.AIR);
                                    else if (y == 49) chunk.getBlock(x, 50, z).setType(Material.GRASS_BLOCK);
                                }
                            }
                        }
                    }


                    Location wl = w.getSpawnLocation();
                    Location HighestLoc = w.getHighestBlockAt(wl).getLocation();
                    HighestLoc.setY(51);
                    w.setSpawnLocation(HighestLoc);
                    player.setGameMode(GameMode.ADVENTURE);
                    gameData.getGamePlayers().addPlayer(player);
                    setUPPlayer(gameData, player);
                    if (SaveGameData.save(gameData)) {
                        player.teleport(w.getSpawnLocation());
                    }

                    }
                }
            }.runTaskAsynchronously(IslandsPlugin.getPlugin());
        }
    }

    public static void setUPPlayer(GameData gameData, Player player){
        player.getInventory().clear();
        player.setLevel(0);
        player.setExp(0);
        player.setFoodLevel(gameData.getGamePlayers().getGamePlayer(player.getUniqueId()).getFood());
        player.setMaxHealth(gameData.getGameStatus().getHealth() + gameData.getGameStatus().getBonusHealth());
        player.setHealth(gameData.getGamePlayers().getGamePlayer(player.getUniqueId()).getHealth());
        player.setGameMode(GameMode.ADVENTURE);
        player.giveExp(gameData.getGameStatus().getXp());
        GameItems gameItems = gameData.getGamePlayers().getGameItems();
        gameItems.getItemCreators().forEach(value -> {
            if (((ToolCreator) value).setToolTyp() != null) player.getInventory().setItem(((ToolCreator) value).setToolTyp().getSlot(), value.getItemStack());
            else if(((ToolCreator) value).slot != null) player.getInventory().setItem(((ToolCreator) value).slot, value.getItemStack());
        });

        for (Integer i : gameData.getGamePlayers().getGamePlayer(player.getUniqueId()).getInventory().keySet()) {
            player.getInventory().setItem(i, gameData.getGamePlayers().getGamePlayer(player.getUniqueId()).getInventory().get(i));
        }
        player.getInventory().close();
        player.setPlayerListFooter(ChatColor.GOLD + "ID: " + ChatColor.GREEN + gameData.getId().getMostSignificantBits());
    }
}
