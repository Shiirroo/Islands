package de.shiirroo.islands;

import de.shiirroo.islands.command.CommandManager;
import de.shiirroo.islands.command.subcommands.Spawn;
import de.shiirroo.islands.command.subcommands.islandscommands.CreateWorld;
import de.shiirroo.islands.event.block.onBlockBreak;
import de.shiirroo.islands.event.block.onBlockPlace;
import de.shiirroo.islands.event.entity.onEntityDamageEntityEvent;
import de.shiirroo.islands.event.entity.onEntityDamageEvent;
import de.shiirroo.islands.event.menu.MenuManager;
import de.shiirroo.islands.event.onServerCommandEvent;
import de.shiirroo.islands.event.player.*;
import de.shiirroo.islands.event.world.onWorldInitEvent;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.items.gameitems.GameItemList;
import de.shiirroo.islands.utilis.Utilis;
import de.shiirroo.islands.utilis.VersionManager;
import de.shiirroo.islands.worker.GameBlockSpawner;
import de.shiirroo.islands.worker.GameUI;
import de.shiirroo.islands.worker.IslandSave;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;

import java.io.*;
import java.util.*;


public final class IslandsPlugin extends JavaPlugin {
    private static Plugin plugin;
    private static final LinkedHashSet<GameData> gameData = new LinkedHashSet<>();
    public static final File worldFolder = new File("plugins//islands//gameData");
    public static final File chunkFolder = new File("plugins//islands//chunks");
    public static final GameItemList gameItemList= new GameItemList();

    public static GameItemList getGameItemList(){return gameItemList;}

    public static LinkedHashSet<GameData> getGameData() {
        return gameData;
    }

    public static GameData getGameData(UUID uuid){
        Player p = Bukkit.getPlayer(uuid);
        if(p != null) {
            Optional<GameData> optionalGameData = gameData.stream().filter(gd -> gd.getId().toString().equalsIgnoreCase(p.getWorld().getName())).findFirst();
            return optionalGameData.orElse(null);
        }
        return null;
    }

    public static String getprefix() {
        return ChatColor.DARK_GRAY +"["+ ChatColor.GREEN + "Islands" +ChatColor.DARK_GRAY +"] "+ ChatColor.GRAY ;
    }


    @Override
    public void onEnable() {
        plugin = this;
        MenuManager.setup(getServer(), this);

        VersionManager.updateBStats();
        registerEvents();
        plugin.getServer().setDefaultGameMode(GameMode.ADVENTURE);
        plugin.getServer().setSpawnRadius(0);

        Bukkit.getOnlinePlayers().forEach(onPlayerJoin::changePlayerName);

        Bukkit.getOnlinePlayers().forEach(Player -> Player.getInventory().close());


        World world = Bukkit.getWorld("world");
        if(world != null){
            world.setDifficulty(Difficulty.PEACEFUL);
            world.setGameRule(GameRule.DO_MOB_SPAWNING,false);
            world.setGameRule(GameRule.RANDOM_TICK_SPEED,0);
            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN,true);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE,false);

            loadWorlds();

