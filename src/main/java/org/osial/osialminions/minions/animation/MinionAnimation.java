package org.osial.osialminions.minions.animation;

import org.osial.osialminions.minions.Minion;

public interface MinionAnimation {

    void nextStage(Minion minion);
    boolean isComplete();

}
