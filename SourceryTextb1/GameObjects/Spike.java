/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * A dangerous class, to say the least!
 * @author riley-ubuntu
 */
public class Spike extends Mortal{
    static Random rand = new Random();
    private int xvariance = 1;
    private int yvariance = 1;
    private int moveFrq = 60; //Higher is slower

    public Spike(ImageOrg orga, Room theRoom){
        this(orga, theRoom, 35, 9);
    }
    public Spike(ImageOrg orga, Room theRoom, int xStart, int yStart){
        super.strClass = "Spike";
        layerName = "spikebed";
        orgo = orga;
        room = theRoom;
        x = xStart;
        y = yStart;
        setHealth(12);
        if (-1 == orga.getPosLayer(layerName)) {// Layer doesn't exist yet; add it
            orgo.addLayer(new Layer(new String[y+10][x+10], layerName));
        }
    }

    public void setMoveFrq(int newfrq){
        moveFrq = newfrq;
    }

    @Override
    public void update(){  // Moves a bit when it feels the urge.
        if (r(moveFrq) < 1) {
            orgo.editLayer(" ", layerName, y, x);
            room.makePlaceNotSolid(x,y);
            boolean goodPlace = false;
            while (!goodPlace){
                int newX = x + r(2 * xvariance) - xvariance;
                int newY = y + r(2 * yvariance) - yvariance;
                if (!room.isPlaceSolid(newX, newY)){
                    goodPlace = true;
                }
            }
            orgo.editLayer("^", layerName, y, x);
            room.makePlaceSolid(x,y);
        }else{
            orgo.editLayer("^", layerName, y, x);
        }
        if (r(moveFrq/2) < 1) { // Cast a spell randomly!  Dangerous, eh?
            room.addObject(new Spell(orgo, room, room.playo.castingLayer, x, y, r(3), "Spark"));
        }

        if (abs(room.playo.y - y) <= 2 && abs(room.playo.x - x) <= 2) {
            if (room.storedStuff.get("Spiked") == null && room.index == 3){  // Legacy
                room.infoMessage(orgo, "Have you considered that stepping on a spike, as you just did, is detrimental?");
                room.storedStuff.put("Spiked", 1);
            }
            room.foodEaten -= 5;
            room.playo.hurt(3);
        }
        if (checkDeath()){
            System.out.println("AAAAAaaaack, a spike died.");
            DroppedItem flameSpell = new DroppedItem(room, orgo, "You got a new spell: Flame!", "Flame", "DropFlameLayer", "!",x,y);
            room.addObject(flameSpell);
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
