package com.duckblade.eugine.api.chars;

import com.duckblade.eugine.api.Squishy;

public class SquishyArcher extends Squishy {
    public static SquishyArcher archer = new SquishyArcher();
    public SquishyArcher() {
        super("archer", 10, 15, 80, 6, 0, 0);
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
