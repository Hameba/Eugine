package net.llamositopia.eugine;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;

public enum Character {

    MAGE("mage", 10, 20, 80),
    NINJA("ninja", 15, 10, 95),
    BRUISER("bruiser", 20, 10, 90),
    ARCHER("archer", 10, 15, 85),
    ;

    private Image rest, melee1, melee2, melee3, pr;

    private int x = -25, y = 0, meleeDamage, rangedDamage, maxHealth, health;

    public int frames = 0, risingFrames = 0, deadFrames = -1;
    public boolean rising = false;

    private boolean isFacingLeft = false;
    private final String key;

    private String IP = null;
    private int imageInt = 0;

    protected int ammo = 15;

    public int lives = 5;
    private Character lastDamageSource = null;
    public boolean prIsMovingLeft;
    private boolean prIsMoving = false;
    private final ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    Character(String key, int meleeDamage, int rangedDamage, int maxHealth){
        this.meleeDamage = meleeDamage;
        this.rangedDamage = rangedDamage;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
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
        if (imageInt==0){
            return rest;
        }
        if (imageInt==1){
            return rest.getFlippedCopy(true, false);
        }
        if (imageInt==2){
            return melee1;
        }
        if (imageInt==3){
            return melee1.getFlippedCopy(true, false);
        }
        if (imageInt==4){
            return melee2;
        }
        if (imageInt==5){
            return melee2.getFlippedCopy(true, false);
        }
        if (imageInt==6){
            return melee3;
        }
        if (imageInt==7){
            return melee3.getFlippedCopy(true, false);
        }
        return rest;
    }

    public Image getProjectile() {
        return pr;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getImageInt() {
        return imageInt;
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

    public void setIsFacingLeft(boolean b){
        isFacingLeft = b;
    }

    public boolean isFacingLeft(){
        return isFacingLeft;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setImageInt(int imageInt) {
        this.imageInt = imageInt;
    }

    public int getMeleeDamage() {
        return meleeDamage;
    }

    public int getRangedDamage() {
        return rangedDamage;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void damage(int meleeDamage, Character source) {
        this.health -= meleeDamage;
        this.lastDamageSource = source;
    }

    public Character getLastDamageSource(){
        return lastDamageSource;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void launchProjectile(boolean left){
        if (left){

        }else{

        }
    }

    public synchronized ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
}
