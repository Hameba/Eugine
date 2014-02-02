package net.llamositopia.eugine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class NetworkManager {

    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    private static ServerSocket ss;

    private static boolean isServer = false;
    protected static ArrayList<ObjectOutputStream> outs = new ArrayList<ObjectOutputStream>();
    protected static ArrayList<ObjectInputStream> ins = new ArrayList<ObjectInputStream>();

    public static void connect(String ip){
        try {
            Socket s = new Socket(ip, 2600);
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(s.getInputStream());
            oos.writeObject(VH.myChar.getKey());
            if (ois.readBoolean()){
                String chars = (String) ois.readObject();
                String map = (String) ois.readObject();
                System.out.println("Entering map: " + map);
                if (map.equals("planes")){
                    VH.sbg.enterState(10);
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
            ss = new ServerSocket(2600);
            VH.mapName = "planes";
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
                            outs.add(oos);
                            ins.add(ois);
                            VH.arena.characters.add(Character.valueOf(ch.toUpperCase()));
                            Character.valueOf(ch.toUpperCase()).setIP(String.valueOf(s.getInetAddress()));
                            System.out.println(s.getInetAddress() + " has successfully connected to the game as the " + ch + ".");
                            Character.valueOf(ch.toUpperCase()).setX(-25);
                            Character.valueOf(ch.toUpperCase()).setY(0);
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
