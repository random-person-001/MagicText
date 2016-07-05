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
public class Troll extends Mortal {
    private static Random rand = new Random();
    private int moveFrq = 20; //Higher is slower
    private DroppedItem itemOnDrop;

    public Troll(ImageOrg orga, Room theRoom, int xStart, int yStart, DroppedItem itemToDrop) {
        super.strClass = "Troll";
        layerName = "trollLayer";
        orgo = orga;
        room = theRoom;
        itemOnDrop = itemToDrop;
        x = xStart;
        y = yStart;
        setHealth(20);
        if (-1 == orgo.getPosLayer(layerName)) {// Layer doesn't exist yet; add it
            System.out.println("troll layer doesn't yet exist");
            orgo.addLayer(new Layer(new String[room.roomHeight][room.roomWidth], layerName));
        }
    }

    public void setMoveFrq(int newfrq) {
        moveFrq = newfrq;
    }

    @Override
    public void update() {
        // Try to move
        orgo.editLayer(" ", layerName, y, x);
        room.removeFromObjHitMesh(x, y);
        boolean goodPlace = false;
        int rationality = 40000;
        while (!goodPlace) {
            int newX = x;
            int newY = y;
            System.out.println("Not good place yet");
            if (distanceTo(room.playo) < 10 && r(15)>1){ /// Most of the time go towards player
                if (r(1) == 0 && x != room.playo.getX())
                    newX += (x < room.playo.getX()) ? 1 : -1;
                else if (room.playo.getY() != y)
                    newY += (y < room.playo.getY()) ? 1 : -1;
                System.out.println("Going towards player");
            }else if (r(moveFrq) < 1) {
                System.out.println("Moving randomly");
                if (r(1) < 0) {
                    newX += r(2) - 1;
                } else {
                    newY += r(2) - 1;
                }
            }
            if (!room.isPlaceSolid(newX, newY)) {
                goodPlace = true;
                x = newX;
                y = newY;
            }
            rationality--;
            if (rationality < 0) { // We're stuck in a really bad spot.  Suicide is painless, so they say...
                room.removeMortal(this);
                System.out.println("Troll at x=" + x + " y=" + y + " committing suicide cuz there's a low chance it's not stuck");
                return;
            }
        }
        orgo.editLayer("T", layerName, y, x);
        room.addToObjHitMesh(x, y);

        if (abs(room.playo.y - y) <= 3 && abs(room.playo.x - x) <= 3) {
            room.playo.hurt(3, "You know, maybe you should have listened \n when your mother told you not to \n play with trolls.");
        }
        if (checkDeath()) {
            System.out.println("AAAAAaaaack, a troll died.");
            //
            // room.addObject(itemOnDrop);
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
