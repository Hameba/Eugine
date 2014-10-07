package com.duckblade.eugine.api.arenas;

import com.duckblade.eugine.api.StateArena;

public class StateArenaFactory extends StateArena {
    protected int getArenaID() {
        return 1;
    }

    public String getArenaKey() {
        return "factory";
    }
}
