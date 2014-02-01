package net.llamositopia.eugine;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;

public enum Character {

    MAGE("mage"),
    NINJA("ninja"),
    SLAYER("slayer")
    ;

    private Image rest, melee1, melee2, melee3, pr;

    private int x, y, pr_x, pr_y, meleeFrames;

    private boolean isFacingLeft = false;
    private final String key;

    private String IP = null;

    Character(String key){
        this.key = key;
        try {
            pr = new Image("res/img/char/pr_" + key + ".png");
            SpriteSheet sprites = new SpriteSheet("res/img/char/" + key + ".png", 82, 32);
            rest = sprites.getSprite(0, 0);
            melee1 = sprites.getSprite(1, 0);
            melee2 = sprites.getSprite(2, 0);
            melee3 = sprites.getSprite(3, 0);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public Image getImage(){
        Image returnVal;
        if (meleeFrames!=-1){
            if (meleeFrames>=0 && meleeFrames<5){
                returnVal = melee1;
            }
            if (meleeFrames>=5 && meleeFrames<10){
                returnVal = melee2;
            }
            if (meleeFrames>=10 && meleeFrames<15){
                returnVal = melee3;
            }
        }
        returnVal = rest;
        return isFacingLeft ? returnVal.getFlippedCopy(true, false) : returnVal;
    }


    public Image getProjectile() {
        return pr;
    }

    public float getPr_X() {
        return pr_x;
    }

    public float getPr_Y() {
        return pr_y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getImageInt() {
        if (getImage().equals(rest)){
            return 0;
        }
        if (getImage().equals(rest.getFlippedCopy(true, false))){
            return 1;
        }
        if (getImage().equals(melee1)){
            return 2;
        }
        if (getImage().equals(melee1.getFlippedCopy(true, false))){
            return 3;
        }
        if (getImage().equals(melee2)){
            return 4;
        }
        if (getImage().equals(melee2.getFlippedCopy(true, false))){
            return 5;
        }
        if (getImage().equals(melee3)){
            return 6;
        }
        if (getImage().equals(melee3.getFlippedCopy(true, false))){
            return 7;
        }
        return -1;
    }

    public String getKey() {
        return key;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
}
