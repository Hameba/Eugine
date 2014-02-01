package net.llamositopia.eugine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public abstract class StateArena extends BasicGameState{

    public final int getID() {
        return getArenaID()+10;
    }

    protected abstract int getArenaID();

    protected ArrayList<Character> characters;

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        for (Character c : characters){
            graphics.drawImage(c.getProjectile(), c.getPr_X(), c.getPr_Y());
            graphics.drawImage(c.getImage(), c.getX(), c.getY());
        }
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (NetworkManager.isServer()){
            runServerStuff();
            return;
        }

    }

    private void runServerStuff() {
        String data = "";
        for (Character c : characters){
            data += data.equals("") ? "" + c.getKey() : ";" + c.getKey();
            data += ":" + c.getX() + ":";
            data += c.getY() + ":";
            data += c.getPr_X() + ":";
            data += c.getPr_Y() + ":";
            data += c.getImageInt();
        }
        for (ObjectOutputStream oos : NetworkManager.outs){
            try {
                oos.writeObject(data);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
