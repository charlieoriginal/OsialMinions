package org.osial.osialminions.minions;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.osial.osialminions.minions.animation.MinionAnimation;
import org.osial.osialminions.minions.tasks.MinionAnimationTask;

import java.util.UUID;

public interface Minion {

    UUID getOwner();
    void setOwner(UUID uuid);
    void spawn(Location location);
    ArmorStand getArmorStand();
    ItemStack getPlaceItem();
    MinionAnimationTask getAnimationTask();
    void setAnimation(MinionAnimation animation);
    void increaseMaterialCount();
    long getMaterialCount();

}
