package de.shiirroo.islands.gamedata.game.items.gameitems;

import de.shiirroo.islands.command.CommandBuilder;
import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.BlockCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.common.*;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.epic.CosmicShard;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.epic.CryingObsidian;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.epic.LavaCauldron;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.epic.Obsidian;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.rare.*;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.ItemCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.common.Broken_Iron;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.epic.Gold_Ingot;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.uncommon.*;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.common.Melon_Slice;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.common.Wheat;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.epic.MoltenLava;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.legendary.CosmicIngot;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.legendary.DragonFragment;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.legendary.RawDragonOre;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.mythic.DragonEgg;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.mythic.DragonHead;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.mythic.DragonIngot;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.mythic.DragonSoul;
import de.shiirroo.islands.gamedata.game.items.gameitems.items.rare.Iron_Ingot;
import de.shiirroo.islands.gamedata.game.items.gameitems.blocks.uncommon.*;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.axe.*;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.pickaxe.*;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.pickaxe.Obliterator;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.sword.*;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameItemList {

    private final List<Item> items = new ArrayList<>();
    private final HashMap<ItemStack, Item> itemsMap = new HashMap<>();

    private final List<Item> craftItems = new ArrayList<>();
    private final HashMap<ItemStack, Item> craftItemsMap = new HashMap<>();

    private final List<Item> toolItems = new ArrayList<>();
    private final HashMap<ItemStack, Item> toolItemsMap = new HashMap<>();

    private final List<Item> blockItems = new ArrayList<>();
    private final HashMap<ItemStack, Item> blockItemsMap = new HashMap<>();

    private final List<Item> itemItems = new ArrayList<>();
    private final HashMap<ItemStack, Item> itemItemsMap = new HashMap<>();
    private final CommandBuilder itemsCommand = new CommandBuilder("Item");
    private final CommandBuilder blocks = new CommandBuilder("Block");
    private final CommandBuilder tools = new CommandBuilder("Tool");


    public GameItemList(){
        String name = Coal_Ore.class.getPackage().getName();



        //------------Items-----------
        this.items.add(new Coal());
        this.items.add(new Melon_Slice());
        this.items.add(new Wheat());
        this.items.add(new Iron_Ingot());
        this.items.add(new Copper());
        this.items.add(new BrokenGold());
        this.items.add(new Broken_Iron());
        this.items.add(new Bread());
        this.items.add(new Apple());
        this.items.add(new Charcoal());

        //------------Epic Item-----------
        this.items.add(new MoltenLava());
        this.items.add(new Gold_Ore());
        this.items.add(new Gold_Ingot());

        //------------Legendary Item-----------
        this.items.add(new DragonFragment());
        this.items.add(new RawDragonOre());
        //------------Mythic Item-----------
        this.items.add(new DragonEgg());
        this.items.add(new DragonIngot());
        this.items.add(new DragonSoul());
        this.items.add(new DragonHead());


        //------------Block-----------
        this.items.add(new Warped_Stem());
        this.items.add(new Warped_Hyphae());
        this.items.add(new Stone());
        this.items.add(new Sugar_Cane());
        this.items.add(new Cactus());
        this.items.add(new Iron_Ore());
        this.items.add(new Lily_Pad());
        this.items.add(new Oak_Leaves());
        this.items.add(new Oak_Log());
        this.items.add(new Melon_Block());
        this.items.add(new Hay_Block());
        this.items.add(new Coal_Ore());
        this.items.add(new Cobblestone());
        this.items.add(new Copper_Ore());
        this.items.add(new Warped_Nylium());
        this.items.add(new Sand());
        this.items.add(new Dirt());
        this.items.add(new Gravel());
        this.items.add(new Granite());
        this.items.add(new DeepslateCoal_Ore());
        this.items.add(new Deepslate());
        this.items.add(new DeepslateIron());
        this.items.add(new Raw_Gold_Block());
        this.items.add(new Raw_Iron_Block());
        this.items.add(new Netherrack());
        this.items.add(new Netherrack_Gold());
        this.items.add(new Netherbricks());
        this.items.add(new Grass());
        this.items.add(new Skull());
        this.items.add(new Obsidian());
        this.items.add(new CryingObsidian());

        this.items.add(new SmallCosmicShard());
        this.items.add(new MediumCosmicShard());
        this.items.add(new CosmicShard());
        this.items.add(new CosmicIngot());

        //------------Tools-----------
        //------------Axe-----------
        this.items.add(new StartAxe());
        this.items.add(new AxeOnAStick());
        this.items.add(new StoneDicer());
        this.items.add(new SkullAxe());
        this.items.add(new GoldChopper());
        this.items.add(new CosmicAxe());
        this.items.add(new ChopperOfDragonsouls());

        //------------Sword-----------
        this.items.add(new StartSword());
        this.items.add(new SwordOnAStick());
        this.items.add(new StoneCutter());
        this.items.add(new SkullSword());
        this.items.add(new GoldSlayer());
        this.items.add(new CosmicSword());
        this.items.add(new Dragonslayer());
        //------------PickAxe-----------
        this.items.add(new StartPickAxe());
        this.items.add(new PickOnAStick());
        this.items.add(new StoneSlicer());
        this.items.add(new SkullPickaxe());
        this.items.add(new GoldDigger());
        this.items.add(new CosmicPickaxe());
        this.items.add(new Obliterator());


        this.itemsMap.putAll(items.stream().collect(Collectors.toMap(Item::getItemStack, Function.identity())));

        this.craftItems.addAll(items.stream().filter(item -> item.itemRecipe() != null).toList());
        this.craftItemsMap.putAll(craftItems.stream().collect(Collectors.toMap(Item::getItemStack, Function.identity())));

        this.toolItems.addAll(items.stream().filter(item -> item instanceof ToolCreator).toList());
        this.toolItemsMap.putAll(toolItems.stream().collect(Collectors.toMap(Item::getItemStack, Function.identity())));

        this.blockItems.addAll(items.stream().filter(item -> item instanceof BlockCreator).toList());
        this.blockItemsMap.putAll(blockItems.stream().collect(Collectors.toMap(Item::getItemStack, Function.identity())));

        this.itemItems.addAll(items.stream().filter(item -> item instanceof ItemCreator).toList());
        this.itemItemsMap.putAll(itemItems.stream().collect(Collectors.toMap(Item::getItemStack, Function.identity())));

        items.forEach(item -> {
            if(item instanceof BlockCreator blockCreator){
                blocks.addSubCommandBuilder(new CommandBuilder(ChatColor.stripColor(blockCreator.getItemStack().getItemMeta().getDisplayName().replace(" ", "_"))).setCustomInput());
            }
            else if(item instanceof ToolCreator toolCreator){
                tools.addSubCommandBuilder(new CommandBuilder(ChatColor.stripColor(toolCreator.getItemStack().getItemMeta().getDisplayName().replace(" ", "_"))).setCustomInput());
            }
            else if(item instanceof ItemCreator itemCreator){
                itemsCommand.addSubCommandBuilder(new CommandBuilder(ChatColor.stripColor(itemCreator.getItemStack().getItemMeta().getDisplayName().replace(" ", "_"))).setCustomInput());
            }});
    }


    public CommandBuilder getBlocks() {
        return blocks;
    }

    public CommandBuilder getItemsCommand() {
        return itemsCommand;
    }

    public CommandBuilder getTools() {
        return tools;
    }

    public List<Item> getCraftItems() {
        return craftItems;
    }

    public HashMap<ItemStack, Item> getCraftItemsMap() {
        return craftItemsMap;
    }

    public HashMap<ItemStack, Item> getItemsMap() {
        return itemsMap;
    }

    public List<Item> getItems() {
        return items;
    }


    public List<Item> getItemItems() {
        return itemItems;
    }


    public List<Item> getBlockItems() {
        return blockItems;
    }


    public HashMap<ItemStack, Item> getBlockItemsMap() {
        return blockItemsMap;
    }


    public List<Item> getToolItems() {
        return toolItems;
    }

    public HashMap<ItemStack, Item> getItemItemsMap() {
        return itemItemsMap;
    }


    public HashMap<ItemStack, Item> getToolItemsMap() {
        return toolItemsMap;
    }
}
