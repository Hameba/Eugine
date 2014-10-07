package com.duckblade.eugine.server;

import com.duckblade.eugine.api.Floor;
import com.duckblade.eugine.api.Projectile;
import com.duckblade.eugine.api.Squishy;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        NetworkManager.startServer();
        while (NetworkManager.isRunning()){
            try {
                Thread.sleep(30);
                runLogicUpdate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void runLogicUpdate() {
        for (Squishy c : VH.arena.squishies){
            for (Iterator<Projectile> i = c.getProjectiles().iterator();i.hasNext();){
                Projectile p = i.next();
                if (p.getX()<0 || p.getX()>800){
                    i.remove();
                }
                if (p.getLeft()){
                    loop: for (int j = 0; j < 6; j++) {
                        for (Floor a : Floor.getFloors()){
                            if (p.getY()<a.getY()+8){
                                if (p.getY()+16>a.getY()){
                                    if (p.getX()==a.getX()+8){
                                        i.remove();
                                        break loop;
                                    }
                                }
                            }
                        }
                        p.setX(p.getX()-1);
                    }
                }else{
                    loop: for (int j = 0; j < 6; j++) {
                        for (Floor a : Floor.getFloors()){
                            if (p.getY()<a.getY()+8){
                                if (p.getY()+16>a.getY()){
                                    if (p.getX()+16==a.getX()){
                                        i.remove();
                                        break loop;
                                    }
                                }
                            }
                        }
                        p.setX(p.getX()+1);
                    }
                }
            }
            if (c.deadFrames!=-1){
                c.deadFrames++;
                c.setX(-100);
                c.setY(-100);
                if (c.deadFrames>=90){
                    c.lives--;
                    if (c.lives>0){
                        c.deadFrames=-1;
                        c.ammo = 15;
                        c.setHealth(c.getMaxHealth());
                        c.setX(-25);
                        c.setY(0);
                        c.risingFrames = -1;
                        c.frames = -1;
                    }
                }
                continue;
            }else{
                if (c.getX()<-25){
                    c.setX(-25);
                }
                if (c.getX()>743){
                    c.setX(743);
                }
                if (c.getY()<0){
                    c.setY(0);
                }
                if (c.getY()>567){
                    c.deadFrames = 0;
                    c.setHealth(c.getMaxHealth());
                    c.setY(567);
                }
            }
            if (c.risingFrames!=-1) {
                boolean notUp = false;
                if (c.risingFrames < 12) {
                    for (int i = 0; i < 16; i++) {
                        for (Floor a : Floor.getFloors()) {
                            if (c.getY() == a.getY() + 8) {
                                if (c.getX() + 57 < a.getX()) {
                                    continue;
                                }
                                if (c.getX() + 25 > a.getX() + 8) {
                                    continue;
                                }
                                notUp = true;
                            }
                        }
                        if (!notUp){
                            c.setY(c.getY()-1);
                        }else{
                            break;
                        }
                    }
                }
                if (notUp) c.risingFrames =-1;
                else c.risingFrames += 1;
                if (Floor.isOnFloor(c)) {
                    c.risingFrames = -1;
                }
            }
            for (Squishy c2 : VH.arena.squishies){
                if (c.equals(c2)){
                    continue;
                }
                if (c.frames==0 && (c.getImageInt()==2 || c.getImageInt()==3)){
                    if (c.getY()<c2.getY()+32 && c.getY()+32>c2.getY()){
                        if (c.isFacingLeft()){
                            if (c.getX()<=c2.getX()+82 && c.getX()+82-25>c2.getX() && c.getY()<=c2.getY()+32 && c.getY()+32>c2.getY()){
                                c2.damage(c.getMeleeDamage(), c);
                            }
                        }else{
                            if (c.getX()<=c2.getX()+82 && c.getX()+82+25>c2.getX() && c.getY()<=c2.getY()+32 && c.getY()+32>c2.getY()){
                                c2.damage(c.getMeleeDamage(), c);
                            }
                        }
                    }
                }
                for (Iterator<Projectile> i = c.getProjectiles().iterator();i.hasNext();){
                    Projectile p = i.next();
                    if (p.getX()<c2.getX()+82 && p.getX()+16>c2.getX() && p.getY()<c2.getY()+32 && p.getY()+16>c2.getY()){
                        c2.damageRanged(c.getRangedDamage(), c);
                        i.remove();
                    }
                }
                if (c2.getHealth()<=0){
                    c2.deadFrames = 0;
                    c2.setHealth(c2.getMaxHealth());
                }
            }
            if (c.frames!=-1){
                c.frames++;
            }
            if (c.frames>2){
                c.setImageInt(c.getImageInt()+2);
                if (c.getImageInt()==8 || c.getImageInt()==9){
                    c.frames=-1;
                    c.setImageInt(c.getImageInt()%8);
                }else{
                    c.frames=0;
                }
            }
            for (int i = 0; i < 8; i++) {
                if (!Floor.isOnFloor(c)){
                    c.setY(c.getY()+1);
                }
            }
        }
    }

}
