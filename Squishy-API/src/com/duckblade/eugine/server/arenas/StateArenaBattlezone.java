package com.duckblade.eugine.server.arenas;

import com.duckblade.eugine.server.StateArena;

public class StateArenaBattlezone extends StateArena {
    protected int getArenaID() {
        return 2;
    }

    public String getArenaKey() {
        return "battlezone";
    }
}
