/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Rooms.Room;

import java.util.Random;

/**
 *
 * @author riley
 */
public class Food extends GameObject {
    static Random rand = new Random();
    ImageOrg org;
    Room room;
    String layerName;
    int x = 0;
    int y = 0;
    String symbol = "*";

    // 1+r((MaxW, -H )-3)
    public Food(ImageOrg orga, String layerN, Room room) {
        this(orga, 1+r(67), 1+r(37), layerN, room);
    }

    public Food(ImageOrg orga, int xPos, int yPos, String layername, Room rooom) {
        super.strClass = "Food";
        room = rooom;
        layerName = layername;
        org = orga;

        boolean bad = true; // Make sure we aren't hitting any solid places
        while (bad){
            xPos = 1+r(67);
            yPos = 1+r(37);
            if (!room.isPlaceSolid(xPos, yPos)){
                bad = false;
            }
        }

        x = xPos;
        y = yPos;
        super.x = x;
        super.y = y;
        org.editLayer(symbol, layerName, y, x); // Place a char at a gooooooooood place
    }
    
    @Override
    public void update(){
        Player playo = room.getPlayer();
        if (playo.y == getY() && playo.x == getX()) {
            die();
            room.removeObject(this);
            playo.celebrate();
            if (room.storedStuff.get("Fooded") == null && room.index == 1){
                room.infoMessage(org, "Those are food.  Eating more of them is generally considered good.");
                room.storedStuff.put("Fooded", 1);
            }
            return;
        }
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public void die(){
        org.editLayer(" ", layerName, y, x);
        room.foodEaten++;
        //System.out.println("Oops, I died!\n X : " + x + "\n Y : " + y);
    }

    static int r(int max) {
        return r(max, 0);
    }
    static int r(int max, int min) {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
