package net.llamositopia.eugine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class StateArenaPlanes extends StateArena {

    public int getArenaID() {
        return 0;
    }

    protected ArrayList<Floor> getFloors() {
        ArrayList<Floor> floors = new ArrayList<Floor>();
        floors.add(new Floor(32, 32));
        floors.add(new Floor(32, 40));
        floors.add(new Floor(32, 48));
        return floors;
    }
}
