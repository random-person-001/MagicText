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

    int followDist = 30;

    public PathingObj(ImageOrg orga, Room theRoom, int xStart, int yStart) {
        super.strClass = "PathingObj";
        layerName = "pathLayer";
        orgo = orga;
        room = theRoom;
        x = xStart;
        y = yStart;
        setHealth(20);
        orgo.addLayer(new Layer(new String[1][1], layerName, y, x));

        setupTimer(750);
    }

    public void setMoveFrq(int newfrq) {
        moveFrq = newfrq;
    }

    @Override
    public void update() {
        // Try to move
        orgo.editLayer("O", layerName, 0, 0);
        long nanoLast = System.nanoTime();
        if (withinDist(room.playo.getX(), room.playo.getY(), x, y, followDist * 20)) {
            int stepsNeeded = createPathTo(room.playo.getX(), room.playo.getY(), followDist);
            //System.out.println(stepsNeeded);
            for (PathPoint pt : pathPts) {
                if (pt.getCntr() == stepsNeeded - 1 && pt.getCntr() != 0 && abs(pt.getX() - x) <= 1 && abs(pt.getY() - y) <= 1) {
                    x = pt.getX();
                    y = pt.getY();
                    orgo.getLayer(orgo.getPosLayer(layerName)).setPos(y, x);
                    break;
                }
            }
        }
        System.out.println(String.format("Time to complete: %1$d ms", (System.nanoTime() - nanoLast) / 1000000));

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
