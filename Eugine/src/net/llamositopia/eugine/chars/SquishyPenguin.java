package net.llamositopia.eugine.chars;

import net.llamositopia.eugine.Squishy;

public class SquishyPenguin extends Squishy {
    public static SquishyPenguin penguin = new SquishyPenguin();
    public SquishyPenguin() {
        super("penguin", 5, 25, 55);
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
