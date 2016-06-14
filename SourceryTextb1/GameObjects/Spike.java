/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.GameObjects.GameObject;

import java.awt.*;
import java.util.Random;

/**
 * A dangerous class, to say the least!
 * @author riley-ubuntu
 */
public class Spike extends GameObject{
    static Random rand = new Random();
    ImageOrg org;
    Room room;
    public String layerName = "spikebed";
    int x;
    int y;
    private int xvariance = 1;
    private int yvariance = 1;
    private int moveFrq = 60; //Higher is slower

    public Spike(ImageOrg orga, Room theRoom){
        this(orga, theRoom, 35, 9);
    }
    public Spike(ImageOrg orga, Room theRoom, int xStart, int yStart){
        super.strClass = "Spike";
        org = orga;
        room = theRoom;
        x = xStart;
        y = yStart;
    }

    @Override
    public void update(){  // Moves a bit when it feels the urge.
        int loc = org.getPosLayer(layerName);
        if (r(moveFrq) < 1) {
            org.editLayer(" ", loc, x, y);
            boolean goodPlace = false;
            while (!goodPlace){
                int newX = x + r(2 * xvariance) - xvariance;
                int newY = y + r(2 * yvariance) - yvariance;
                if (!room.isPlaceSolid(newX, newY)){
                    goodPlace = true;
                }
            }
            org.editLayer("^", loc, x, y);
        }else{
            org.editLayer("^", loc, x, y);
        }
        Player playo = room.getPlayer();
        if (playo.y == x && playo.x == y) {
            if (room.storedStuff.get("Spiked") == null && room.index == 3){
                room.infoMessage(org, "Have you considered that stepping on a spike, as you just did, is detrimental?");
                room.storedStuff.put("Spiked", 1);
            }
            room.foodEaten -= 5;
            room.playo.hurt(3);
        }
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
