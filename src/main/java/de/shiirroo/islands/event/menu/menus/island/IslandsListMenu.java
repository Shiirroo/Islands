package de.shiirroo.islands.event.menu.menus.island;

import de.shiirroo.islands.event.menu.*;
import de.shiirroo.islands.gamedata.game.blocks.BlockDataCreator;
import de.shiirroo.islands.gamedata.game.chunk.chunks.ChunkCreator;
import de.shiirroo.islands.gamedata.game.chunk.chunks.ChunkPresetList;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class IslandsListMenu extends Menu {
    public IslandsListMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    private int site = 0;

    @Override
    public String getMenuName() {
        return ChatColor.GREEN + "Islands Menu";
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
        } else if (Objects.equals(e.getCurrentItem(), getBackPage())) {
            if (site >= 1) {
                site--;
                setMenuItems();
            }
        } else if (Objects.equals(e.getCurrentItem(), getNextPage())) {
            if (site < chunkCreatorList.size() / 24) {
                site++;
                setMenuItems();
            }

        }
    }


    List<ChunkCreator> chunkCreatorList = new ChunkPresetList().getChunkPresetList();

    @Override
    public void setMenuItems() {
        inventory.clear();
        if(site > 0)  inventory.setItem(45,getBackPage());
        else inventory.setItem(45,null);

        int slot = 0;
        inventory.setItem(4, getSitePage());


        if(chunkCreatorList.size() > 0) {
            for (int i = 28 * site; i != 28 * (site + 1); i++) {
                if(i >= chunkCreatorList.size()) break;
                if (i != 28 * site && i % 7 == 0) slot += 2;
                ChunkCreator creator = chunkCreatorList.get(i);
                ItemStack itemStack = chunkCreatorList.get(i).chunkDisplayItem();
                ItemMeta itemMeta = itemStack.getItemMeta();
                List<String> lore = new ArrayList<>(List.of(""));
                if(creator.rarity() != null)    lore.add(ChatColor.GOLD + "Island Rarity: " +creator.rarity().getChatColor() + creator.rarity());

                lore.add(ChatColor.GOLD + "Spawn max Size: " +ChatColor.GREEN+ creator.ChunkPlaceSize());

                if(creator.getPlacedOnHashSet().size() >= 1) {
                    lore.addAll(List.of("", ChatColor.GOLD + "Spawn-place: "));
                    for (BlockCreator block : creator.getPlacedOnHashSet()) {
                        lore.addAll(List.of(block.getItemStack().getItemMeta().getDisplayName()));
                    }
                }
                if(creator.getChunkMaterialList().size() >= 1) {
                    lore.addAll(List.of("", ChatColor.GOLD + "Spawn-Blocks: "));
                    for (BlockDataCreator block : creator.getChunkMaterialList()) {
                        lore.addAll(List.of(block.getItemStack().getItemMeta().getDisplayName()));
                    }
                }
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(slot + 10, itemStack);
                slot++;
            }
        }

        if (chunkCreatorList.size() > (28 * (site +1))) inventory.setItem(53, getNextPage());
        else inventory.setItem(53,null);



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
        itemMeta.setDisplayName(ChatColor.GOLD + String.valueOf(site + 1 ) + ChatColor.GRAY +" | " + ChatColor.GOLD + (1 + chunkCreatorList.size()/24));
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(site+1);
        return itemStack;
    }
    public ItemStack getBackPage(){
        ItemStack playHead = Utilis.getPlayHead();
        SkullMeta im = (SkullMeta) playHead.getItemMeta();
        im.setDisplayName(ChatColor.GOLD + "Page: " + ChatColor.GREEN + (site + 1));
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
