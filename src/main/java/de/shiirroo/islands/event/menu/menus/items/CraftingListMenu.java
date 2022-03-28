package de.shiirroo.islands.event.menu.menus.items;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.event.menu.*;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.items.Item;

import de.shiirroo.islands.gamedata.game.items.ItemTyp;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import de.shiirroo.islands.gamedata.game.player.GamePlayers;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import java.util.stream.Collectors;

public class CraftingListMenu extends Menu {
    public CraftingListMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    private int site = 0;

    public static HashMap<UUID, ItemMenu> craftingMenu = new HashMap<>();


    @Override
    public String getMenuName() {
        return ChatColor.GREEN + "Crafting Menu";
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
        } else if(Objects.equals(e.getCurrentItem(), CLOSE_ITEM)) {
            inventory.close();
            setMenuItems();
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
        } else {
            GameData gameData = IslandsPlugin.getGameData(uuid);
            if (gameData != null) {
                if (Objects.equals(e.getCurrentItem(), getBackPage())) {
                    if (site >= 1) {
                        site--;
                        setMenuItems();
                    }
                } else if (Objects.equals(e.getCurrentItem(), getNextPage())) {
                    if (site < gameData.getGameSkills().getCraftableItems().size() / 24) {
                        site++;
                        setMenuItems();
                    }
                } else {
                    if (displayItems.size() > 0) {
                        if (displayItems.containsKey(e.getCurrentItem())) {
                            Item item = displayItems.get(e.getCurrentItem());
                            if (item != null && item.itemRecipe() != null) {
                                if (craftingMenu.containsKey(uuid)) {
                                    craftingMenu.get(uuid).getLastMenu().setBack(true).setViewMode(false).setItem(item).open();
                                } else {
                                    craftingMenu.put(uuid, new ItemMenu(MenuManager.getMenu(CraftingMenu.class, uuid).setBack(true).setViewMode(false).setItem(item).open()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }




    public static void refreshCraftingMenu(GamePlayers gamePlayers, Item item, Boolean updating){
        for (UUID uuid:gamePlayers.getPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            if(player != null) {
                if (craftingMenu.containsKey(uuid)) {
                    Menu m = craftingMenu.get(uuid).getLastMenu();
                    if(player.getOpenInventory().getTopInventory().equals(m.getInventory())) {
                        if (m.getItem().equals(item)) {
                            if (item instanceof ToolCreator && !updating) {
                                try {
                                    m.back();
                                } catch (MenuManagerException | MenuManagerNotSetupException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                m.setMenuItems();
                            }
                        }
                    }
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

        GameData gameData = IslandsPlugin.getGameData(uuid);
        if(gameData != null) {

            int slot = 0;
            if(itemTyp.equals(ItemTyp.ITEM)) itemList =  gameData.getGameSkills().getCraftableItems().parallelStream().filter(ITEM -> ITEM instanceof ItemCreator).collect(Collectors.toList());
            else if(itemTyp.equals(ItemTyp.TOOL)) itemList =  gameData.getGameSkills().getCraftableItems().parallelStream().filter(ITEM -> ITEM instanceof ToolCreator).collect(Collectors.toList());
            else if(itemTyp.equals(ItemTyp.PLACEDHOLDER) && craftable) itemList =  gameData.getGameSkills().getCraftableItems().parallelStream().filter(ITEM -> ITEM.itemRecipe() != null).collect(Collectors.toList());
            else if(itemTyp.equals(ItemTyp.BLOCK)) itemList =  gameData.getGameSkills().getCraftableItems().parallelStream().filter(ITEM -> ITEM instanceof BlockCreator).collect(Collectors.toList());
            else itemList = gameData.getGameSkills().getCraftableItems();
            displayItems.clear();
            inventory.setItem(4, getSitePage());

            if(itemList.size() > 0) {
                for (int i = 24 * site; i != 24 * (site + 1); i++) {
                    if(i >= itemList.size()) break;
                    if (i != 24 * site && i % 6 == 0) slot += 3;
                    ItemStack itemStack = itemList.get(i).getItemStack();
                    itemStack.setAmount(itemList.get(i).craftItemAmount());
                    inventory.setItem(slot + 11, itemStack);
                    if(itemList.get(i).itemRecipe() != null) displayItems.put(itemStack, itemList.get(i));
                    slot++;
                }
            }

            if (itemList.size() > (24 * (site +1))) inventory.setItem(53, getNextPage());
            else inventory.setItem(53,null);
        }


        inventory.setItem(9, getCraftingSearch());
        inventory.setItem(18, getItemsSearch());
        inventory.setItem(27, getToolSearch());
        inventory.setItem(36, getBlockSearch());


        if(hasBack){
            inventory.setItem(49, BACK_ITEM);
        } else {
            inventory.setItem(49, CLOSE_ITEM);
        }

        setFillerGlass(false);
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
        im.setDisplayName(ChatColor.GOLD + "Page: " + ChatColor.GREEN + (site +1));
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
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
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




}
