package com.duckblade.eugine.server.arenas;

import com.duckblade.eugine.server.StateArena;

public class StateArenaFactory extends StateArena {
    protected int getArenaID() {
        return 1;
    }

    public String getArenaKey() {
        return "factory";
    }
}
