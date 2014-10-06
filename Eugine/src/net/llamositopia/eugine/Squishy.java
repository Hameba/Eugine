package net.llamositopia.eugine;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.ArrayList;

public abstract class Squishy {

    private static ArrayList<Squishy> squishies = new ArrayList<Squishy>();
    private final int speed;
    private final int def;
    private final int pr_def;

    private Image rest, melee1, melee2, melee3, pr;

    private int x = -25, y = 0, meleeDamage, rangedDamage, maxHealth, health;

    public int frames = 0, risingFrames = 0, deadFrames = -1;
    public boolean rising = false;

    private boolean isFacingLeft = false;
    private String key;

    private String IP = null;
    private int imageInt = 0;

    protected int ammo = 15;

    public int lives = 5;
    private Squishy lastDamageSource = null;
    public boolean prIsMovingLeft;
    private boolean prIsMoving = false;
    private final ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    public Squishy(String key, int meleeDamage, int rangedDamage, int maxHealth, int speed, int def, int pr_def){
        this.meleeDamage = meleeDamage;
        this.rangedDamage = rangedDamage;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.key = key;
        this.speed = 4;
        this.def = def;
        this.pr_def = pr_def;
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
        squishies.add(this);
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

    public void damage(int meleeDamage, Squishy source) {
        this.health -= (meleeDamage-def>=0 ? meleeDamage-def : 0);
        this.lastDamageSource = source;
    }

    public void damageRanged(int rangedDamage, Squishy source) {
        this.health -= (rangedDamage-def>=0 ? meleeDamage-def : 0);
        this.lastDamageSource = source;
    }

    public Squishy getLastDamageSource(){
        return lastDamageSource;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public synchronized ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public static Squishy[] values() {
        try {
            Class.forName("net.llamositopia.eugine.chars.SquishyArcher");
            Class.forName("net.llamositopia.eugine.chars.SquishyBrainiac");
            Class.forName("net.llamositopia.eugine.chars.SquishyBruiser");
            Class.forName("net.llamositopia.eugine.chars.SquishyGenie");
            Class.forName("net.llamositopia.eugine.chars.SquishyMage");
            Class.forName("net.llamositopia.eugine.chars.SquishyNinja");
            Class.forName("net.llamositopia.eugine.chars.SquishyPenguin");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Squishy[] returnVal = new Squishy[squishies.size()];
        for (int i = 0; i < squishies.size(); i++) {
            returnVal[i] = squishies.get(i);
        }
        return returnVal;
    }

    public static Squishy valueOf(String key){
        key = key.toLowerCase();
        Squishy c = null;
        for (Squishy b : Squishy.values()){
            if (b.getKey().equals(key)){
                c = b;
                break;
            }
        }
        return c;
    }

    public abstract void usePrimaryAbility();

    public abstract void useSecondaryAbility();

    public abstract boolean isPrimaryActive();
    public abstract boolean isSecondaryActive();

    public int getSpeed() {
        return speed;
    }

    public int getDef() {
        return def;
    }

    public int getPr_def() {
        return pr_def;
    }
}
