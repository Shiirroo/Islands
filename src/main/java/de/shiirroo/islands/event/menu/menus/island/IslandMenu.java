package de.shiirroo.islands.event.menu.menus.island;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.subcommands.Spawn;
import de.shiirroo.islands.event.menu.*;
import de.shiirroo.islands.event.menu.menus.items.CraftingListMenu;
import de.shiirroo.islands.event.menu.menus.items.ItemListMenu;
import de.shiirroo.islands.event.menu.menus.land.MapUpgradeMenu;
import de.shiirroo.islands.event.menu.menus.skill.SkillMenu;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.GameBonusTyps;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class IslandMenu extends Menu {
    public IslandMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public static HashMap<UUID, Menu> mapUpgradeHashMap = new HashMap<>();

    public static HashMap<UUID, Menu> craftingHashMap = new HashMap<>();

    @Override
    public String getMenuName() {
        return ChatColor.GREEN + "Island Menu";
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
        if(Objects.equals(e.getCurrentItem(), CLOSE_ITEM)){
            inventory.close();
        } else if(Objects.equals(e.getCurrentItem(), getMapUpgrade())){
            if(mapUpgradeHashMap.containsKey(uuid)){
                mapUpgradeHashMap.get(uuid).open();
            } else {
                mapUpgradeHashMap.put(uuid, MenuManager.getMenu(MapUpgradeMenu.class, uuid).setBack(true).open());
            }
        } else if(Objects.equals(e.getCurrentItem(), getSkillItem())){
            MenuManager.getMenu(SkillMenu.class, uuid).setBack(true).open();
        } else if(Objects.equals(e.getCurrentItem(), getCraftingTable())){
            if(craftingHashMap.containsKey(uuid)){
                craftingHashMap.get(uuid).open();
            } else {
                craftingHashMap.put(uuid, MenuManager.getMenu(CraftingListMenu.class, uuid).setBack(true).open());
            }
        } else if(Objects.equals(e.getCurrentItem(), getSpawn())){
            GameData gameData = IslandsPlugin.getGameData(uuid);
            if(gameData != null) {
                Spawn.Spawn(getPlayer(), gameData);
            }
        } else if(Objects.equals(e.getCurrentItem(), getItemList())){
            MenuManager.getMenu(ItemListMenu.class, uuid).setBack(true).open();
        } else if(Objects.equals(e.getCurrentItem(), getIslands())){
            MenuManager.getMenu(IslandsListMenu.class, uuid).setBack(true).open();
        }
    }

    @Override
    public void setMenuItems() {


        inventory.setItem(13, getOwnerHead());

        Player player = getPlayer();
        if(player != null && player.isOp()) inventory.setItem(10, getItemList());


        inventory.setItem(16, getIslands());

        inventory.setItem(28, getMapUpgrade());

        inventory.setItem(30, getSpawn());

        inventory.setItem(32, getSkillItem());

        inventory.setItem(34, getCraftingTable());


        inventory.setItem(49, CLOSE_ITEM);

        setFillerGlass(false);
    }

    protected ItemStack getMapUpgrade(){
        ItemStack itemStack = new ItemStack(Material.FILLED_MAP);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN +""+ ChatColor.BOLD + ChatColor.UNDERLINE+ "Buy land");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack getCraftingTable(){
        ItemStack itemStack = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.DARK_GREEN +""+ ChatColor.BOLD + ChatColor.UNDERLINE+ "Crafting");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack getItemList(){
        ItemStack itemStack = new ItemStack(Material.BOOKSHELF);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.DARK_GRAY +""+ ChatColor.BOLD + ChatColor.UNDERLINE+ "Items");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack getSpawn(){
        ItemStack itemStack = new ItemStack(Material.END_CRYSTAL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BLUE +""+ ChatColor.BOLD + ChatColor.UNDERLINE+ "Spawn");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack getIslands(){
        ItemStack itemStack = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GRAY +""+ ChatColor.BOLD + ChatColor.UNDERLINE+ "Islands");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }



    private ItemStack getOwnerHead(){
        ItemStack playHead = Utilis.getPlayHead();
        SkullMeta im = (SkullMeta) playHead.getItemMeta();
        im.setDisplayName(ChatColor.GREEN +""+ ChatColor.BOLD + "Island Stats:");
        GameData gameData = IslandsPlugin.getGameData(uuid);
        if(gameData != null) {
            Player onlinePlayer = Bukkit.getPlayer(gameData.getGamePlayers().getCreator());
            im.setOwner(onlinePlayer != null ? onlinePlayer.getName() : Bukkit.getOfflinePlayer((Objects.requireNonNull(IslandsPlugin.getGameData(uuid)).getGamePlayers().getCreator())).getName());
            ArrayList<String> lore = new ArrayList<>(Arrays.asList("", ChatColor.GOLD + "Bonus: "));
            for(GameBonusTyps bonusTyps: GameBonusTyps.values()){
                    lore.add(ChatColor.GOLD + bonusTyps.name() + ": " + ChatColor.GREEN + ((gameData.getGameStatus().getGameBonus().get(bonusTyps) / bonusTyps.getDeduction())  + (bonusTyps != GameBonusTyps.Health? +100L:0)) + " " + bonusTyps.getAddon());
            }
            im.setLore(lore);

        }
        playHead.setItemMeta(im);
        return playHead;
    }


    protected ItemStack getSkillItem(){
        ItemStack itemStack = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW +""+ ChatColor.BOLD + ChatColor.UNDERLINE+ "Skills");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


}
