package org.osial.osialminions.minions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.osial.osialminions.OsialMinions;
import org.osial.osialminions.minions.actual.MiningMinion;
import org.osial.osialminions.minions.animation.actual.MovePickaxeAnimation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MinionHandler implements Listener {

    private OsialMinions plugin;

    private HashMap<UUID, List<Minion>> minions;

    public MinionHandler(OsialMinions plugin) {
        this.plugin = plugin;
        this.minions = new HashMap<>();

        Bukkit.getScheduler().runTaskTimer(plugin, this::minionDebug, 0L, 20L);
    }

    @EventHandler
    public void onJoinTest(PlayerJoinEvent e) {
        UUID owner = e.getPlayer().getUniqueId();
        ItemStack placeItem = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        Minion minion = new MiningMinion(owner, placeItem, Material.COBBLESTONE);
        minion.spawn(e.getPlayer().getLocation());

        List<Minion> minionList = minions.get(owner);
        if (minionList == null) {
            minionList = new ArrayList<>();
        }

        minionList.add(minion);
        minions.put(owner, minionList);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String messageRaw = e.getMessage();
        if (messageRaw.contains("do_anim")) {
            if (e.getPlayer().hasPermission("osialminions.admin.testanims")) {
                UUID owner = e.getPlayer().getUniqueId();
                List<Minion> minionList = minions.get(owner);
                if (minionList != null) {
                    for (Minion minion : minionList) {
                        minion.setAnimation(new MovePickaxeAnimation());
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onArmorStandInteract(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof ArmorStand) {
            for (UUID uuid : minions.keySet()) {
                for (Minion minion : minions.get(uuid)) {
                    if (minion.getArmorStand().equals(e.getRightClicked())) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityDamageEvent(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof ArmorStand) {
            for (UUID uuid : minions.keySet()) {
                for (Minion minion : minions.get(uuid)) {
                    if (minion.getArmorStand().equals(e.getEntity())) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    public void minionDebug() {
        for (UUID uuid : minions.keySet()) {
            System.out.println("========" + uuid + "========");
            List<Minion> minionList = minions.get(uuid);
            for (Minion minion : minionList) {
                System.out.println("Minion Materials Obtained: " + minion.getMaterialCount());
            }
        }
    }

}
