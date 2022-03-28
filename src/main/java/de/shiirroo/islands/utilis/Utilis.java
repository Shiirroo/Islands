package de.shiirroo.islands.utilis;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.items.ItemTyp;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

public class Utilis {

    public static int generateRandomInt(int upperRange) {
        Random random = new Random();
        return random.nextInt(upperRange);
    }

    public static boolean isNewVersionHead(){
        return Arrays.stream(Material.values()).map(Material::name).toList().contains("PLAYER_HEAD");
    }


    public static ItemStack getPlayHead(){
        Material type = Material.matchMaterial(isNewVersionHead() ? "PLAYER_HEAD": "SKULL_ITEM");
        assert type != null;
        ItemStack playHead = new ItemStack(type, 1);
        if(!isNewVersionHead())
            playHead.setDurability((short) 3);
        return playHead;
    }

    public static String numberToFormat(long i) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);
        return decimalFormat.format(i);
    }

    public static boolean isInstanceOf(Item item, ItemTyp itemTyp){
        switch (itemTyp){
            case ITEM -> {return item instanceof ItemCreator;}
            case BLOCK -> {return item instanceof BlockCreator;}
            case TOOL -> {return item instanceof ToolCreator;}
            case WEAPON, ARMOR -> {return false;}
            case PLACEDHOLDER -> {return item.itemRecipe() != null;}
            default -> {return true;}
        }
    }


    public static boolean isNumeric(String strNum) {
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public static void deleteRecursively(File directory, boolean newVersion) {
        if(newVersion) {
            if (directory.exists()) {
                try {
                    FileUtils.deleteDirectory(directory);
                } catch (IOException e) {
                    Bukkit.getLogger().info(IslandsPlugin.getprefix() + ChatColor.RED + "Something went wrong while deleting files.");
                }
            }
        } else if (directory.exists()) {
            for (File file: Objects.requireNonNull(directory.listFiles())) {
                if(file.isDirectory()){
                    deleteRecursively(file, false);
                } else {
                    file.delete();
                }
            }
        }
    }

    public static ItemStack makeSkull(String base64EncodedString) {
        final ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        assert meta != null;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", base64EncodedString));
        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(meta);
        return skull;
    }
}
