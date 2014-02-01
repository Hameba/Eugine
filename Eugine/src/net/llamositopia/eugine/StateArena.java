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

    protected ArrayList<Character> characters;

    private Image floorImage;

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        floorImage = new Image("res/img/floor.png");
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        for (Character c : characters){
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
        String inDataRaw = NetworkManager.receive();
        String[] inData = inDataRaw.split(";");
        for (String a : inData){
            String[] charData = a.split(":");
            Character c = Character.valueOf(charData[0].toUpperCase());
            c.setX(Integer.parseInt(charData[1]));
            c.setY(Integer.parseInt(charData[2]));
            c.setPr_X(Integer.parseInt(charData[3]));
            c.setPr_Y(Integer.parseInt(charData[4]));
            c.setImageInt(Integer.parseInt(charData[5]));
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
    }

    private void runServerLogicStuff() {
        for (Character c : characters){
            if (c.deadFrames!=-1){
                c.deadFrames++;
                if (c.deadFrames>=180){
                    c.deadFrames=-1;
                }
                c.setX(-25);
                c.setY(568);
                continue;
            }
            if (c.risingFrames!=-1){
                c.setY(c.getY()+8);
                c.risingFrames++;
                if (c.risingFrames==12){
                    c.risingFrames=-1;
                }
            }
            if (c.frames==0 && c.getImageInt()==2){
                for (Character c2 : characters){
                    if (c.equals(c2)){
                        continue;
                    }
                    if (c.getY()<c2.getY()+32 && c.getY()+32>c2.getY()){
                        if (c.isFacingLeft()){
                            if (c.getX()<=c2.getX()+c2.getImage().getWidth() && c.getX()+c.getImage().getWidth()-35>c2.getX()){
                                c2.damage(c.getMeleeDamage(), c);
                            }
                        }else{
                            if (c.getX()<=c2.getX()+c2.getImage().getWidth() && c.getX()+c.getImage().getWidth()+35>c2.getX()){
                                c2.damage(c.getMeleeDamage(), c);
                            }
                        }
                    }
                    if (c.prIsMovingLeft()){
                        if (c.getPr_X()<=c2.getX()+c2.getImage().getWidth() && c.getPr_X()+c.getProjectile().getWidth()-35>c2.getX()){
                            c2.damage(c.getRangedDamage(), c);
                        }
                    }else{
                        if (c.getPr_X()<=c2.getX()+c2.getImage().getWidth() && c.getPr_X()+c.getProjectile().getWidth()+35>c2.getX()){
                            c2.damage(c.getRangedDamage(), c);
                        }
                    }
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
            for (Floor a : getFloors()){
                if (c.getY()<a.getY()){
                    c.setY(c.getY()-1);
                }
                if (c.getY()<a.getY()){
                    c.setY(c.getY()-1);
                }
                if (c.getY()<a.getY()){
                    c.setY(c.getY()-1);
                }
                if (c.getY()<a.getY()){
                    c.setY(c.getY()-1);
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

    private void runServerInputStuff() {
        for (ObjectInputStream ois : NetworkManager.ins){
            try {
                String inData = (String) ois.readObject();
                String[] dataRaw = inData.split(";");
                Character c = Character.valueOf(dataRaw[0].toUpperCase());
                for (int i = 0; i < dataRaw.length; i++) {
                    if (c.deadFrames!=-1){break;}
                    if (i==0) continue;
                    if (dataRaw[i].equals("<")){
                        c.setIsFacingLeft(true);
                        c.setImageInt(1);
                        c.setX(c.getX()-2);
                    }
                    if (dataRaw[i].equals("^")){
                        c.risingFrames = c.risingFrames==-1 ? c.risingFrames++ : c.risingFrames;
                    }
                    if (dataRaw[i].equals(">")){
                        c.setIsFacingLeft(false);
                        c.setImageInt(0);
                        c.setX(c.getX()+2);
                    }
                    if (dataRaw[i].equals("z")){
                        c.setImageInt(c.getImageInt()+2);
                        c.frames++;
                    }
                    if (dataRaw[i].equals("x")){
                        c.setPrIsMoving(true, c.isFacingLeft());
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
