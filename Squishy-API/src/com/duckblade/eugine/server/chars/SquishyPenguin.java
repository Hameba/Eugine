package com.duckblade.eugine.server.chars;

import com.duckblade.eugine.server.Squishy;

public class SquishyPenguin extends Squishy {
    public static SquishyPenguin penguin = new SquishyPenguin();
    public SquishyPenguin() {
        super("penguin", 5, 25, 55, 5, 0, 0);
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
