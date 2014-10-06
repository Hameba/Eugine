package com.duckblade.eugine.api;

import java.io.IOException;
import java.io.ObjectInputStream;

public class InputManager {

    public static void update(ObjectInputStream ois) {
        try {
            String inData = (String) ois.readObject();
            System.out.println(inData);
            String[] dataRaw = inData.split(";");
            Squishy c = Squishy.valueOf(dataRaw[0].toUpperCase());
            for (int i = 0; i < dataRaw.length; i++) {
                if (c.deadFrames != -1) {
                    break;
                }
                if (i == 0) continue;
                if (dataRaw[i].equals("<")) {
                    if (c.frames == -1) {
                        c.setIsFacingLeft(true);
                        c.setImageInt(1);
                    }
                    boolean notMoving = false;
                    loop:
                    for (Floor a : Floor.getFloors()) {
                        if (c.getY() < a.getY() + 8 && c.getY() + 32 > a.getY()) {
                            for (int k = 0; k < c.getSpeed(); k++) {
                                if (a.getX() + 8 == c.getX() + 25) {
                                    notMoving = true;
                                    break loop;
                                }
                                c.setX(c.getX() - 1);
                            }
                            c.setX(c.getX() + c.getSpeed());
                        }
                    }
                    if (!notMoving) {
                        c.setX(c.getX() - c.getSpeed());
                    }
                }
                if (dataRaw[i].equals("^")) {
                    if (c.risingFrames == -1) {
                        c.risingFrames = 0;
                    }
                }
                if (dataRaw[i].equals(">")) {
                    if (c.frames == -1) {
                        c.setIsFacingLeft(false);
                        c.setImageInt(0);
                    }
                    boolean notMoving = false;
                    loop:
                    for (Floor a : Floor.getFloors()) {
                        if (c.getY() < a.getY() + 8 && c.getY() + 32 > a.getY()) {
                            for (int k = 0; k < c.getSpeed(); k++) {
                                if (a.getX() == c.getX() + 57) {
                                    notMoving = true;
                                    break loop;
                                }
                                c.setX(c.getX() + 1);
                            }
                            c.setX(c.getX() - c.getSpeed());
                        }
                    }
                    if (!notMoving) {
                        c.setX(c.getX() + c.getSpeed());
                    }
                }
                if (dataRaw[i].equals("z")) {
                    if (c.getImageInt() == 1 || c.getImageInt() == 0) {
                        c.setImageInt(c.getImageInt() + 2);
                        c.frames++;
                    }
                }
                if (dataRaw[i].equals("x")) {
                    if (c.ammo > 0) {
                        synchronized (c.getProjectiles()) {
                            c.getProjectiles().add(new Projectile(c.isFacingLeft() ? c.getX() + 25 : c.getX() + 57, c.getY() + c.getImage().getHeight() / 2, c, c.isFacingLeft()));
                            c.ammo--;
                        }
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
