package net.llamositopia.eugine;

import com.duckblade.eugine.server.Squishy;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StatePlayerChoose extends BasicGameState {
    public int getID() {
        return 1;
    }

    Image bg;
    Image play;
    Image quit;

    private int selected = 0;

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        bg = new Image("res/img/menu/background.png");
        play = new Image("res/img/menu/play.png");
        quit = new Image("res/img/menu/quit.png");
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(bg, 0, 0);
        g.drawImage(play, gc.getWidth() / 2 - play.getWidth() / 2, gc.getHeight() / 2 + play.getHeight() / 2 + gc.getHeight() / 5);
        g.setColor(Color.white);
        for (int i = 0; i < Squishy.values().length; i++) {
            Squishy c  = Squishy.values()[i];
            g.drawImage(c.getImage(), gc.getWidth()/Squishy.values().length*2*(i/2), i%2==0 ? 268 : 332);
        }
        g.setLineWidth(5);
        g.drawRect(gc.getWidth()/Squishy.values().length*selected+25+(selected%2==1 ? -99 : 0), 268 + (selected%2==1 ? 64 : 0), 32, 32);
        g.drawImage(quit, gc.getWidth()/2-quit.getWidth()/2, gc.getHeight()/2+quit.getHeight()/2+gc.getHeight()/2);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        if (gc.getInput().isKeyPressed(Input.KEY_F11)){
            gc.setFullscreen(!gc.isFullscreen());
        }
        if (gc.getInput().isKeyPressed(Input.KEY_LEFT) && selected>1){
            selected-=2;
        }
        if (gc.getInput().isKeyPressed(Input.KEY_RIGHT) && selected<Squishy.values().length-2){
            selected+=2;
        }
        if (gc.getInput().isKeyPressed(Input.KEY_UP) && selected%2==1){
            selected--;
        }
        if (gc.getInput().isKeyPressed(Input.KEY_DOWN) && selected%2==0 && selected<Squishy.values().length-1){
            selected++;
        }
        if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
            if (gc.getInput().getMouseX()>gc.getWidth()/2-play.getWidth()/2 && gc.getInput().getMouseX()<gc.getWidth()/2+play.getWidth()/2 && gc.getInput().getMouseY()>gc.getHeight()/2-play.getHeight()/2+gc.getHeight()/5 && gc.getInput().getMouseY()>gc.getHeight()/2+play.getHeight()/2+gc.getHeight()/5){
                VH.myChar = Squishy.values()[selected];
                sbg.enterState(2);
            }
            if (gc.getInput().getMouseX()>gc.getWidth()/2-quit.getWidth()/2 && gc.getInput().getMouseX()<gc.getWidth()/2+quit.getWidth()/2 && gc.getInput().getMouseY()>gc.getHeight()/2-quit.getHeight()/2+gc.getHeight()/3 && gc.getInput().getMouseY()>gc.getHeight()/2+quit.getHeight()/2+gc.getHeight()/3){
                sbg.enterState(0);
            }
        }
    }
}
