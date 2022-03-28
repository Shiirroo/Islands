package de.shiirroo.islands;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftZombie;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_18_R1.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

public class NPCUtil {

    public static void spawnNPC(Player player) {

        ServerPlayer craftPlayer = ((CraftPlayer) player).getHandle();

        // NPC textures
        Property textures = (Property) craftPlayer.getGameProfile().getProperties().get("textures").toArray()[0];
        UUID uuid = UUID.randomUUID();
        GameProfile gameProfile = new GameProfile(uuid, String.valueOf(uuid.getMostSignificantBits()));
        gameProfile.getProperties().put("textures", new Property("textures", textures.getValue(), textures.getSignature()));

        ServerPlayer corpse = new ServerPlayer(
                ((CraftServer)Bukkit.getServer()).getServer(), ((CraftPlayer) player).getHandle().getLevel(), gameProfile);

        corpse.setPos(player.getLocation().getX(), player.getLocation().getY(),player.getLocation().getZ());

        corpse.setPose(Pose.SWIMMING);


        for(Player on: Bukkit.getOnlinePlayers()){
            ServerGamePacketListenerImpl connection = ((CraftPlayer)on).getHandle().connection;

            connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER,corpse));
            connection.send(new ClientboundAddPlayerPacket(corpse));
            connection.send(new ClientboundSetEntityDataPacket(corpse.getId(), corpse.getEntityData(), false));

            connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, corpse));


        }

    }
}