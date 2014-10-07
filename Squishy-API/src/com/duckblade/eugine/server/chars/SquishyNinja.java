package com.duckblade.eugine.server.chars;

import com.duckblade.eugine.server.Squishy;

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
