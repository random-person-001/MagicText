package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

import java.util.Random;


/**
 * A dangerous troll (not the internet kind)
 * Created by riley on 16-Jun-2016.
 */
public class Spider extends Mortal {
    private static Random rand = new Random();
    private int moveFrq = 20; //Higher is slower

    int followDist = 20;

    public Spider(ImageOrg orga, Room theRoom, int xStart, int yStart) {
        super.strClass = "Spider";
        layerName = "spiderLayer";
        orgo = orga;
        room = theRoom;
        x = xStart;
        y = yStart;
        setHealth(8);
        orgo.addLayer(new Layer(new String[1][1], layerName, y, x));

        setupTimer(750);
    }

    public void setMoveFrq(int newfrq) {
        moveFrq = newfrq;
    }

    @Override
    public void update() {
        // Try to move
        orgo.editLayer("S", layerName, 0, 0);
        //long nanoLast = System.nanoTime();
        if (Math.abs(x - room.playo.getX()) <= 1 && Math.abs(y - room.playo.getY()) <= 1){
            room.playo.subtractHealth(2);
        }
        pathToPos(followDist, room.playo.getX(), room.playo.getY());

        if (checkDeath()){
            orgo.removeLayer(layerName);
        }
        //System.out.println(String.format("Time to complete: %1$d ms", (System.nanoTime() - nanoLast) / 1000000));

        orgo.editLayer("S", layerName, 0, 0);
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
