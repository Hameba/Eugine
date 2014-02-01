package net.llamositopia.eugine;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

    public static void main(String[] args) {
        Game g = new Game("Eugine");
        try {
            AppGameContainer gc = new AppGameContainer(g);
            gc.setTargetFrameRate(60);
            gc.setShowFPS(false);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
