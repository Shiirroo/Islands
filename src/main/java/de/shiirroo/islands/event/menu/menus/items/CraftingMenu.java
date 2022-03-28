package de.shiirroo.islands.event.menu.menus.items;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.event.menu.*;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.items.gameitems.RecipeCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class CraftingMenu extends Menu {
    public CraftingMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    private int site = 0;

    @Override
    public String getMenuName() {
        return ChatColor.DARK_GRAY +""+ ChatColor.BOLD + (isViewMode()? "View: ": "Craft: ") + getItem().getItemStack().getItemMeta().getDisplayName();
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
        return false;
    }

    @Override
    public void handleMenuClickEvent(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        if(e.getClickedInventory() != null && e.getClickedInventory().equals(inventory)) {
            e.setCancelled(true);
            if (Objects.equals(e.getCurrentItem(), BACK_ITEM)) {
                back();
            } else if (Objects.equals(e.getCurrentItem(), CLOSE_ITEM)) {
                inventory.close();
            }  else if (Objects.equals(e.getCurrentItem(), getBackPage())) {
                if (site >= 1) {
                    site--;
                    setMenuItems();
                }
            } else if (Objects.equals(e.getCurrentItem(), getNextPage())) {
                if (site < getItem().itemRecipe().size()) {
                    site++;
                    setMenuItems();
                }
            } else if (!viewMode) {
                GameData gameData = IslandsPlugin.getGameData(uuid);
                if (gameData != null) {
                    if(craftItem != null){
                        if(Objects.equals(e.getCurrentItem(), craftItem)){
                            if(gameData.getGameSkills().getCraftableItems().contains(getItem())) {
                                craftItem(gameData, e.getSlot());
                            }
                        } else {
                            if (e.getCurrentItem() != null){
                                Optional<Item> optionalItemCreator = gameData.getGameSkills().getCraftableItems().parallelStream().filter(itemCreator -> itemCreator.itemRecipe() != null && itemCreator.getItemStack().getItemMeta().equals(e.getCurrentItem().getItemMeta())).findFirst();
                                if (optionalItemCreator.isPresent() && e.getSlot() != 23) {
                                    if (CraftingListMenu.craftingMenu.containsKey(uuid)) {
                                        ItemMenu itemMenu = CraftingListMenu.craftingMenu.get(uuid);
                                        if (itemMenu != null) {
                                            itemMenu.addMenu(MenuManager.getMenu(CraftingMenu.class, uuid).setBack(true).setItem(optionalItemCreator.get()).setLastItem(getLastItemList()).addLastMenu(getItem()).open());
                                        } else {
                                            CraftingListMenu.craftingMenu.put(uuid, new ItemMenu(MenuManager.getMenu(CraftingMenu.class, uuid).setBack(true).setItem(optionalItemCreator.get()).setLastItem(getLastItemList()).addLastMenu(getItem()).open()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if(e.getCurrentItem() != null && e.getSlot() != 23){
                ItemStack itemStack = e.getCurrentItem();
                Optional<Item> optionalItem = IslandsPlugin.getGameItemList().getCraftItems().parallelStream().filter(stack -> stack.getItemStack().getItemMeta().equals(itemStack.getItemMeta())).findFirst();
                if(optionalItem.isPresent()){
                        Item item = optionalItem.get();
                            if (CraftingListMenu.craftingMenu.containsKey(uuid)) {
                                ItemMenu itemMenu = CraftingListMenu.craftingMenu.get(uuid);
                                if (itemMenu != null) {
                                    itemMenu.addMenu(MenuManager.getMenu(CraftingMenu.class, uuid).setBack(true).setLastItem(getLastItemList()).addLastMenu(getItem()).setItem(item).setViewMode(true).open());
                                }
                            } else {
                                CraftingListMenu.craftingMenu.put(uuid, new ItemMenu((MenuManager.getMenu(CraftingMenu.class, uuid).setBack(true).setLastItem(getLastItemList()).addLastMenu(getItem()).setItem(item).setViewMode(true).open())));
                        }
                    }
                }
            }
        }
    }

    private ItemStack craftItem = null;


    private void craftItem(GameData gameData, Integer slot){
            Player player = getPlayer();
            if (player == null || slot != 23) return;
            RecipeCreator recipe = item.itemRecipe().get(site);


            for (Item craftingItems : recipe.getItems().keySet()) {
                if (sizeHasItems(player, craftingItems.getItemStack()) < recipe.getItems().get(craftingItems)) {
                    return;
                }
            }
            ItemStack itemStack = getItem().getItemStack();
            ItemStack[] inv = player.getInventory().getStorageContents();

            if(!getItemSpace(itemStack, inv)) return;

        for (Item craftingItems : recipe.getItems().keySet()) {
               removeItems(player.getInventory(), craftingItems.getItemStack(), recipe.getItems().get(craftingItems));
           }

           if(getItem() instanceof ToolCreator toolCreator){
               if(gameData.getGameSkills().getCraftableItems().remove(getItem())) {
                   gameData.getGamePlayers().updateGamePlayersItem(toolCreator);
                   if( CraftingListMenu.craftingMenu.containsKey(uuid)){
                       CraftingListMenu.craftingMenu.get(uuid).addMenu(this);
                   }
                   CraftingListMenu.refreshCraftingMenu(gameData.getGamePlayers(), getItem(), false);
               }
           } else {
               itemStack.setAmount(getItem().craftItemAmount());
               player.getInventory().addItem(itemStack);
               //setMenuItems();
               CraftingListMenu.refreshCraftingMenu(gameData.getGamePlayers(), getItem(), true);
           }
    }

    public boolean getItemSpace(ItemStack itemStack, ItemStack[] inv){
        for(ItemStack invItemStack : inv) {
            if(invItemStack == null) return true;
            else if(invItemStack.getItemMeta().equals(itemStack.getItemMeta()) && (invItemStack.getAmount() + getItem().getItemStack().getAmount()) <= invItemStack.getMaxStackSize()) return true;
        }
        return false;

    }


    public static int removeItems(Inventory inventory, ItemStack itemStack, int amount) {
        if(itemStack == null || inventory == null || amount <= 0)
            return -1;
        if (amount == Integer.MAX_VALUE) {
            inventory.remove(itemStack);
            return 0;
        }
        itemStack.setAmount(amount);
        HashMap<Integer,ItemStack> retVal = inventory.removeItem(itemStack);
        int notRemoved = 0;
        for(ItemStack item: retVal.values()) {
            notRemoved+=item.getAmount();
        }
        return notRemoved;
    }



    public int sizeHasItems(Player player, ItemStack itemStack){
        int amount = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getItemMeta().equals(itemStack.getItemMeta())) {
                amount += item.getAmount();
            }
        }
        return amount;
    }


    @Override
    public void setMenuItems(){
        if(site + 1 > getItem().itemRecipe().size()) site = 0;
        inventory.clear();
        craftItem = null;
        if(site > 0)  inventory.setItem(45,getBackPage());
        else inventory.setItem(45,null);
        GameData gameData = IslandsPlugin.getGameData(uuid);
        if(gameData != null && getItem() != null) {
                Item item = getItem();
                Player player = Bukkit.getPlayer(uuid);
                    if(player != null){
                        if(item.itemRecipe() != null) {
                            RecipeCreator recipe = item.itemRecipe().get(site);
                            List<String> lore = new ArrayList<>();
                            boolean canCraftItem = true;
                            row = 0;
                            for (Item craftingItem: recipe.getItems().keySet()) {
                                ItemStack itemStack = craftingItem.getItemStack();
                                int playerInventoryItemSize = sizeHasItems(player, craftingItem.getItemStack());
                                int ItemNeedSize = recipe.getItems().get(craftingItem);
                                if(playerInventoryItemSize < ItemNeedSize){
                                    canCraftItem = false;
                                    lore.add(itemStack.getItemMeta().getDisplayName() +": " + ChatColor.RED + playerInventoryItemSize + ChatColor.GRAY + " | " + ChatColor.GOLD + ItemNeedSize);
                                } else {
                                    lore.add(itemStack.getItemMeta().getDisplayName() +": " + ChatColor.GREEN + playerInventoryItemSize + ChatColor.GRAY +  " | " + ChatColor.GOLD + ItemNeedSize);
                                }
                                    int amount = ItemNeedSize;
                                    while (amount > 64){
                                        setItem(64, itemStack);
                                        amount-= 64;
                                    }
                                    setItem(amount, itemStack);
                            }
                            craftItem = item.getItemStack();
                            craftItem.setAmount(item.craftItemAmount());
                            inventory.setItem(23, craftItem);
                            inventory.setItem(25, setPaperItem(lore));
                            for(int i = 46; i<53;i++){
                                if(i != 49 && !viewMode) {
                                    if(canCraftItem){
                                        inventory.setItem(i, getCanCraftItem());
                                    }
                                    else inventory.setItem(i, getCanNotCraftItem());
                                }
                        }
                    } else {
                            close();
                        }
                }
            if (item.itemRecipe().size() > 1 && site < item.itemRecipe().size() -1) inventory.setItem(53, getNextPage());
            else inventory.setItem(53,null);

            }

        inventory.setItem(4, getSitePage());

        if(hasBack){
            inventory.setItem(49, BACK_ITEM);
        } else {
            inventory.setItem(49, CLOSE_ITEM);
        }

        setFillerGlass(false);
    }

    private int row;


    private ItemStack setPaperItem(List<String> lore){
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Recipe-Items:");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }



    private void setItem(int amount,ItemStack craftingItems){
        if (row == 3 || row == 12) row+=6;
        craftingItems.setAmount(amount);
        inventory.setItem(row + 10 , craftingItems);
        row++;
    }

    public ItemStack getCanCraftItem(){
        ItemStack itemStack = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName("");
        itemStack.setItemMeta(im);
        return itemStack;
    }


    public ItemStack getCanNotCraftItem(){
        ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName("");
        itemStack.setItemMeta(im);
        return itemStack;
    }

    public ItemStack getSitePage(){
        ItemStack itemStack = new ItemStack(Material.BOOK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + String.valueOf(site + 1 ) + ChatColor.GRAY +" | " + ChatColor.GOLD + ((getItem().itemRecipe() != null ? getItem().itemRecipe().size(): 0)));
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
