package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

import java.util.Random;

import static java.lang.StrictMath.abs;


/**
 * A dangerous troll (not the internet kind)
 * Created by riley on 16-Jun-2016.
 */
public class PathingObj extends Mortal {
    private static Random rand = new Random();
    private int moveFrq = 20; //Higher is slower

    int followDist = 6;

    public PathingObj(ImageOrg orga, Room theRoom, int xStart, int yStart) {
        super.strClass = "PathingObj";
        layerName = "pathLayer";
        orgo = orga;
        room = theRoom;
        x = xStart;
        y = yStart;
        setHealth(20);
        orgo.addLayer(new Layer(new String[1][1], layerName));
    }

    public void setMoveFrq(int newfrq) {
        moveFrq = newfrq;
    }

    @Override
    public void update() {
        // Try to move
        //orgo.editLayer(" ", layerName, y, x);
        if (withinDist(room.playo.getX(), room.playo.getY(), x, y, followDist)){
            createPathTo(room.playo.getX(), room.playo.getY(), followDist);
            for (PathPoint pt : pathPts){
                if (pt.getCntr() == followDist - 1){
                    x = pt.getX();
                    y = pt.getY();
                }
            }
        }

        orgo.editLayer("O", layerName, 0, 0);
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
