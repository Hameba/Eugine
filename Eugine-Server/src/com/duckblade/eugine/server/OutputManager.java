package com.duckblade.eugine.server;

import com.duckblade.eugine.api.Projectile;
import com.duckblade.eugine.api.Squishy;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class OutputManager {
    public static void update(ObjectOutputStream oos) {
        String data = "";
        for (Squishy c : VH.arena.squishies){
            data += data.equals("") ? "" + c.getKey() : ";" + c.getKey();
            data += ":" + c.getX() + ":";
            data += c.getY() + ":";
            data += c.getImageInt() + ":";
            data += c.getHealth() + ":";
            data += c.lives;
            for (Projectile p : c.getProjectiles()) {
                data += ":" + p.getX() + ":" + p.getY();
            }
        }
        try {
            oos.writeObject(data);
            oos.flush();
        } catch (IOException ignored) {
        }
    }
}
