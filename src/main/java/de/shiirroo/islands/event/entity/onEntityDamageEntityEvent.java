package de.shiirroo.islands.event.entity;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.gamedata.GameData;
import de.shiirroo.islands.gamedata.game.items.Item;
import de.shiirroo.islands.gamedata.game.items.gameitems.tools.ToolCreator;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import javax.tools.Tool;
import java.util.Optional;

public class onEntityDamageEntityEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    private void EntityDamageEntityEvent(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player player){
            GameData gameData = IslandsPlugin.getGameData(player.getUniqueId());
            if(gameData != null){
                if(event.getEntity() instanceof LivingEntity livingEntity){
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    Optional<ToolCreator> optionalTool = gameData.getGamePlayers().getGameItems().getItemCreators().stream().filter(item -> item.getItemStack().equals(itemStack)).findFirst();
                    if(optionalTool.isPresent()){
                        ToolCreator tool = optionalTool.get();
                        event.setDamage(tool.getToolAttackDamage());
                    }
                }
            }
        }
    }
}
