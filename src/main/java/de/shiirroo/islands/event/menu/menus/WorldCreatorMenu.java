package de.shiirroo.islands.event.menu.menus;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.subcommands.islandscommands.CreateWorld;
import de.shiirroo.islands.event.menu.Menu;
import de.shiirroo.islands.event.menu.MenuManagerException;
import de.shiirroo.islands.event.menu.MenuManagerNotSetupException;
import de.shiirroo.islands.event.menu.PlayerMenuUtility;
import de.shiirroo.islands.event.player.onPlayerInteractEntityEvent;
import de.shiirroo.islands.event.player.onPlayerInteractEvent;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.player.GamePlayers;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class WorldCreatorMenu extends Menu {

        public WorldCreatorMenu(PlayerMenuUtility playerMenuUtility) {
                super(playerMenuUtility);
        }

        @Override
        public String getMenuName() {
                return ChatColor.GREEN + "Create World";
        }

        @Override
        public InventoryType getInventoryType() {
                return InventoryType.CHEST;
        }

        @Override
        public int getSlots() {
                return 36;
        }

        @Override
        public boolean cancelAllClicks() {
                return true;
        }

        @Override
        public void handleMenuClickEvent(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
                if(Objects.equals(e.getCurrentItem(), CLOSE_ITEM)){
                        close();
                } else  {
                        Player player = (Player) e.getWhoClicked();
                        if(Objects.equals(e.getCurrentItem(), getCreateWorldItemStack())){
                                close();
                                CreateWorld.createWorld(player);
                         } else {
                                ArrayList<GameData> gameDataList = IslandsPlugin.getGameData().stream().filter(gameData -> gameData.getGamePlayers().getPlayers().contains(player.getUniqueId())).collect(Collectors
                                        .toCollection(ArrayList::new));
                                for (GameData gameData : gameDataList) {
                                        if (Objects.equals(e.getCurrentItem(), getGameDataItemStack(gameData))) {
                                                World world = Bukkit.getWorld(gameData.getId().toString());
                                                if(world != null) {
                                                        CreateWorld.setUPPlayer(gameData, player);
                                                        player.teleport(world.getSpawnLocation());
                                                        onPlayerInteractEntityEvent.refreshWorldCreatorMenu(gameData.getGamePlayers());

                                                }
                                        }
                                }
                        }
                }

        }


        @Override
        public void setMenuItems() {
                Player player = Bukkit.getPlayer(uuid);
                if(player != null) {
                        if (!CreateWorld.createWorld.contains(player.getUniqueId())) {
                                ArrayList<GameData> gameDataList = IslandsPlugin.getGameData().stream().filter(gameData -> gameData.getGamePlayers().getPlayers().contains(player.getUniqueId())).collect(Collectors
                                        .toCollection(ArrayList::new));
                                for (int i = 0; i < 4; i++) {
                                        if(gameDataList.size() <= i) {
                                                inventory.setItem(10 + i * 2, getCreateWorldItemStack());
                                        } else {
                                                inventory.setItem(10 + i * 2, getGameDataItemStack(gameDataList.get(i)));
                                        }
                                }

                        }
                }



                inventory.setItem(getSlots() - 5, CLOSE_ITEM);
                setFillerGlass(false);
        }

        public ItemStack getGameDataItemStack(GameData gameData){
                ItemStack itemStack = new ItemStack(Material.GREEN_TERRACOTTA);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Teleport to Island");
                Player player = Bukkit.getPlayer(gameData.getGamePlayers().getCreator());
                String playername = player != null ? player.getName() : Bukkit.getOfflinePlayer(gameData.getGamePlayers().getCreator()).getName();
                World world = Bukkit.getWorld(gameData.getId().toString());
                int onlinePlayers = 0;
                if(world != null) onlinePlayers = world.getPlayers().size();

                ArrayList<String> lore = new ArrayList<>(Arrays.asList(" ",
                        ChatColor.GOLD + "-> GameID: " + ChatColor.GREEN +  gameData.getId(),
                        ChatColor.GOLD + "-> Creator: " + ChatColor.GREEN +  playername,
                        ChatColor.GOLD + "-> Players: " + ChatColor.GREEN +  gameData.getGamePlayers().getPlayers().size(),
                        ChatColor.GOLD + "-> Players-Online: " + ChatColor.GREEN +  onlinePlayers
                        ));

                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
        }

        public ItemStack getCreateWorldItemStack(){
                ItemStack itemStack = new ItemStack(Material.ORANGE_TERRACOTTA);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName("Create or invite to a World");
                itemStack.setItemMeta(itemMeta);
                return itemStack;
        }
}
