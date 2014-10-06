package com.duckblade.eugine.api;

import org.newdawn.slick.state.BasicGameState;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class StateArena extends BasicGameState{

    private boolean initialized = false;

    public final int getID() {
        return getArenaID()+10;
    }

    protected abstract int getArenaID();

    public ArrayList<Squishy> squishies = new ArrayList<Squishy>();

    public static ArrayList<StateArena> arenas = new ArrayList<StateArena>();

    public void loadFloors(){
        try {
            Scanner reader = new Scanner(new File("res/arena/" + getArenaKey() + "/floors.dat"));
            while (reader.hasNextLine()){
                String floor = reader.nextLine();
                new Floor(Integer.parseInt(floor.split(";")[0]), Integer.parseInt(floor.split(";")[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract String getArenaKey();
}