            Bukkit.getOnlinePlayers().forEach(player -> {
            if(!player.getWorld().getName().equalsIgnoreCase("world") && gameData.stream().noneMatch(data -> data.getId().toString().equalsIgnoreCase(player.getWorld().getName()))){
                String w = player.getWorld().getName();
                player.getInventory().clear();
                player.setLevel(0);
                player.setExp(0);
                player.teleport(world.getSpawnLocation());
                Bukkit.unloadWorld(w, false);
                Utilis.deleteRecursively(new File(w), true);
             }
            });
        }



        Objects.requireNonNull(getCommand("Islands")).setExecutor(new CommandManager(plugin.getName()));
        Objects.requireNonNull(getCommand("Islands")).setTabCompleter(new CommandManager(plugin.getName()));
        Objects.requireNonNull(getCommand("Spawn")).setExecutor(new Spawn());
        Objects.requireNonNull(getCommand("Spawn")).setTabCompleter(new Spawn());

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new GameUI(), 0, 5);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new GameBlockSpawner(), 0, 20);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new IslandSave(), 0, 6000);

        //VersionManager.checkVersion();
        Bukkit.getLogger().info(getprefix() +"plugin started.");
    }


    @Override
    public void onDisable() {

    }

    public void loadWorlds() {
        if(new File("plugins//islands").exists()) {
            if (!worldFolder.exists()) {
                worldFolder.mkdir();
            } else {
                for (File f : Objects.requireNonNull(worldFolder.listFiles())) {
                    try {
                        BukkitObjectInputStream oos = new BukkitObjectInputStream(new FileInputStream(f.getPath()));
                        GameData gameData = (GameData) oos.readObject();
                        if(Bukkit.getWorld(gameData.getId().toString()) == null) {
                            World customWorld = new WorldCreator(gameData.getId().toString()).createWorld();
                            Bukkit.getWorlds().add(customWorld);
                        }

                        for (UUID uuid:gameData.getGamePlayers().getPlayers()) {
                            Player player = Bukkit.getPlayer(uuid);
                            if(player != null && player.isOnline() && player.getWorld().getName().equalsIgnoreCase(gameData.getId().toString())) {
                                CreateWorld.setUPPlayer(gameData, player);
                            }
                        }
                        getGameData().add(gameData);

                    } catch (IOException | ClassNotFoundException e) {
                        Bukkit.unloadWorld(f.getName(), false);
                        Utilis.deleteRecursively(new File(f.getName()), true);
                        f.delete();
                        e.printStackTrace();
                    }
                }
                Bukkit.getLogger().info(getprefix() + getGameData().size() +" worlds are loaded.");
            }
        }
    }



    @Override
    public void onLoad() {
        Bukkit.getLogger().info(getprefix() +"plugin is loading.");
    }

    public static Plugin getPlugin(){
        return plugin;
    }


    private void registerEvents(){

        //---------------------BLOCK------------------------
        getServer().getPluginManager().registerEvents(new onBlockPlace(), this);
        getServer().getPluginManager().registerEvents(new onBlockBreak(), this);
        //---------------------Entity-----------------------
        getServer().getPluginManager().registerEvents(new onEntityDamageEvent(), this);
        getServer().getPluginManager().registerEvents(new onEntityDamageEntityEvent(), this);
        //---------------------Player-------------------------
        getServer().getPluginManager().registerEvents(new onInventoryClickEvent(), this);
        getServer().getPluginManager().registerEvents(new onPlayerDropItemEvent(), this);
        getServer().getPluginManager().registerEvents(new onPlayerInteractEvent(), this);
        getServer().getPluginManager().registerEvents(new onPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new onPlayerLeave(), this);
        getServer().getPluginManager().registerEvents(new onPlayerSwapHandItemsEvent(), this);
        getServer().getPluginManager().registerEvents(new onPlayerInteractEntityEvent(), this);
        getServer().getPluginManager().registerEvents(new onPlayerMove(), this);
        getServer().getPluginManager().registerEvents(new onPlayerDeathEvent(), this);
        getServer().getPluginManager().registerEvents(new onAsyncPlayerChatEvent(), this);
        getServer().getPluginManager().registerEvents(new onPlayerCommandPreprocessEvent(), this);
        getServer().getPluginManager().registerEvents(new onPrepareItemCraftEvent(), this);
        getServer().getPluginManager().registerEvents(new onPlayerPickupItemEvent(), this);
        getServer().getPluginManager().registerEvents(new onInventoryOpenEvent(), this);
        //----------------------------------------------------
        getServer().getPluginManager().registerEvents(new onServerCommandEvent(), this);
        //---------------------World-----------------------------
        getServer().getPluginManager().registerEvents(new onWorldInitEvent(), this);

    }

}
