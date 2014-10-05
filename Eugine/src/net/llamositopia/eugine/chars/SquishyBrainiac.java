package net.llamositopia.eugine.chars;

import net.llamositopia.eugine.Squishy;

public class SquishyBrainiac extends Squishy {
    public static SquishyBrainiac brainiac = new SquishyBrainiac();
    public SquishyBrainiac() {
        super("brainiac", 20, 20, 45, 4, 0, 0);
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
