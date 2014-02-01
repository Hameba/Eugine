package net.llamositopia.eugine;

public class Floor {

    private final int x, y;

    public Floor(int x, int y){
        if (x%8!=0){
            throw new IllegalArgumentException();
        }
        if (y%8!=0){
            throw new IllegalArgumentException();
        }
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
