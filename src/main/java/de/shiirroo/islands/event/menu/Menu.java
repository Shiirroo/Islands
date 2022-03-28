package de.shiirroo.islands.event.menu;
import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.skills.SkillUnlock;
import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class Menu implements InventoryHolder {

    protected PlayerMenuUtility playerMenuUtility;
    protected UUID uuid;
    protected GameMode gameMode;
    protected Inventory inventory;
    protected boolean hasBack = false;
    protected boolean cheatMode = false;
    protected boolean viewMode = false;
    protected Item item;
    protected List<Item> lastItem = new ArrayList<>();
    protected SkillUnlock skillUnlock;
    protected String bacKName;

    protected ItemStack FILLER_GLASS = makeItem(Material.GRAY_STAINED_GLASS_PANE, " ");
    protected ItemStack CLOSE_ITEM = makeItem(Material.BARRIER, ChatColor.RED + "CLOSE");
    ArrayList<DyeColor> colorBACK = new ArrayList<>(Arrays.asList(DyeColor.BLACK, DyeColor.BLACK, DyeColor.WHITE,DyeColor.WHITE, DyeColor.WHITE));
    ArrayList<PatternType> patternTypeBACK = new ArrayList<>(Arrays.asList(PatternType.STRIPE_LEFT, PatternType.STRIPE_MIDDLE, PatternType.STRIPE_TOP,PatternType.STRIPE_BOTTOM, PatternType.CURLY_BORDER));
    protected ItemStack BACK_ITEM = getItemStackBanner(ChatColor.GREEN + "BACK", Material.WHITE_BANNER, colorBACK, patternTypeBACK, ChatColor.BLUE);
    protected String name;
    public Menu(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
        this.uuid = playerMenuUtility.getUuid();
        this.gameMode = Objects.requireNonNull(Bukkit.getPlayer(playerMenuUtility.getUuid())).getGameMode();
    }

    public Item getItem() {
        return item;
    }

    public Menu setSkillUnlock(SkillUnlock skillUnlock){
        this.skillUnlock = skillUnlock;
        return this;
    }

    public SkillUnlock getSkillUnlock() {
        return skillUnlock;
    }

    public Menu setBackName(String bacKName){
        this.bacKName = bacKName;
        return this;
    }

    public String getBacKName() {
        return bacKName;
    }

    public Item getLastItem() {
        if(lastItem.size() >= 1) return lastItem.get(lastItem.size() -1);
        return null;
    }
    public List<Item> getLastItemList() {
        return this.lastItem;
    }

    public Menu setItem(Item item) {
        this.item = item;
        return this;
    }

    public Menu setLastItem(List<Item> lastItem) {
        this.lastItem.addAll(lastItem);
        return this;
    }

    public Menu addLastMenu(Item lastItem){
        this.lastItem.add(lastItem);
        return this;
    }

    public abstract String getMenuName();

    public abstract InventoryType getInventoryType();

    public abstract int getSlots();

    public abstract boolean cancelAllClicks();

    public abstract void handleMenuClickEvent(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException;

    public abstract void setMenuItems();

    public Menu setBack(boolean b){
        this.hasBack = b;
        return this;
    }

    public Menu setName(String name){
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public Menu setCheatMode(boolean cheat){
        this.cheatMode = cheat;
        return this;
    }

    public boolean isCheatMode() {
        return cheatMode;
    }

    public boolean isViewMode() {
        return viewMode;
    }

    public Menu setViewMode(boolean viewMode){
        this.viewMode = viewMode;
        return this;
    }

    public Menu open() {
        Player player = Bukkit.getPlayer(playerMenuUtility.getUuid());
        if(player != null) {
            if ((getInventoryType() == null || getInventoryType().equals(InventoryType.CHEST)) && getSlots() != 0) {
                inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

            } else if (getInventoryType().equals(InventoryType.PLAYER)) {
                inventory = player.getInventory();
                playerMenuUtility.setData(player.getUniqueId().toString(), this);

            } else {
                inventory = Bukkit.createInventory(this, getInventoryType(), getMenuName());
            }

            this.setMenuItems();

            if (!getInventoryType().equals(InventoryType.PLAYER))
                player.openInventory(inventory);
            playerMenuUtility.pushMenu(this);
        }
        return this;
    }


    public void back() throws MenuManagerException, MenuManagerNotSetupException {
        Menu menu = MenuManager.getMenu(playerMenuUtility.lastMenu().getClass(), playerMenuUtility.getUuid());
        menu.setBack(hasBack);
        if(lastItem != null && lastItem.size() >= 1){
            Item lastItemInt = getLastItem();
            List<Item> itemList = getLastItemList();
            itemList.remove(lastItemInt);
            menu.setLastItem(itemList);
            menu.setItem(lastItemInt);
        }
        if(getBacKName() != null) menu.setName(getBacKName());

        menu.setViewMode(viewMode).setCheatMode(cheatMode).open();
    }

    public void close() {
        inventory.close();
    }


    protected void reloadItems() {
        for (int i = 0; i < inventory.getSize(); i++){
            inventory.setItem(i, null);
        }
        setMenuItems();
    }

    protected void reload() throws MenuManagerException, MenuManagerNotSetupException {
        Player player = Bukkit.getPlayer(playerMenuUtility.getUuid());
        if(player != null) {
            player.closeInventory();
            MenuManager.getMenu(this.getClass(), uuid).open();
        }
    }

    protected Player getPlayer(){
       return Bukkit.getPlayer(uuid);
    }


    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public GameMode getGameMode() {return gameMode;}

    public void setFillerGlass(Boolean showID){
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null){
                if(showID)
                    inventory.setItem(i, makeItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.GOLD + (String.valueOf(i))));
                 else
                inventory.setItem(i, FILLER_GLASS);
            }
        }
    }

    protected ItemStack makeItem(Material material, String displayName) {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(displayName);
        item.setItemMeta(itemMeta);

        return item;
    }

    protected ItemStack getItemStackBanner(String displayname, Material banner, ArrayList<DyeColor> dyeColors, ArrayList<PatternType> patternTypes, ChatColor color){
        ItemStack is = new ItemStack(banner);
        BannerMeta bannerMeta = (BannerMeta) is.getItemMeta();
        if(dyeColors.size() == patternTypes.size()) {
            for (int i = 0; i != dyeColors.size(); i++)
                bannerMeta.addPattern(new Pattern(dyeColors.get(i), patternTypes.get(i)));
            bannerMeta.setDisplayName(color + displayname);
            bannerMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            is.setItemMeta(bannerMeta);
        }
        return is;
    }

    public ItemStack CommingSoon(){
        ItemStack itemStack = new ItemStack(Material.RED_TERRACOTTA, 1);
        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(ChatColor.RED + "" +ChatColor.BOLD +"Comming Soon..");
        itemStack.setItemMeta(im);
        return itemStack;
    }

    public void setGameMode(GameMode gameMode){
        this.gameMode = gameMode;
    }

}

