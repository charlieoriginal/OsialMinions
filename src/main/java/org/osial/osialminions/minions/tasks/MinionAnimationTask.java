package org.osial.osialminions.minions.tasks;

import org.osial.osialminions.minions.Minion;
import org.osial.osialminions.minions.animation.MinionAnimation;

public class MinionAnimationTask implements Runnable {

    private Minion minion;
    private MinionAnimation animation;

    public MinionAnimationTask(Minion minion) {
        this.minion = minion;
    }

    public void setMinionAnim(MinionAnimation animation) {
        this.animation = animation;
    }

    @Override
    public void run() {
        if (animation != null) {
            if (animation.isComplete()) {
                animation = null;
            }

            if (animation != null) {
                animation.nextStage(minion);
            }
        }
    }

}
