package org.osial.osialminions.minions.actual;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.osial.osialminions.OsialMinions;
import org.osial.osialminions.minions.Minion;
import org.osial.osialminions.minions.animation.MinionAnimation;
import org.osial.osialminions.minions.tasks.MinerMinionTask;
import org.osial.osialminions.minions.tasks.MinionAnimationTask;
import org.osial.osialminions.utils.ItemBuilder;

import java.util.UUID;

public class MiningMinion implements Minion {

    private UUID owner;
    private UUID minion;
    private BukkitTask animationBukkitTask;
    private MinionAnimationTask animationTask;
    private BukkitTask miningBukkitTask;
    private MinerMinionTask miningTask;
    private ArmorStand armorStand;
    private Location location;
    private long materialCount = 0;
    private ItemStack placeItem;
    private Material material;
    private String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjljMzhmZTRmYzk4YTI0ODA3OWNkMDRjNjViNmJmZjliNDUwMTdmMTY0NjBkYWIzYzM0YzE3YmZjM2VlMWQyZiJ9fX0=";

    public MiningMinion(UUID owner, ItemStack placeItem, Material material) {
        this.owner = owner;
        this.placeItem = placeItem;
        this.material = material;
        this.minion = UUID.randomUUID();
    }

    @Override
    public UUID getOwner() {
        return owner;
    }

    @Override
    public void setOwner(UUID uuid) {
        this.owner = uuid;
    }

    @Override
    public void spawn(Location location) {
        this.location = location;
        this.armorStand = location.getWorld().spawn(location, ArmorStand.class);
        this.armorStand.setGravity(false);
        this.armorStand.setVisible(true);
        this.armorStand.setBasePlate(false);
        this.armorStand.setSmall(true);
        this.armorStand.setCustomName("Mining Minion");
        this.armorStand.setArms(true);

        ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE)
                .setColor(Color.GRAY)
                .build();
        this.armorStand.getEquipment().setChestplate(chestplate);

        ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS)
                .setColor(Color.GRAY)
                .build();
        this.armorStand.getEquipment().setLeggings(leggings);

        ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS)
                .setColor(Color.GRAY)
                .build();
        this.armorStand.getEquipment().setBoots(boots);

        ItemStack head = new ItemBuilder("head-" + base64)
                .build();
        this.armorStand.getEquipment().setHelmet(head);

        ItemStack pickaxe = new ItemBuilder(Material.WOODEN_PICKAXE)
                .build();
        this.armorStand.getEquipment().setItemInMainHand(pickaxe);

        if (animationBukkitTask == null) {
            this.animationTask = new MinionAnimationTask(this);
            this.animationBukkitTask = Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(OsialMinions.class), animationTask, 0L, 5L);
            this.miningTask = new MinerMinionTask(armorStand.getLocation(), armorStand, 4, material, this);
            this.miningBukkitTask = Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(OsialMinions.class), miningTask, 0L, 60L);
        }
    }

    @Override
    public void increaseMaterialCount() {
        materialCount++;
    }

    @Override
    public long getMaterialCount() {
        return materialCount;
    }

    @Override
    public ArmorStand getArmorStand() {
        return armorStand;
    }

    @Override
    public ItemStack getPlaceItem() {
        return placeItem;
    }

    @Override
    public MinionAnimationTask getAnimationTask() {
        return animationTask;
    }

    @Override
    public void setAnimation(MinionAnimation animation) {
        animationTask.setMinionAnim(animation);
    }
}
