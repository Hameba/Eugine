package net.llamositopia.eugine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkManager {

    private static String IP;

    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    public static void connect(String ip){
        try {
            Socket s = new Socket(ip, 2600);
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
