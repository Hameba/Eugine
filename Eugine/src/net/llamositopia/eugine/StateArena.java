package net.llamositopia.eugine;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class StateArena extends BasicGameState{

    public final int getID() {
        return getArenaID()+10;
    }

    protected abstract int getArenaID();

    protected ArrayList<Character> characters = new ArrayList<Character>();

    private Image floorImage;

    protected Image bg;

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        floorImage = new Image("res/img/floor.png");
        VH.arena = this;
        bg = getBackground();
    }

    public Image getBackground() throws SlickException{
        Image i = new Image("res/img/" + getArenaKey() + "/background.png");
        Floor.clearFloors();
        for (int x = 0; x < i.getWidth(); x++) {
            for (int y = 0; y < i.getHeight(); y++) {
                if (x%8==0 && y%8==0){
                    if (i.getColor(x, y).equals(new Color(0xff00ff))){
                        new Floor(x, y);
                    }
                }
            }
        }
        return i;
    }

    protected abstract String getArenaKey();

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        if (bg!=null){
            graphics.drawImage(bg, 0, 0);
        }
        for (Character c : characters){
            System.out.println(c.getX() + " " + c.getY());
            for (Iterator<Projectile> i = c.getProjectiles().iterator();i.hasNext();){
                Projectile p = i.next();
                graphics.drawImage(p.getImage(), p.getX(), p.getY());
            }
            graphics.drawImage(c.getImage(), c.getX(), c.getY());
            graphics.setColor(Color.white);
            graphics.drawString("Health: " + c.getHealth() + "\nLives: " + c.lives, c.getX()+c.getImage().getWidth()/2-graphics.getFont().getWidth("Health: " + c.getHealth() + "\nLives: " + c.lives)/2, c.getY()-graphics.getFont().getHeight("Health: " + c.getHealth() + "\nLives: " + c.lives));
        }
        for (Floor a : Floor.getFloors()){
            graphics.drawImage(floorImage, a.getX(), a.getY());
        }
    }

    public void update(GameContainer gc, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (NetworkManager.isServer()){
            runServerInputStuff();
            runServerLogicStuff();
            runServerOutputStuff();
            return;
        }
        String outData = VH.myChar.getKey();
        if (gc.getInput().isKeyDown(Input.KEY_LEFT)){
            outData += outData.equals("") ? outData += "<" : ";<";
        }
        if (gc.getInput().isKeyDown(Input.KEY_UP)){
            outData += outData.equals("") ? outData += "^" : ";^";
        }
        if (gc.getInput().isKeyDown(Input.KEY_RIGHT)){
            outData += outData.equals("") ? outData += ">" : ";>";
        }
        if (gc.getInput().isKeyPressed(Input.KEY_Z)){
            outData += outData.equals("") ? outData += "z" : ";z";
        }
        if (gc.getInput().isKeyPressed(Input.KEY_X)){
            outData += outData.equals("") ? outData += "x" : ";x";
        }
        NetworkManager.send(outData);
        String inDataRaw = NetworkManager.receive();
        System.out.println("Received " + inDataRaw + " from the server.");
        String[] inData = inDataRaw.split(";");
        for (String a : inData){
            System.out.println("Using " + a);
            String[] charData = a.split(":");
            Character c = Character.valueOf(charData[0].toUpperCase());
            if (!characters.contains(c)){
                characters.add(c);
            }
            c.setX(Integer.parseInt(charData[1]));
            c.setY(Integer.parseInt(charData[2]));
            c.setImageInt(Integer.parseInt(charData[3]));
            c.setHealth(Integer.parseInt(charData[4]));
            c.lives = Integer.parseInt(charData[5]);
            synchronized (c.getProjectiles()){
                c.getProjectiles().clear();
                for (int j = 0; j < charData.length; j++) {
                    if (j<6){
                        continue;
                    }
                    c.getProjectiles().add(new Projectile(Integer.parseInt(charData[j]), Integer.parseInt(charData[j+1]), c, false));
                    j++;
                }
            }
        }
    }

    private void runServerLogicStuff() {
        for (Character c : characters){
            for (Iterator<Projectile> i = c.getProjectiles().iterator();i.hasNext();){
                Projectile p = i.next();
                if (p.getX()<0 || p.getX()>800){
                    i.remove();
                }
                if (p.getLeft()){
                    for (int j = 0; j < 12; j++) {
                        for (Floor a : Floor.getFloors()){
                            if (p.getY()<a.getY()+8){
                                if (p.getY()+p.getImage().getHeight()>a.getY()){
                                    if (p.getX()==a.getX()+8){
                                        i.remove();
                                        break;
                                    }
                                    p.setX(p.getX()-1);
                                }
                            }
                        }
                    }
                }else{
                    for (int j = 0; j < 12; j++) {
                        for (Floor a : Floor.getFloors()){
                            if (p.getY()<a.getY()+8){
                                if (p.getY()+p.getImage().getHeight()>a.getY()){
                                    if (p.getX()+p.getImage().getWidth()==a.getX()){
                                        i.remove();
                                        break;
                                    }
                                    p.setX(p.getX()+1);
                                }
                            }
                        }
                    }
                }
            }
            if (c.deadFrames!=-1){
                c.deadFrames++;
                if (c.deadFrames>=90){
                    c.lives--;
                    if (c.lives>0){
                        c.deadFrames=-1;
                        c.ammo = 15;
                        c.setHealth(c.getMaxHealth());
                    }
                }
                c.setX(-100);
                c.setY(-100);
                continue;
            }
            if (c.risingFrames!=-1) {
                boolean notUp = false;
                if (c.risingFrames < 12) {
                    for (int i = 0; i < 16; i++) {
                        for (Floor a : Floor.getFloors()) {
                            if (c.getY() == a.getY() + 8) {
                                if (c.getX() + 57 < a.getX()) {
                                    continue;
                                }
                                if (c.getX() + 25 > a.getX() + 8) {
                                    continue;
                                }
                                notUp = true;
                            }
                        }
                        if (!notUp){
                            c.setY(c.getY()-1);
                        }else{
                            break;
                        }
                    }
                }
                if (notUp) c.risingFrames =-1;
                else c.risingFrames += 1;
                if (Floor.isOnFloor(c)) {
                    c.risingFrames = -1;
                }
            }
            for (Character c2 : characters){
                if (c.equals(c2)){
                    continue;
                }
                if (c.frames==0 && (c.getImageInt()==2 || c.getImageInt()==3)){
                    if (c.getY()<c2.getY()+32 && c.getY()+32>c2.getY()){
                        if (c.isFacingLeft()){
                            if (c.getX()<=c2.getX()+c2.getImage().getWidth() && c.getX()+c.getImage().getWidth()-25>c2.getX() && c.getY()<=c2.getY()+c2.getImage().getHeight() && c.getY()+c.getImage().getHeight()>c2.getY()){
                                c2.damage(c.getMeleeDamage(), c);
                            }
                        }else{
                            if (c.getX()<=c2.getX()+c2.getImage().getWidth() && c.getX()+c.getImage().getWidth()+25>c2.getX() && c.getY()<=c2.getY()+c2.getImage().getHeight() && c.getY()+c.getImage().getHeight()>c2.getY()){
                                c2.damage(c.getMeleeDamage(), c);
                            }
                        }
                    }
                }
                for (Iterator<Projectile> i = c.getProjectiles().iterator();i.hasNext();){
                    Projectile p = i.next();
                    if (p.getX()<c2.getX()+82 && p.getX()+c.getProjectile().getWidth()>c2.getX() && p.getY()<c2.getY()+32 && p.getY()+c.getProjectile().getHeight()>c2.getY()){
                        c2.damage(c.getRangedDamage(), c);
                        i.remove();
                    }
                }
                if (c2.getHealth()<=0){
                    c2.deadFrames = 0;
                    c2.setHealth(c2.getMaxHealth());
                }
            }
            if (c.frames!=-1){
                c.frames++;
            }
            if (c.frames>2){
                c.setImageInt(c.getImageInt()+2);
                if (c.getImageInt()==8 || c.getImageInt()==9){
                    c.frames=-1;
                    c.setImageInt(c.getImageInt()%8);
                }else{
                    c.frames=0;
                }
            }
            for (int i = 0; i < 8; i++) {
                if (!Floor.isOnFloor(c)){
                    c.setY(c.getY()+1);
                }
            }
            if (c.deadFrames==-1){
                if (c.getX()<-25){
                    c.setX(-25);
                }
                if (c.getX()>743){
                    c.setX(743);
                }
                if (c.getY()<0){
                    c.setY(0);
                }
                if (c.getY()>568){
                    c.setY(568);
                }
            }else{
                c.setX(-82);
                c.setY(-32);
            }
        }
    }

    private synchronized void runServerInputStuff() {
        for (Iterator<ObjectInputStream> j = NetworkManager.ins.iterator();j.hasNext();){
            ObjectInputStream ois = j.next();
            try {
                String inData = (String) ois.readObject();
                System.out.println(inData);
                String[] dataRaw = inData.split(";");
                Character c = Character.valueOf(dataRaw[0].toUpperCase());
                for (int i = 0; i < dataRaw.length; i++) {
                    if (c.deadFrames!=-1){break;}
                    if (i==0) continue;
                    if (dataRaw[i].equals("<")){
                        if (c.frames==-1){
                            c.setIsFacingLeft(true);
                            c.setImageInt(1);
                        }
                        boolean notMoving = false;
                        for (Floor a : Floor.getFloors()){
                            if (c.getY()<a.getY()+8 && c.getY()+32>a.getY()){
                                if (a.getX()+8==c.getX()+25){
                                    notMoving = true;
                                }
                            }
                        }
                        if (!notMoving){
                            c.setX(c.getX()-4);
                        }
                    }
                    if (dataRaw[i].equals("^")){
                        if (c.risingFrames==-1){
                            c.risingFrames=0;
                        }
                    }
                    if (dataRaw[i].equals(">")){
                        if (c.frames==-1){
                            c.setIsFacingLeft(false);
                            c.setImageInt(0);
                        }
                        boolean notMoving = false;
                        for (Floor a : Floor.getFloors()){
                            if (c.getY()<a.getY()+8 && c.getY()+32>a.getY()){
                                if (a.getX()==c.getX()+57){
                                    notMoving = true;
                                }
                            }
                        }
                        if (!notMoving){
                            c.setX(c.getX()+4);
                        }
                    }
                    if (dataRaw[i].equals("z")){
                        if (c.getImageInt()==1 || c.getImageInt()==0){
                            c.setImageInt(c.getImageInt()+2);
                            c.frames++;
                        }
                    }
                    if (dataRaw[i].equals("x")){
                        if (c.ammo>0){
                            synchronized (c.getProjectiles()){
                                c.getProjectiles().add(new Projectile(c.isFacingLeft() ? c.getX() + 25 : c.getX() + 57, c.getY() + c.getImage().getHeight() / 2, c, c.isFacingLeft()));
                                c.ammo--;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void runServerOutputStuff() {
        String data = "";
        for (Character c : characters){
            data += data.equals("") ? "" + c.getKey() : ";" + c.getKey();
            data += ":" + c.getX() + ":";
            data += c.getY() + ":";
            data += c.getImageInt() + ":";
            data += c.getHealth() + ":";
            data += c.lives;
            for (Iterator<Projectile> i = c.getProjectiles().iterator();i.hasNext();){
                Projectile p = i.next();
                data += ":" + p.getX() + ":" + p.getY();
            }
        }
        for (ObjectOutputStream oos : NetworkManager.outs){
            try {
                oos.writeObject(data);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
