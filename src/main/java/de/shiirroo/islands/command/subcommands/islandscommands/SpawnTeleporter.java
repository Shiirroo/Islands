package de.shiirroo.islands.command.subcommands.islandscommands;

import de.shiirroo.islands.IslandsPlugin;
import de.shiirroo.islands.command.CommandBuilder;
import de.shiirroo.islands.command.SubCommand;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class SpawnTeleporter extends SubCommand {
    @Override
    public String getName() {
        return "SpawnTeleporter";
    }

    @Override
    public String getDescription() {
        return "Spawn Teleporter";
    }

    @Override
    public String getSyntax() {
        return "/"+ IslandsPlugin.getPlugin().getName() + " SpawnTeleporter";
    }
    @Override
    public Boolean getNeedOp() {
        return true;
    }

    @Override
    public CommandBuilder getSubCommandsArgs(String[] arg, Player player) {
        return null;
    }

    @Override
    public void perform(Player player, String[] args) {
        Villager villager = (Villager)  player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);
        villager.setAdult();
        villager.setAI(false);
        villager.setAgeLock(true);
        villager.setCanPickupItems(false);
        villager.setInvulnerable(true);
        villager.setCollidable(false);
        villager.setCustomName(ChatColor.GOLD + "Teleport to "+ IslandsPlugin.getPlugin().getName() + " World");
        villager.setCustomNameVisible(true);
        villager.setSilent(true);
    }
}
