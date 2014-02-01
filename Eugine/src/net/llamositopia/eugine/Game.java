package net.llamositopia.eugine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {
    public Game(String name) {
        super(name);
    }

    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(new StateMenu());
        enterState(0);
    }
}
