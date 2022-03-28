package de.shiirroo.islands.event.menu.menus.land;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.event.menu.menus.island.IslandMenu;
import de.shiirroo.islands.gamedata.game.Rarity;
import de.shiirroo.islands.gamedata.game.chunk.GameBlock;
import de.shiirroo.islands.event.menu.Menu;
import de.shiirroo.islands.event.menu.MenuManagerException;
import de.shiirroo.islands.event.menu.MenuManagerNotSetupException;
import de.shiirroo.islands.event.menu.PlayerMenuUtility;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.SaveGameData;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import de.shiirroo.islands.gamedata.game.chunk.chunks.*;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.player.GamePlayers;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.stream.Collectors;

public class MapUpgradeMenu extends Menu {
    public MapUpgradeMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.GREEN + "Map Upgrade";
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.CHEST;
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenuClickEvent(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        if(Objects.equals(e.getCurrentItem(), BACK_ITEM)){
            back();
        } else if(Objects.equals(e.getCurrentItem(), CLOSE_ITEM)){
            inventory.close();
        } else {

            Player p = Bukkit.getPlayer(uuid);
            if (p != null) {
                Chunk c = p.getChunk();
                GameData gameData = IslandsPlugin.getGameData(uuid);
                if(gameData != null) {
                    for (int z = -2 + c.getZ(); z < 3 + c.getZ(); z++) {
                        for (int x = -4 + c.getX(); x < 5 + c.getX(); x++) {
                            int finalX = x;
                            int finalZ = z;
                            if (Objects.equals(e.getCurrentItem(), getBuyIslandItemStack(finalX, finalZ, gameData)) && gameData.getGameArea().getGameChunks().stream().noneMatch(gameChunk -> gameChunk.sameChunk(finalX, finalZ))) {
                                if (gameData.getGameStatus().getMoney() >= Math.round((100*gameData.getGameArea().getGameChunks().size()+1)*Math.pow(gameData.getGameArea().getGameChunks().size(),2)) -1) {
                                    gameData.getGameStatus().setMoney(gameData.getGameStatus().getMoney() -Math.round((100*gameData.getGameArea().getGameChunks().size()+1)*Math.pow(gameData.getGameArea().getGameChunks().size(),2)) -1);
                                    GameChunk gameChunk = new GameChunk(x, z, p.getWorld().getName(), getChunkCreator());
                                    gameData.getGameArea().getGameChunks().add(gameChunk);
                                    createLand(gameChunk.getChunkCreator(),p.getWorld().getChunkAt(x, z));
                                    refreshWorldCreatorMenu(gameData.getGamePlayers());
                                    SaveGameData.save(gameData);
                                    setMenuItems();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public ChunkCreator getChunkCreator(){
        int random = Utilis.generateRandomInt(100);
        Optional<Rarity> optionalRarity = Arrays.stream(Rarity.values()).filter(rarity -> rarity != null && random >= rarity.getMin() && random <= rarity.getMax()).findFirst();
        Rarity rarity = Rarity.COMMON;
        if(optionalRarity.isPresent()) rarity = optionalRarity.get();
        ChunkPresetList chunkPresetList = new ChunkPresetList();
        Rarity finalRarity = rarity;
        List<ChunkCreator> optionalChunkCreator = chunkPresetList.getChunkPresetList().parallelStream().filter(chunkCreator -> chunkCreator.rarity().equals(finalRarity)).toList();
        ChunkCreator creator = chunkPresetList.getChunkPresetList().get(0);
        if(optionalChunkCreator.size() > 0) creator = optionalChunkCreator.get(Utilis.generateRandomInt(optionalChunkCreator.size()));
        return creator;
    }








    public static void refreshWorldCreatorMenu(GamePlayers gamePlayers){
        for (UUID uuid:gamePlayers.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null) {
                if(IslandMenu.mapUpgradeHashMap.containsKey(uuid)) {
                    Menu m = IslandMenu.mapUpgradeHashMap.get(uuid);
                    if (player.getOpenInventory().getTopInventory().equals(m.getInventory())) {
                        m.setMenuItems();
                    }
                }
            }
        }
    }

    public void createLand(ChunkCreator chunkCreator, Chunk Chunk){
        if(chunkCreator.getChunkCreatorData() != null) {
            boolean rotation = Utilis.generateRandomInt(2) != 1;
            for (GameBlock block : chunkCreator.getChunkCreatorData().getGameBlocks()) {
                if(block.getBlockData().getMaterial().equals(Material.BEDROCK)){
                    BlockCreator blockCreator = chunkCreator.placedOn().get(Utilis.generateRandomInt(chunkCreator.getPlacedOnHashSet().size()));
                    Bukkit.createBlockData(blockCreator.getItemStack().getType()).getAsString();
                    new GameBlock(Bukkit.createBlockData(blockCreator.getItemStack().getType()).getAsString(), block.getX(), block.getY(), block.getZ()).setBlock(Chunk, rotation);
                } else
                block.setBlock(Chunk, rotation);
            }
        }
    }

    @Override
    public void setMenuItems() {
        int slot = 0;
        Player p = Bukkit.getPlayer(uuid);
        if(p != null) {
            Chunk c = p.getChunk();
            GameData gameData = IslandsPlugin.getGameData(uuid);
            if(gameData != null){
                for (int z = -2 + c.getZ(); z < 3+ c.getZ(); z++) {
                    for (int x = -4+ c.getX(); x < 5+ c.getX(); x++) {
                        int finalZ = z;
                        int finalX = x;
                        if(finalX == c.getX() && finalZ == c.getZ()) {
                            inventory.setItem(slot, getPlayerPostion(finalX, finalZ));
                        } else {
                            Optional<GameChunk> optionalGameChunk = gameData.getGameArea().getGameChunks().stream().filter(gameChunk -> gameChunk.sameChunk(finalX, finalZ)).findFirst();
                            if (optionalGameChunk.isPresent()) {
                                inventory.setItem(slot, getIslandItemStack(finalX, finalZ, optionalGameChunk.get()));
                            } else if (hasChunkNeighbor(gameData, finalX, finalZ)) {
                                inventory.setItem(slot, getBuyIslandItemStack(finalX, finalZ, gameData));
                            } else {
                                inventory.setItem(slot, getWaterItemStack(finalX, finalZ));
                            }
                        }
                        slot++;
                    }
                }
            }
        }

        if(hasBack){
            inventory.setItem(49, BACK_ITEM);
        } else {
            inventory.setItem(49, CLOSE_ITEM);
        }

        setFillerGlass(true);
    }

    public boolean hasChunkNeighbor(GameData gameData, int x, int z){
        boolean xPlus = gameData.getGameArea().getGameChunks().stream().anyMatch(gameChunk -> gameChunk.sameChunk(x +1, z));
        boolean xMinus = gameData.getGameArea().getGameChunks().stream().anyMatch(gameChunk -> gameChunk.sameChunk(x-1, z));
        boolean zPlus = gameData.getGameArea().getGameChunks().stream().anyMatch(gameChunk -> gameChunk.sameChunk(x, z +1));
        boolean zMinus = gameData.getGameArea().getGameChunks().stream().anyMatch(gameChunk -> gameChunk.sameChunk(x, z - 1));
        return  (xPlus || xMinus || zPlus || zMinus);
    }


    protected ItemStack getBuyIslandItemStack(int x, int z, GameData gameData){
        ItemStack itemStack = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Buy Land");
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ArrayList<String> lore = new ArrayList<>(List.of("",ChatColor.GOLD+ "Price: " + ChatColor.YELLOW +  Utilis.numberToFormat(Math.round((100*gameData.getGameArea().getGameChunks().size()+1)*Math.pow(gameData.getGameArea().getGameChunks().size(),2)) -1),"",ChatColor.GOLD + "Postion:", ChatColor.GOLD + "X: "+ ChatColor.GREEN + x + ChatColor.GRAY  +" | "+ ChatColor.GOLD + "Z: "+ ChatColor.GREEN + z));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    protected ItemStack getPlayerPostion(int x, int z){
        ItemStack playHead = Utilis.getPlayHead();
        SkullMeta im = (SkullMeta) playHead.getItemMeta();
        im.setDisplayName(ChatColor.DARK_GRAY +""+ ChatColor.BOLD + ChatColor.UNDERLINE+ "You");
        im.setOwner(Objects.requireNonNull(Bukkit.getPlayer(uuid)).getDisplayName());
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        ArrayList<String> lore = new ArrayList<>(List.of("",ChatColor.GOLD + "Postion:", ChatColor.GOLD + "X: "+ ChatColor.GREEN + x  + ChatColor.GRAY + " | "+ ChatColor.GOLD + "Z: "+ ChatColor.GREEN + z));
        im.setLore(lore);
        playHead.setItemMeta(im);
        return playHead;
    }



    protected ItemStack getIslandItemStack(int x, int z, GameChunk gameChunk){
        ItemStack itemStack = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Own Land");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        ArrayList<String> lore = new ArrayList<>(List.of("",ChatColor.GOLD + "Postion:",
                ChatColor.GOLD + "X: "+ ChatColor.GREEN + x  + ChatColor.GRAY +" | "+ ChatColor.GOLD + "Z: "+ ChatColor.GREEN + z));

        if(gameChunk.getChunkCreator().rarity() != null) lore.addAll(List.of("",  ChatColor.GOLD +"Typ: " + gameChunk.getChunkCreator().rarity().getChatColor() + gameChunk.getChunkCreator().chunkDisplayItem().getItemMeta().getDisplayName()));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    protected ItemStack getWaterItemStack(int x, int z){
        ItemStack itemStack = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        //ArrayList<String> lore = Arrays.asList("", ChatColor.GOLD + "X : "+ ChatColor.GREEN + x , ChatColor.GOLD + "Z : "+ ChatColor.GREEN + z);
        itemMeta.setLore(new ArrayList<>());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
