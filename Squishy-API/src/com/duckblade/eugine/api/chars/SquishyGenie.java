package com.duckblade.eugine.api.chars;

import com.duckblade.eugine.api.Squishy;

public class SquishyGenie extends Squishy {
    public static SquishyGenie genie = new SquishyGenie();
    public SquishyGenie() {
        super("genie", 10, 5, 110, 4, 5, 10);
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
