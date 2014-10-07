package com.duckblade.eugine.server.chars;

import com.duckblade.eugine.server.Squishy;

public class SquishyDrunk extends Squishy {
    public static SquishyDrunk drunk = new SquishyDrunk();
    public SquishyDrunk() {
        super("drunk", 20, 1, 125, 4, 5, 10);
    }

    public void usePrimaryAbility() {

    }

    public void useSecondaryAbility() {

    }

    public boolean isPrimaryActive() {
        return false;
    }

    public boolean isSecondaryActive() {
        return false;
    }
}
