package com.duckblade.eugine.server.chars;

import com.duckblade.eugine.server.Squishy;

public class SquishyMage extends Squishy {
    public static SquishyMage mage = new SquishyMage();
    private SquishyMage() {
        super("mage", 10, 20, 60, 4, 0, 10);
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
