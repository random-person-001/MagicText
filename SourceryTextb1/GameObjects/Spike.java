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

/**
 * A dangerous class, to say the least!
 * @author riley-ubuntu
 */
public class Spike extends Mortal{
    static Random rand = new Random();
    private int xvariance = 1;
    private int yvariance = 1;
    private int moveFrq = 60; //Higher is slower

    public Spike(ImageOrg orga, Room theRoom, int xStart, int yStart){
        super.strClass = "Spike";
        layerName = "spikebed";
        orgo = orga;
        room = theRoom;
        x = xStart;
        y = yStart;
        setHealth(12);
        if (-1 == orgo.getPosLayer(layerName)) {// Layer doesn't exist yet; add it
            System.out.println("Spikebed doesn't yet exist");
            orgo.addLayer(new Layer(new String[room.roomHeight][room.roomWidth], layerName));
        }
        setupTimer(100);
    }

    public void setMoveFrq(int newfrq){
        moveFrq = newfrq;
    }

    @Override
    public void update(){  // Moves a bit when it feels the urge.

        if (r(moveFrq/2) < 1) { // Cast a spell randomly!  Dangerous, eh?
            room.addObject(new Spell(orgo, room, room.playo.castingLayer, x, y, r(3), "Spark"));
        }

        orgo.editLayer("^", layerName, y, x);

        if (distanceTo(room.playo) < 3) {
            /*
            if (room.storedStuff.get("Spiked") == null && room.index == 3){  // Legacy
                room.infoMessage(orgo, "Have you considered that stepping on a spike, as you just did, is detrimental?");
                room.storedStuff.put("Spiked", 1);
            }
            room.foodEaten -= 5;
            */
            room.playo.subtractHealth(attack, "You know, maybe you should have listened \n when your mother told you not to \n step on spikes.");
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
