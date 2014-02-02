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
        floors.add(new Floor(128, 128));
        floors.add(new Floor(136, 128));
        floors.add(new Floor(144, 128));
        return floors;
    }
}
