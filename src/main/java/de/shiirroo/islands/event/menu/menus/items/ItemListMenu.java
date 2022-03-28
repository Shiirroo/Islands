package de.shiirroo.islands.event.menu.menus.items;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.event.menu.*;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.items.ItemTyp;

import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ItemListMenu extends Menu {
    public ItemListMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    private int site = 0;

    @Override
    public String getMenuName() {
        return ChatColor.DARK_GREEN + "Item Menu:" + (gameMode.equals(GameMode.CREATIVE) ? ChatColor.DARK_PURPLE + " --> CHEATMODE <--" : "");
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
        Long l = Calendar.getInstance().getTime().getTime();
        if (e.getCurrentItem() == null) return;
        if (Objects.equals(e.getCurrentItem(), BACK_ITEM)) {
            back();
        } else if (Objects.equals(e.getCurrentItem(), CLOSE_ITEM)) {
            inventory.close();
        } else if (Objects.equals(e.getCurrentItem(), getItemsSearch())) {
            craftable = false;
            site = 0;
            if (itemTyp.equals(ItemTyp.ITEM)) itemTyp = ItemTyp.PLACEDHOLDER;
            else itemTyp = ItemTyp.ITEM;
            setMenuItems();
        } else if (Objects.equals(e.getCurrentItem(), getToolSearch())) {
            craftable = false;
            site = 0;
            if (itemTyp.equals(ItemTyp.TOOL)) itemTyp = ItemTyp.PLACEDHOLDER;
            else itemTyp = ItemTyp.TOOL;
            setMenuItems();
        } else if (Objects.equals(e.getCurrentItem(), getBlockSearch())) {
            craftable = false;
            site = 0;
            if (itemTyp.equals(ItemTyp.BLOCK)) itemTyp = ItemTyp.PLACEDHOLDER;
            else itemTyp = ItemTyp.BLOCK;
            setMenuItems();
        } else if (Objects.equals(e.getCurrentItem(), getCraftingSearch())) {
            site = 0;
            if (!craftable) {
                itemTyp = ItemTyp.PLACEDHOLDER;
                craftable = true;
            } else {
                craftable = false;
            }
            setMenuItems();
        } else if (Objects.equals(e.getCurrentItem(), getBackPage())) {
            if (site >= 1) {
                site--;
                setMenuItems();
            }
        } else if (Objects.equals(e.getCurrentItem(), getNextPage())) {
            if (site < IslandsPlugin.getGameItemList().getItems().size() / 24) {
                site++;
                setMenuItems();
            }
        } else {
            if (displayItems.containsKey(e.getCurrentItem())) {
                Item item = displayItems.get(e.getCurrentItem());
                if (gameMode.equals(GameMode.CREATIVE) || cheatMode) {
                    Player player = getPlayer();
                    GameData gameData = IslandsPlugin.getGameData(uuid);
                    if (player != null && gameData != null) {
                        ItemStack itemStack = item.getItemStack();
                        if (item instanceof ToolCreator toolCreator) gameData.getGamePlayers().updateGamePlayersItem(toolCreator);
                        else if (e.getClick().isShiftClick()) {
                            itemStack.setAmount(64);
                            player.getInventory().addItem(itemStack);
                        }
                        else player.getInventory().addItem(itemStack);
                    }
                } else {
                    MenuManager.getMenu(CraftingMenu.class, uuid).setBack(true).setItem(item).setViewMode(true).setCheatMode(gameMode.equals(GameMode.CREATIVE)).open();
                }
            }
        }
    }

    private ItemTyp itemTyp = ItemTyp.PLACEDHOLDER;
    private boolean craftable = false;
    private final HashMap<ItemStack, Item> displayItems = new HashMap<>();
    private List<Item> itemList = new ArrayList<>();


    @Override
    public void setMenuItems() {
        inventory.clear();
        if(site > 0)  inventory.setItem(45,getBackPage());
        else inventory.setItem(45,null);
        if(itemTyp.equals(ItemTyp.ITEM)) itemList =  IslandsPlugin.getGameItemList().getItemItems();
        else if(itemTyp.equals(ItemTyp.TOOL)) itemList =  IslandsPlugin.getGameItemList().getToolItems();
        else if(itemTyp.equals(ItemTyp.PLACEDHOLDER) && craftable) itemList =  IslandsPlugin.getGameItemList().getCraftItems();
        else if(itemTyp.equals(ItemTyp.BLOCK)) itemList =  IslandsPlugin.getGameItemList().getBlockItems();
        else itemList = IslandsPlugin.getGameItemList().getItems();

        displayItems.clear();

        int slot = 0;
        if(itemList.size() > 0) {
            for (int i = 24 * site; i != 24 * (site + 1); i++) {
                if(i >= itemList.size()) break;
                if (i != 24 * site && i % 6 == 0) slot += 3;
                ItemStack itemStack = itemList.get(i).getItemStack();
                inventory.setItem(slot + 11, itemStack);
                if(itemList.get(i).itemRecipe() != null) displayItems.put(itemStack, itemList.get(i));
                slot++;
            }
        }
        if (itemList.size() > (24 * (site +1))) inventory.setItem(53, getNextPage());
        else inventory.setItem(53,null);


        inventory.setItem(4, getSitePage());
        inventory.setItem(9, getCraftingSearch());
        inventory.setItem(18, getItemsSearch());
        inventory.setItem(27, getToolSearch());
        inventory.setItem(36, getBlockSearch());
        inventory.setItem(49, CLOSE_ITEM);


        setFillerGlass(false);
    }


    public ItemStack getBlockSearch(){
        ItemStack itemStack = new ItemStack(Material.COBBLESTONE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Search Blocks");
        if(itemTyp.equals(ItemTyp.BLOCK)) {
        itemMeta.addEnchant(Enchantment.MENDING, 1,true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getItemsSearch(){
        ItemStack itemStack = new ItemStack(Material.IRON_INGOT);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Search Items");
        if(itemTyp.equals(ItemTyp.ITEM)) {
            itemMeta.addEnchant(Enchantment.MENDING, 1,true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public ItemStack getCraftingSearch(){
        ItemStack itemStack = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Search Craftable");
        if(itemTyp.equals(ItemTyp.PLACEDHOLDER) && craftable) {
            itemMeta.addEnchant(Enchantment.MENDING, 1,true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getToolSearch(){
        ItemStack itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Search Tools");
        if(itemTyp.equals(ItemTyp.TOOL)) {
            itemMeta.addEnchant(Enchantment.MENDING, 1,true);
        }
        itemStack.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }





    public ItemStack getSitePage(){
        ItemStack itemStack = new ItemStack(Material.BOOK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + String.valueOf(site + 1 ) + ChatColor.GRAY +" | " + ChatColor.GOLD + (1 + itemList.size()/24));
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(site+1);
        return itemStack;
    }



    public ItemStack getBackPage(){
        ItemStack playHead = Utilis.getPlayHead();
        SkullMeta im = (SkullMeta) playHead.getItemMeta();
        im.setDisplayName(ChatColor.GOLD + "Page: " + ChatColor.GREEN + (site + 1 ));
        im.setOwner("MHF_ArrowLeft");
        playHead.setItemMeta(im);
        return playHead;
    }

    public ItemStack getNextPage(){
        ItemStack playHead = Utilis.getPlayHead();
        SkullMeta im = (SkullMeta) playHead.getItemMeta();
        im.setDisplayName(ChatColor.GOLD +"Page: " + ChatColor.GREEN + (site + 2));
        im.setOwner("MHF_ArrowRight");
        playHead.setItemMeta(im);
        return playHead;
    }



}
