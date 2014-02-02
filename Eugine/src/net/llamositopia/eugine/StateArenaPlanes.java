package net.llamositopia.eugine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class StateArenaPlanes extends StateArena {

    public int getArenaID() {
        return 0;
    }

    public Image getBackground() throws SlickException {
        return new Image("res/img/background.png");
    }

    protected ArrayList<Floor> getFloors() {
        ArrayList<Floor> floors = new ArrayList<Floor>();
        floors.add(new Floor(128, 584));
        floors.add(new Floor(136, 584));
        floors.add(new Floor(144, 584));
        return floors;
    }
}
