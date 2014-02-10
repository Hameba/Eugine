package net.llamositopia.eugine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class NetworkManager {

    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    private static ServerSocket ss;

    private static boolean isServer = false;
    protected static final CopyOnWriteArrayList<ObjectOutputStream> outs = new CopyOnWriteArrayList<ObjectOutputStream>();
    protected static final CopyOnWriteArrayList<ObjectInputStream> ins = new CopyOnWriteArrayList<ObjectInputStream>();

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
                if (map.equals("plains")){
                    VH.sbg.enterState(10);
                    VH.arena = (StateArena) VH.sbg.getState(10);
                }
                if (map.equals("factory")){
                    VH.sbg.enterState(11);
                    VH.arena = (StateArena) VH.sbg.getState(11);
                }
                if (map.equals("battlezone")){
                    VH.sbg.enterState(12);
                    VH.arena = (StateArena) VH.sbg.getState(12);
                }
                if (map.equals("lumberyard")){
                    VH.sbg.enterState(13);
                    VH.arena = (StateArena) VH.sbg.getState(13);
                }
                for (String a : chars.split(";")){
                    VH.arena.characters.add(Character.valueOf(a.toUpperCase()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean isServer(){
        return isServer;
    }

    public static void startServer(){
        try {
            isServer = true;
            ss = new ServerSocket(21499);
            int map = new Random().nextInt(4);
            if (map==0){
                VH.mapName = "plains";
                VH.sbg.enterState(10);
            }else if (map==1){
                VH.mapName = "factory";
                VH.sbg.enterState(11);
            }else if (map==2){
                VH.mapName = "battlezone";
                VH.sbg.enterState(12);
            }else{
                VH.mapName = "lumberyard";
                VH.sbg.enterState(13);
            }
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Socket s = ss.accept();
                            System.out.println("Recieved connection attempt from " + s.getInetAddress());
                            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                            oos.flush();
                            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                            String ch = (String) ois.readObject();
                            boolean wrongChar = false;
                            for (Character c : Character.values()){
                                if (c.getKey().equals(ch)){
                                    if (c.getIP()!=null){
                                        wrongChar = true;
                                    }
                                }
                            }
                            if (wrongChar){
                                oos.writeBoolean(false);
                                s.close();
                                System.out.println("Kicking " + s.getInetAddress());
                                continue;
                            }
                            oos.writeBoolean(true);
                            String chars = ch;
                            for (Character c : VH.arena.characters){
                                if (c.getKey().equals(ch)){
                                    continue;
                                }
                                chars += ";" + c.getKey();
                            }
                            oos.writeObject(chars);
                            oos.writeObject(VH.mapName);
                            synchronized (outs){
                                outs.add(oos);
                                ins.add(ois);
                            }
                            VH.arena.characters.add(Character.valueOf(ch.toUpperCase()));
                            Character.valueOf(ch.toUpperCase()).setIP(String.valueOf(s.getInetAddress()));
                            System.out.println(s.getInetAddress() + " has successfully connected to the game as the " + ch + ".");
                            Character.valueOf(ch.toUpperCase()).setX(-25);
                            Character.valueOf(ch.toUpperCase()).setY(0);
                            Character.valueOf(ch.toUpperCase()).risingFrames = -1;
                            Character.valueOf(ch.toUpperCase()).frames = -1;
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isServer = true;
    }

    public static void send(String outData) {
        try {
            System.out.println("Sending " + outData  + " to the server.");
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
