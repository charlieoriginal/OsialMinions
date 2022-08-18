package org.osial.osialminions.minions.animation.actual;

import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.osial.osialminions.minions.Minion;
import org.osial.osialminions.minions.animation.MinionAnimation;

public class MovePickaxeAnimation implements MinionAnimation {

    private boolean isComplete;
    private int stage = 0;
    private double maxUp = -1D;
    private double maxDown = 0.1D;

    @Override
    public void nextStage(Minion minion) {
        ArmorStand armorStand = minion.getArmorStand();
        EulerAngle right = armorStand.getRightArmPose();
        double x = right.getX();

        if (stage == 0) {
            if (x > maxUp) {
                EulerAngle newRight = new EulerAngle(right.getX()-0.1, right.getY(), right.getZ());
                armorStand.setRightArmPose(newRight);
            } else {
                stage = 1;
            }
        } else if (stage == 1) {
            if (x < maxDown) {
                EulerAngle newRight = new EulerAngle(right.getX()+0.1, right.getY(), right.getZ());
                armorStand.setRightArmPose(newRight);
            } else {
                stage = 0;
                isComplete = true;
            }
        }
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

}
