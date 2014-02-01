package net.llamositopia.eugine;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateMenu extends BasicGameState {
    public int getID() {
        return 0;
    }

    Image bg;
    Image play;
    Image options;
    Image quit;

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        bg = new Image("res/img/menu/background.png");
        play = new Image("res/img/menu/play.png");
        options = new Image("res/img/menu/options.png");
        quit = new Image("res/img/menu/quit.png");
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        if (gc.getInput().isKeyPressed(Input.KEY_F11)){
            gc.setFullscreen(!gc.isFullscreen());
        }
        if (gc.getInput().getMouseX()>gc.getWidth()/2-play.getWidth()/2 && gc.getInput().getMouseX()<gc.getWidth()/2+play.getWidth()/2 && gc.getInput().getMouseY()>gc.getHeight()/2-play.getHeight()/2 && gc.getInput().getMouseY()>gc.getHeight()/2+play.getHeight()/2){
            sbg.enterState(1);
        }
        if (gc.getInput().getMouseX()>gc.getWidth()/2-options.getWidth()/2 && gc.getInput().getMouseX()<gc.getWidth()/2+options.getWidth()/2 && gc.getInput().getMouseY()>gc.getHeight()/2-options.getHeight()/2 && gc.getInput().getMouseY()>gc.getHeight()/2+options.getHeight()/2){
            sbg.enterState(2);
        }
        if (gc.getInput().getMouseX()>gc.getWidth()/2-quit.getWidth()/2 && gc.getInput().getMouseX()<gc.getWidth()/2+quit.getWidth()/2 && gc.getInput().getMouseY()>gc.getHeight()/2-quit.getHeight()/2 && gc.getInput().getMouseY()>gc.getHeight()/2+quit.getHeight()/2){
            gc.exit();
        }
    }
}
