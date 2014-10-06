package com.duckblade.eugine.api.chars;

import net.llamositopia.eugine.Squishy;

public class SquishyNinja extends Squishy {
    public static SquishyNinja ninja = new SquishyNinja();
    private SquishyNinja(){
        super("ninja", 15, 10, 95, 7, -5, 0);
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
