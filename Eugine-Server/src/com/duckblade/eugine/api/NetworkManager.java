package com.duckblade.eugine.api;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.duckblade.eugine.api.VH.arena;
import static com.duckblade.eugine.api.VH.mapName;

public class NetworkManager {

    private static ServerSocket ss;

    protected static final CopyOnWriteArrayList<ObjectOutputStream> outs = new CopyOnWriteArrayList<ObjectOutputStream>();
    protected static final CopyOnWriteArrayList<ObjectInputStream> ins = new CopyOnWriteArrayList<ObjectInputStream>();
    private static boolean running = true;

    public static void startServer(int port){
        try{
            ss = new ServerSocket(port);
            arena = StateArena.arenas.get(new Random().nextInt(StateArena.arenas.size()));
            mapName = arena.getArenaKey();
            new Thread(new Runnable() {
                public void run() {
                    while (running) {
                        try{
                            final Socket s = ss.accept();
                            System.out.println("Received connection attempt from " + s.getInetAddress());
                            final ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                            oos.flush();
                            final ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                            if (ois.readBoolean()){
                                String ch = (String) ois.readObject();
                                boolean wrongChar = false;
                                for (Squishy c : Squishy.values()){
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
                                for (Squishy c : arena.squishies){
                                    if (c.getKey().equals(ch)){
                                        continue;
                                    }
                                    chars += ";" + c.getKey();
                                }
                                oos.writeObject(chars);
                                oos.writeObject(mapName);
                                outs.add(oos);
                                ins.add(ois);
                                arena.squishies.add(Squishy.valueOf(ch.toUpperCase()));
                                Squishy.valueOf(ch.toUpperCase()).setIP(String.valueOf(s.getInetAddress()));
                                System.out.println(s.getInetAddress() + " has successfully connected to the game as the " + ch + ".");
                                Squishy.valueOf(ch.toUpperCase()).setX(-25);
                                Squishy.valueOf(ch.toUpperCase()).setY(0);
                                Squishy.valueOf(ch.toUpperCase()).risingFrames = -1;
                                Squishy.valueOf(ch.toUpperCase()).frames = -1;
                                oos.flush();
                                System.out.println("Successfully connected to " + s.getInetAddress());
                                new Thread(new Runnable() {
                                    public void run() {
                                        while (s.isConnected()){
                                            InputManager.update(ois);
                                            OutputManager.update(oos);
                                        }
                                    }
                                }).start();
                            }else{
                                String options = "";
                                for (Squishy a : Squishy.values()){
                                    if (a.getIP()!=null){
                                        options+=options.equals("") ? a.getKey() : ";" + a.getKey();
                                    }
                                }
                                oos.writeObject(options);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void startServer(){
        startServer(21499);
    }

    public static void stopServer(){
        running = false;
    }

    public static boolean isRunning() {
        return running;
    }
}
