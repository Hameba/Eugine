package net.llamositopia.eugine;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        if (bg!=null){
            graphics.drawImage(bg, 0, 0);
        }
        for (Character c : characters){
            System.out.println(c.getX() + " " + c.getY());
            graphics.drawImage(c.getProjectile(), c.getPr_X(), c.getPr_Y());
            graphics.drawImage(c.getImage(), c.getX(), c.getY());
        }
        for (Floor a : getFloors()){
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
        if (gc.getInput().isKeyDown(Input.KEY_Z)){
            outData += outData.equals("") ? outData += "z" : ";z";
        }
        if (gc.getInput().isKeyDown(Input.KEY_X)){
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
            c.setPr_X(Integer.parseInt(charData[3]));
            c.setPr_Y(Integer.parseInt(charData[4]));
            c.setImageInt(Integer.parseInt(charData[5]));
            System.out.println(c.getKey() + ":" + c.getX() + ":" + c.getY() + ":" + c.getPr_X() + ":" + c.getPr_Y() + ":" + c.getImageInt());
        }
    }

    private void runServerLogicStuff() {
        for (Character c : characters){
            if (!c.prIsMoving() || c.getPr_X()<0 || c.getPr_X()>800){
                c.setPr_X(c.getX()+c.getImage().getWidth()/2);
                c.setPr_Y(c.getY()+c.getImage().getHeight()/2);
                c.setPrIsMoving(false, false);
            }
            if (c.prIsMoving()){
                if (c.prIsMovingLeft()){
                    c.setPr_X(c.getPr_X()-6);
                }else{
                    c.setPr_X(c.getPr_X()+6);
                }
            }
            if (c.deadFrames!=-1){
                c.deadFrames++;
                if (c.deadFrames>=90){
                    c.lives--;
                    if (c.lives>0){
                        c.deadFrames=-1;
                        c.setHealth(c.getMaxHealth());
                    }
                }
                c.setX(-100);
                c.setY(-100);
                continue;
            }
            if (c.risingFrames!=-1){
                if (c.risingFrames<12){
                    c.setY(c.getY()-8);
                }
                c.risingFrames+=1;
                if (Floor.isOnFloor(c)){
                    c.risingFrames=-1;
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
                if (c.prIsMoving()){
                    System.out.println("Start");
                    System.out.println(true);
                    System.out.println(c.getPr_X()<c2.getX()+82);
                    System.out.println(c.getPr_X()+c.getProjectile().getWidth()>c2.getX());
                    System.out.println(c.getPr_Y()<c2.getY()+32);
                    System.out.println(c.getPr_Y()+c.getProjectile().getHeight()>c2.getY());
                    if (c.getPr_X()<c2.getX()+82 && c.getPr_X()+c.getProjectile().getWidth()>c2.getX() && c.getPr_Y()<c2.getY()+32 && c.getPr_Y()+c.getProjectile().getHeight()>c2.getY()){
                        c2.damage(c.getRangedDamage(), c);
                        c.setPrIsMoving(false, false);
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
            if (c.frames>5){
                c.setImageInt(c.getImageInt()+2);
                if (c.getImageInt()==8 || c.getImageInt()==9){
                    c.frames=-1;
                    c.setImageInt(c.getImageInt()%8);
                }else{
                    c.frames=0;
                }
            }
            for (int i = 0; i < 4; i++) {
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

    protected abstract ArrayList<Floor> getFloors();

    private synchronized void runServerInputStuff() {
        for (ObjectInputStream ois : NetworkManager.ins){
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
                        c.setX(c.getX()-2);
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
                        c.setX(c.getX()+2);
                    }
                    if (dataRaw[i].equals("z")){
                        if (c.getImageInt()==1 || c.getImageInt()==0){
                            c.setImageInt(c.getImageInt()+2);
                            c.frames++;
                        }
                    }
                    if (dataRaw[i].equals("x")){
                        if (c.ammo>0 && !c.prIsMoving()){
                            c.setPrIsMoving(true, c.isFacingLeft());
                            c.setPr_X(c.isFacingLeft() ? c.getX()+25 : c.getX()+57);
                            c.setPr_Y(c.getY()+c.getImage().getHeight()/2);
                            c.ammo--;
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
            data += c.getPr_X() + ":";
            data += c.getPr_Y() + ":";
            data += c.getImageInt();
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
