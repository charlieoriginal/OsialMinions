package org.osial.osialminions.minions.tasks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.osial.osialminions.minions.Minion;
import org.osial.osialminions.minions.animation.actual.MovePickaxeAnimation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinerMinionTask implements Runnable {

    private Location location;
    private ArmorStand armorStand;
    private int scanRadius;
    private Material validMaterial;
    private Minion minion;

    public MinerMinionTask(Location location, ArmorStand armorStand, int scanRadius, Material validMaterial, Minion minion) {
        this.location = location;
        this.armorStand = armorStand;
        this.scanRadius = scanRadius;
        this.validMaterial = validMaterial;
        this.minion = minion;
    }

    @Override
    public void run() {
        int startY = armorStand.getLocation().getBlockY()-1;
        int startX = armorStand.getLocation().getBlockX()-(scanRadius/2);
        int startZ = armorStand.getLocation().getBlockZ()-(scanRadius/2);
        int endX = armorStand.getLocation().getBlockX()+(scanRadius/2);
        int endZ = armorStand.getLocation().getBlockZ()+(scanRadius/2);

        List<Location> validMaterialBlocks = new ArrayList<>();
        List<Location> invalidMaterialBlocks = new ArrayList<>();

        for (int x = startX; x <= endX; x++) {
            for (int z = startZ; z <= endZ; z++) {
                Location loc = new Location(armorStand.getWorld(), x, startY, z);
                if (loc.getBlock().getType().equals(validMaterial)) {
                    validMaterialBlocks.add(loc);
                } else {
                    invalidMaterialBlocks.add(loc);
                }
            }
        }

        System.out.println("Valid: " + validMaterialBlocks.size());
        System.out.println("Invalid: " + invalidMaterialBlocks.size());

        if (invalidMaterialBlocks.size() > 0) {
            Location randomInvalidMaterialBlock = invalidMaterialBlocks.get(generateRandomNumber(invalidMaterialBlocks.size(), 0));
            randomInvalidMaterialBlock.getBlock().setType(validMaterial);
        } else {
            Location randomValidMaterialBlock = validMaterialBlocks.get(generateRandomNumber(validMaterialBlocks.size(), 0));
            minion.setAnimation(new MovePickaxeAnimation());
            minion.increaseMaterialCount();
            Block block = randomValidMaterialBlock.getBlock();
            randomValidMaterialBlock.getWorld().spawnParticle(Particle.BLOCK_CRACK, randomValidMaterialBlock.add(0.5, 0.5, 0.5), 20, .1, .1, .1, .5, randomValidMaterialBlock.getBlock().getType().createBlockData());
            block.setType(Material.AIR);
        }

    }

    private int generateRandomNumber(int upperBound, int lowerBound) {
        /*
        Generate a number between these two numbers. Can be positive or negative.
        Use a double then cast to an integer.
         */
        Random random = new Random();
        double randomNumber = random.nextDouble() * (upperBound - lowerBound) + lowerBound;
        int randomInt = (int) randomNumber;

        System.out.println("Random Int: " + randomInt);

        return randomInt;
    }
}
