package net.llamositopia.eugine;

import org.newdawn.slick.Image;

public class Projectile {

    private int x;
    private int y;
    private Character c;
    private boolean left;

    public Projectile(int x, int y, Character c, boolean left){
        this.x = x;
        this.y = y;
        this.c = c;
        this.left = left;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Character getCharacter(){
        return c;
    }

    public Image getImage(){
        return c.getProjectile();
    }

    public boolean getLeft(){
        return left;
    }
}
