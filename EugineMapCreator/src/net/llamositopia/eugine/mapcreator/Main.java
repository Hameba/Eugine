package net.llamositopia.eugine.mapcreator;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

    public static void main(String[] args) {
        Game g = new Game();
        try {
            AppGameContainer appgc = new AppGameContainer(g, 800, 600, false);
            appgc.setTargetFrameRate(60);
            appgc.setShowFPS(false);
            appgc.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}