package de.shiirroo.islands.event.block;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.blocks.DropBlockList;
import de.shiirroo.islands.gamedata.game.chunk.GameChunk;
import de.shiirroo.islands.gamedata.game.chunk.GameStructure;
import de.shiirroo.islands.gamedata.game.blocks.BlockDataCreator;
import de.shiirroo.islands.gamedata.game.items.GameItems;
import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import de.shiirroo.islands.utilis.Utilis;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import javax.tools.Tool;
import java.util.Optional;
import java.util.UUID;

public class onBlockBreak implements Listener{

    @EventHandler(priority = EventPriority.HIGH)
    private void BlockBreak(BlockBreakEvent event) {

        event.setExpToDrop(0);
        event.setDropItems(false);
        if(!event.getPlayer().getGameMode().equals(GameMode.CREATIVE) && event.getBlock().getY() == 50 && event.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null && !event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.APPLE)){
            event.setCancelled(true);
            return;
        }
        GameData gameData = IslandsPlugin.getGameData(event.getPlayer().getUniqueId());
        if(gameData != null) {
            Chunk blockChunk = event.getBlock().getChunk();
            World world = blockChunk.getWorld();
            Optional<BlockDataCreator> optionalBlockDataCreator = new DropBlockList().getDropBlockList().stream().filter(blockDataCreator -> blockDataCreator.getMaterial().equals(event.getBlock().getType())).findFirst();
            if(optionalBlockDataCreator.isPresent()){
                checkItemDrop(optionalBlockDataCreator.get(), world, gameData.getGamePlayers().getGameItems(), event);
            } else {
                Optional<GameChunk> optionalGameChunk = gameData.getGameArea().getGameChunks().stream().filter(gameChunk -> gameChunk.sameChunk(blockChunk.getX(), blockChunk.getZ())).findFirst();
                if (optionalGameChunk.isPresent()) {
                        GameChunk gameChunk = optionalGameChunk.get();
                        optionalBlockDataCreator = gameChunk.getChunkCreator().getChunkMaterialList().stream().filter(blockDataCreator -> blockDataCreator.getMaterial().equals(event.getBlock().getType())).findFirst();
                        optionalBlockDataCreator.ifPresent(blockDataCreator -> checkItemDrop(blockDataCreator, world, gameData.getGamePlayers().getGameItems(), event));
                            GameStructure removedStructure = gameChunk.removeStructure(event.getBlock().getX() & 0x000F, event.getBlock().getY(), event.getBlock().getZ() & 0x000F, event.getBlock().getType());
                            if (removedStructure != null) {
                                gameData.getGameStatus().setMoney(gameData.getGameStatus().getMoney() + (2* (Math.max(gameData.getGameStatus().getBonusMoney(), 1L)))); // TEST HALBER

                                if(event.getPlayer().getSaturation() > 2f)  event.getPlayer().setSaturation(event.getPlayer().getSaturation() - 2);
                                else if(event.getPlayer().getFoodLevel() > 2) event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() - 2);

                                int xp = removedStructure.getXp() * (Math.max(gameData.getGameStatus().getBonusXP(), 1));
                                System.out.println(xp);
                                gameData.getGameStatus().addXp(xp);
                                for (UUID uuid : gameData.getGamePlayers().getPlayers()) {
                                    Player player = Bukkit.getPlayer(uuid);
                                    if (player != null && player.isOnline()) {
                                        player.giveExp(xp);
                                    }
                                }
                            }
                        }
                    }
                }
            }



    private void checkItemDrop(BlockDataCreator blockDataCreator, World world, GameItems gameItems, BlockBreakEvent event) {
            Optional<ToolCreator> optionalItemCreator = gameItems.getItemCreators().stream().filter(itemCreator -> itemCreator.getItemStack().equals(event.getPlayer().getActiveItem())).findFirst();
            ToolCreator item = null;
            if (optionalItemCreator.isPresent()) item = optionalItemCreator.get();
            if(checkLiliPad(world, event)) {
                if (blockDataCreator.getDropItems(item) == null)
                    world.dropItemNaturally(event.getBlock().getLocation(), new ItemStack(event.getBlock().getType()));
                else for (ItemStack itemStack : blockDataCreator.getDropItems(item))
                    world.dropItemNaturally(event.getBlock().getLocation(), itemStack);
            }
    }


    public boolean checkLiliPad(World world, BlockBreakEvent event){
        if (event.getBlock().getType().equals(Material.LILY_PAD)) {
            if (world.getPlayers().stream().noneMatch(player -> player.getLocation().getBlockX() == event.getBlock().getX()
                    && player.getLocation().getBlockZ() == event.getBlock().getZ())) {
                for (int y = 52; y < 80; y++) {
                    event.getBlock().getChunk().getBlock(event.getBlock().getX() & 0x000F, y, event.getBlock().getZ() & 0x000F).setType(Material.BARRIER);
                }
                return true;
            } else {
                event.setCancelled(true);
                return false;
            }
        }
        return true;
    }

}
