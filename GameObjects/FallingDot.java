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
 * A proof-of-concept class for eventual character implementation.
 * @author 119184
 */
public class FallingDot extends GameObject{
    static Random rand = new Random();
    ImageOrg org;
    Room room;
    public String layerName = "dotLand";
    int x;
    int y;
    int xvariance = 2;
    int yvariance = 1;

    public FallingDot(ImageOrg orga, Room theRoom){
        this(orga, theRoom, 35, 9);
    }
    public FallingDot(ImageOrg orga, Room theRoom, int xStart, int yStart){
        super.strClass = "FallingDot";
        org = orga;
        room = theRoom;
        x = xStart;
        y = yStart;
    }
    
    @Override
    public void update(){
        int loc = org.getPosLayer("dotLand");
        org.editLayer(" ", loc, y, x);

        x+= r(2*xvariance)-xvariance;
        y+= r(2*yvariance)-yvariance;

        org.editLayer("O", loc, y, x);
    }
    
    public void reset(){
        x = x+1;
        y = 0;
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
