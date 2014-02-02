package net.llamositopia.eugine;

import java.util.ArrayList;

public class Floor {

    private final int x, y;

    private static ArrayList<Floor> floors = new ArrayList<Floor>();

    public Floor(int x, int y){
        if (x%8!=0){
            throw new IllegalArgumentException();
        }
        if (y%8!=0){
            throw new IllegalArgumentException();
        }
        this.x = x;
        this.y = y;
        floors.add(this);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public static boolean isOnFloor(Character c){
        for (Floor a : floors){
            if (c.getY()+32==a.getY()){
                if (c.getX()+57<a.getX()){
                    continue;
                }
                if (c.getX()+25>a.getX()+8){
                    continue;
                }
                return true;
            }
        }
        return !(c.getY()<568);
    }
}
