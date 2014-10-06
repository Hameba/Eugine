package net.llamositopia.eugine.mapcreator;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StateMapCreator extends BasicGameState {

    private int capturing = -1;

    class Tile{
        int x, y;
        int state = 0;
        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }

        public boolean equals(Object obj) {
            return obj instanceof Tile && x == ((Tile) obj).x && y == ((Tile) obj).y;
        }

        public int getState(){
            return state;
        }

        public void toggleStateUp(){
            state++;
            if (state>7){
                state = 0;
            }
        }

        public void toggleStateDown(){
            state--;
            if (state<0){
                state = 7;
            }
        }

    }

    ArrayList<Tile> tiles = new ArrayList<Tile>();

    public int getID() {
        return 0;
    }

    private Image bg;

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        bg = new Image(fileChooser.getSelectedFile().getAbsolutePath());
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(bg, 0, 0);
        for (Tile a : tiles){
            g.setColor(a.getState()==0 ? Color.green : a.getState()==1 ? Color.blue : a.getState()==2 ? Color.orange : a.getState()==3 ? Color.magenta : a.getState()==4 ? Color.gray : a.getState()==5 ? Color.red : a.getState()==6 ? Color.yellow : Color.pink);
            g.fillRect(a.x, a.y, 8, 8);
        }
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
            boolean found = false;
            for (Tile t : tiles){
                if (t.x==roundDown(gc.getInput().getMouseX())){
                    if (t.y==roundDown(gc.getInput().getMouseY())){
                        found = true;
                        t.toggleStateUp();
                        break;
                    }
                }
            }
            if (!found){
                tiles.add(new Tile(roundDown(gc.getInput().getMouseX()), roundDown(gc.getInput().getMouseY())));
            }
        }else if (gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
            tiles.remove(new Tile(roundDown(gc.getInput().getMouseX()), roundDown(gc.getInput().getMouseY())));
        }
        if (gc.getInput().isKeyPressed(Input.KEY_F5)){
            String id = JOptionPane.showInputDialog("Please enter a map id:");
            new File("..\\Squishy-API\\res\\arena\\" + id).mkdirs();
            try {
                PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("..\\Squishy-API\\res\\arena\\" + id + "\\floors.dat", false)));
                for (Tile a : tiles){
                    out.println(a.x + ";" + a.y);
                    out.flush();
                }
                out.close();
                BufferedImage bi = toBufferedImage(bg, true);
                ImageIO.write(bi, "png", new File("..\\Squishy-API\\res\\arena\\" + id + "\\bg.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    int roundDown(int x) {
        return (int) (Math.floor(x / 8d) * 8);
    }

    private BufferedImage toBufferedImage(Image i, boolean hasAlpha) {
        Image image = i.getFlippedCopy(false, true);
        int len = 4 * image.getWidth() * image.getHeight();
        if (!hasAlpha) {
            len = 3 * image.getWidth() * image.getHeight();
        }
        ByteBuffer out = ByteBuffer.allocate(len);
        org.newdawn.slick.Color c;
        for (int y = image.getHeight()-1; y >= 0; y--) {
            for (int x = 0; x < image.getWidth(); x++) {
                c = image.getColor(x, y);
                out.put((byte) (c.r * 255.0f));
                out.put((byte) (c.g * 255.0f));
                out.put((byte) (c.b * 255.0f));
                if (hasAlpha) {
                    out.put((byte) (c.a * 255.0f));
                }
            }
        }
        DataBufferByte dataBuffer = new DataBufferByte(out.array(), len);
        PixelInterleavedSampleModel sampleModel;
        ColorModel cm;
        if (hasAlpha) {
            int[] offsets = { 0, 1, 2, 3 };
            sampleModel = new PixelInterleavedSampleModel(
                    DataBuffer.TYPE_BYTE, image.getWidth(), image.getHeight(), 4,
                    4 * image.getWidth(), offsets);
            cm = new ComponentColorModel(ColorSpace
                    .getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 },
                    true, false, ComponentColorModel.TRANSLUCENT,
                    DataBuffer.TYPE_BYTE);
        } else {
            int[] offsets = { 0, 1, 2};
            sampleModel = new PixelInterleavedSampleModel(
                    DataBuffer.TYPE_BYTE, image.getWidth(), image.getHeight(), 3,
                    3 * image.getWidth(), offsets);
            cm = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                    new int[] {8,8,8,0},
                    false,
                    false,
                    ComponentColorModel.OPAQUE,
                    DataBuffer.TYPE_BYTE);
        }
        WritableRaster raster = Raster.createWritableRaster(sampleModel, dataBuffer, new Point(0, 0));
        return new BufferedImage(cm, raster, false, null);
    }

}