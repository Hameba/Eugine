package com.duckblade.eugine.api;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class StateArena extends BasicGameState{

    private boolean initialized = false;

    public final int getID() {
        return getArenaID()+10;
    }

    protected abstract int getArenaID();

    public ArrayList<Squishy> squishies = new ArrayList<Squishy>();

    private Image floorImage;

    protected Image bg;

    public static ArrayList<StateArena> arenas = new ArrayList<StateArena>();

    public Image getBackground() throws SlickException{
        Image i = new Image("res/img/" + getArenaKey() + "/background.png");
        Floor.clearFloors();
        for (int x = 0; x < i.getWidth(); x++) {
            for (int y = 0; y < i.getHeight(); y++) {
                if (x%8==0 && y%8==0){
                    if (i.getColor(x, y).equals(new Color(0xff00ff))){
                        new Floor(x, y);
                    }
                }
            }
        }
        return i;
    }

    public abstract String getArenaKey();
}
