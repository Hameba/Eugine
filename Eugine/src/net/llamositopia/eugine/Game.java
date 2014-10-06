package net.llamositopia.eugine;

import com.duckblade.eugine.api.arenas.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {
    public Game(String name) {
        super(name);
    }

    public void initStatesList(GameContainer gameContainer) throws SlickException {
        VH.sbg = this;
        addState(new StateMenu());
        addState(new StateConnect());
        addState(new StatePlayerChoose());
        addState(new StateArenaPlains());
        addState(new StateArenaFactory());
        addState(new StateArenaBattlezone());
        addState(new StateArenaLumberyard());
        addState(new StateArenaLisa());
        enterState(0);
    }
}
