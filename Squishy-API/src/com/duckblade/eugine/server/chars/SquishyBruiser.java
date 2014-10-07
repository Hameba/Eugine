package com.duckblade.eugine.server.chars;

import com.duckblade.eugine.server.Squishy;

public class SquishyBruiser extends Squishy {
    public static SquishyBruiser bruiser = new SquishyBruiser();
    public SquishyBruiser() {
        super("bruiser", 20, 10, 90, 5, 10, 5);
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
