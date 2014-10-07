package net.llamositopia.eugine;

import com.duckblade.eugine.api.Squishy;
import com.duckblade.eugine.api.StateArena;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public abstract class VH {

    public static GameContainer gc;
    public static StateBasedGame sbg;
    public static Squishy myChar;
    public static StateArena arena;
    public static ArrayList<Squishy> options = new ArrayList<Squishy>();
    public static String ip;

}
