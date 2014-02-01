package net.llamositopia.eugine;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

    public static void main(String[] args) {
        Game g = new Game("Eugine");
        try {
            AppGameContainer gc = new AppGameContainer(g, 800, 600, false);
            gc.setTargetFrameRate(30);
            gc.setShowFPS(false);
            gc.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
