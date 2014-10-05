package net.llamositopia.eugine.mapcreator;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

    public Game(){
        super("Eugine Map Creator");
    }

    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(new StateMapCreator());
        enterState(0);
    }
}