package net.llamositopia.eugine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.duckblade.eugine.api.Squishy;
import com.duckblade.eugine.api.StateArena;

public class NetworkManager {

    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    public static void connect(String ip){
        try {
            Socket s = new Socket(ip, 21499);
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(s.getInputStream());
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



    public static void send(String outData) {
        try {
            System.out.println("Sending " + outData  + " to the api.");
            oos.writeObject(outData);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String receive() {
        try {
            return (String) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
