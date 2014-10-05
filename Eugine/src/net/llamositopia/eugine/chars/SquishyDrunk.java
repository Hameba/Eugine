package net.llamositopia.eugine.chars;

import net.llamositopia.eugine.Squishy;

public class SquishyDrunk extends Squishy {
    public SquishyDrunk() {
        super("drunk", 20, 1, 175, 5, 5, 0);
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
