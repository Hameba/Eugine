package net.llamositopia.eugine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.duckblade.eugine.api.Projectile;
import com.duckblade.eugine.api.Squishy;
import com.duckblade.eugine.api.StateArena;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class NetworkManager {

    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    public static void connect(String ip){
        try {
            Socket s = new Socket(ip, 21499);
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(s.getInputStream());
            oos.writeObject(true);
            oos.writeObject(VH.myChar.getKey());
            if (ois.readBoolean()){
                String chars = (String) ois.readObject();
                String map = (String) ois.readObject();
                System.out.println("Entering map: " + map);
                for (StateArena b : StateArena.arenas){
                    if (b.getArenaKey().equalsIgnoreCase(map)){
                        VH.sbg.enterState(b.getID());
                        VH.arena = (StateArena) VH.sbg.getState(b.getID());
                        break;
                    }
                }
                for (String a : chars.split(";")){
                    VH.arena.squishies.add(Squishy.valueOf(a.toUpperCase()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static void send(GameContainer gc) {
        try {
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
            System.out.println("Sending " + outData  + " to the server.");
            oos.writeObject(outData);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void receive() {
        try {
            String inDataRaw = (String) ois.readObject();
            System.out.println("Received " + inDataRaw + " from the server.");
            String[] inData = inDataRaw.split(";");
            for (String a : inData) {
                System.out.println("Using " + a);
                String[] charData = a.split(":");
                Squishy c = Squishy.valueOf(charData[0].toUpperCase());
                if (!VH.arena.squishies.contains(c)) {
                    VH.arena.squishies.add(c);
                }
                c.setX(Integer.parseInt(charData[1]));
                c.setY(Integer.parseInt(charData[2]));
                c.setImageInt(Integer.parseInt(charData[3]));
                c.setHealth(Integer.parseInt(charData[4]));
                c.lives = Integer.parseInt(charData[5]);
                synchronized (c.getProjectiles()) {
                    c.getProjectiles().clear();
                    for (int j = 0; j < charData.length; j++) {
                        if (j < 6) {
                            continue;
                        }
                        c.getProjectiles().add(new Projectile(Integer.parseInt(charData[j]), Integer.parseInt(charData[j + 1]), c, false));
                        j++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ping(String ip) {

    }
}
