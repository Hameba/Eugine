package net.llamositopia.eugine;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StateConnect extends BasicGameState {
    public int getID() {
        return 2;
    }

    Image bg;
    Image connect;
    Image quit;

    String ip = "";

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        bg = new Image("res/img/menu/background.png");
        connect = new Image("res/img/menu/connect.png");
        quit = new Image("res/img/menu/quit.png");
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(bg, 0, 0);
        g.drawImage(connect, gc.getWidth()/2-connect.getWidth()/2, gc.getHeight()/2+connect.getHeight()/2+gc.getHeight()/5);
        g.setColor(Color.black);
        g.fillRect(200, 250, 400, 100);
        g.setColor(Color.white);
        g.drawString(ip, gc.getWidth()/2-g.getFont().getWidth(ip)/2, gc.getHeight()/2-g.getFont().getHeight(ip)/2);
        g.drawImage(quit, gc.getWidth()/2-quit.getWidth()/2, gc.getHeight()/2+quit.getHeight()/2+gc.getHeight()/3);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        if (gc.getInput().isKeyPressed(Input.KEY_F11)){
            gc.setFullscreen(!gc.isFullscreen());
        }
        if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
            if (gc.getInput().getMouseX()>gc.getWidth()/2-connect.getWidth()/2 && gc.getInput().getMouseX()<gc.getWidth()/2+connect.getWidth()/2 && gc.getInput().getMouseY()>gc.getHeight()/2-connect.getHeight()/2+gc.getHeight()/5 && gc.getInput().getMouseY()>gc.getHeight()/2+connect.getHeight()/2+gc.getHeight()/5){
                for (Character c : Character.values()){
                    c.setX(-100);
                    c.setY(-100);
                }
                NetworkManager.connect(ip);
            }
            if (gc.getInput().getMouseX()>gc.getWidth()/2-quit.getWidth()/2 && gc.getInput().getMouseX()<gc.getWidth()/2+quit.getWidth()/2 && gc.getInput().getMouseY()>gc.getHeight()/2-quit.getHeight()/2+gc.getHeight()/3 && gc.getInput().getMouseY()>gc.getHeight()/2+quit.getHeight()/2+gc.getHeight()/3){
                sbg.enterState(0);
            }
        }
        String add = "";
        if (gc.getInput().isKeyPressed(Input.KEY_A)){
            add = "A";
        }else if (gc.getInput().isKeyPressed(Input.KEY_B)){
            add = "B";
        }else if (gc.getInput().isKeyPressed(Input.KEY_C)){
            add = "C";
        }else if (gc.getInput().isKeyPressed(Input.KEY_D)){
            add = "D";
        }else if (gc.getInput().isKeyPressed(Input.KEY_E)){
            add = "E";
        }else if (gc.getInput().isKeyPressed(Input.KEY_F)){
            add = "F";
        }else if (gc.getInput().isKeyPressed(Input.KEY_G)){
            add = "G";
        }else if (gc.getInput().isKeyPressed(Input.KEY_H)){
            add = "H";
        }else if (gc.getInput().isKeyPressed(Input.KEY_I)){
            add = "I";
        }else if (gc.getInput().isKeyPressed(Input.KEY_J)){
            add = "J";
        }else if (gc.getInput().isKeyPressed(Input.KEY_K)){
            add = "K";
        }else if (gc.getInput().isKeyPressed(Input.KEY_L)){
            add = "L";
        }else if (gc.getInput().isKeyPressed(Input.KEY_M)){
            add = "M";
        }else if (gc.getInput().isKeyPressed(Input.KEY_N)){
            add = "N";
        }else if (gc.getInput().isKeyPressed(Input.KEY_O)){
            add = "O";
        }else if (gc.getInput().isKeyPressed(Input.KEY_P)){
            add = "P";
        }else if (gc.getInput().isKeyPressed(Input.KEY_Q)){
            add = "Q";
        }else if (gc.getInput().isKeyPressed(Input.KEY_R)){
            add = "R";
        }else if (gc.getInput().isKeyPressed(Input.KEY_S)){
            add = "S";
        }else if (gc.getInput().isKeyPressed(Input.KEY_T)){
            add = "T";
        }else if (gc.getInput().isKeyPressed(Input.KEY_U)){
            add = "U";
        }else if (gc.getInput().isKeyPressed(Input.KEY_V)){
            add = "V";
        }else if (gc.getInput().isKeyPressed(Input.KEY_W)){
            add = "W";
        }else if (gc.getInput().isKeyPressed(Input.KEY_X)){
            add = "X";
        }else if (gc.getInput().isKeyPressed(Input.KEY_Y)){
            add = "Y";
        }else if (gc.getInput().isKeyPressed(Input.KEY_Z)){
            add = "Z";
        }else if (gc.getInput().isKeyPressed(Input.KEY_PERIOD)){
            add = ".";
        }else if (gc.getInput().isKeyPressed(Input.KEY_1)){
            add = "1";
        }else if (gc.getInput().isKeyPressed(Input.KEY_2)){
            add = "2";
        }else if (gc.getInput().isKeyPressed(Input.KEY_3)){
            add = "3";
        }else if (gc.getInput().isKeyPressed(Input.KEY_4)){
            add = "4";
        }else if (gc.getInput().isKeyPressed(Input.KEY_5)){
            add = "5";
        }else if (gc.getInput().isKeyPressed(Input.KEY_6)){
            add = "6";
        }else if (gc.getInput().isKeyPressed(Input.KEY_7)){
            add = "7";
        }else if (gc.getInput().isKeyPressed(Input.KEY_8)){
            add = "8";
        }else if (gc.getInput().isKeyPressed(Input.KEY_9)){
            add = "9";
        }else if (gc.getInput().isKeyPressed(Input.KEY_0)){
            add = "0";
        }
        if (!gc.getInput().isKeyDown(Input.KEY_LSHIFT) && !gc.getInput().isKeyDown(Input.KEY_RSHIFT)){
            add = add.toLowerCase();
        }
        ip += add;
        if (gc.getInput().isKeyPressed(Input.KEY_BACK) && ip.length()>0){
            ip = ip.substring(0, ip.length()-1);
        }
    }
}
