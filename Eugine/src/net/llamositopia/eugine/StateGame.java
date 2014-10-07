package net.llamositopia.eugine;

import com.duckblade.eugine.server.Floor;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends BasicGameState {
    public int getID() {
        return 3;
    }

    public Image bg, floorImage;

    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        bg = new Image("res/arena/" + VH.arena.getArenaKey() + "/bg.png");
        new Thread(new Runnable() {
            public void run() {

            }
        }).start();
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        floorImage = new Image("res/img/floor.png");
    }



    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(bg, 0, 0);
        for (Floor a : Floor.getFloors()){
            g.drawImage(floorImage, a.getX(), a.getY());
        }
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

    }
}
