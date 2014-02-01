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
    private static ArrayList<ObjectInputStream> ins = new ArrayList<ObjectInputStream>();

    public static void connect(String ip){
        try {
            Socket s = new Socket(ip, 2600);
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(s.getInputStream());
            oos.writeObject(VH.myChar);
            if (ois.readBoolean()){
                String map = (String) ois.readObject();

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
            ss = new ServerSocket(2600);
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Socket s = ss.accept();
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
                                continue;
                            }
                            oos.writeBoolean(true);
                            oos.writeObject(VH.mapName);
                            outs.add(oos);
                            ins.add(ois);
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

}
