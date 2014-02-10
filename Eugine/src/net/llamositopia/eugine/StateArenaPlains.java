package net.llamositopia.eugine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class StateArenaPlains extends StateArena {

    public int getArenaID() {
        return 0;
    }

    protected String getArenaKey() {
        return "plains";
    }

}