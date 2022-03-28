package de.shiirroo.islands;

import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftZombie;
import net.minecraft.world.entity.monster.Zombie;

public class CraftCustomZombie extends CraftZombie {






    public CraftCustomZombi(CraftServer server, EntityZombie entity) {
        super(server, entity);

        this.setDisplayName("Our custom zombie displayname !");
        this.setDisplayNameVisible(true);
        this.setRemoveWhenFarAway(false);
    }

    @Override
    public getHandle() {
        return null;
    }

    public CraftCustomZombie(CraftServer server, Zombie entity) {
        super(server, entity);
    }
}