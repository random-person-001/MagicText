package SourceryTextb1.GameObjects.SeaOfSurprise;

import SourceryTextb1.GameObjects.DroppedItem;
import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

import java.util.Random;

import static java.lang.StrictMath.abs;

/**
 * Created by riley on 17-Jun-2016.
 */
public class SmallPiranha extends Mortal{
    private static Random rand = new Random();
    private int moveFrq = 20; //Higher is slower
    private DroppedItem itemOnDrop;

    public SmallPiranha(ImageOrg orga, Room theRoom, int xStart, int yStart, DroppedItem itemToDrop) {
        super.strClass = "SmallPiranha";
        layerName = "piranhaLayer";
        orgo = orga;
        room = theRoom;
        itemOnDrop = itemToDrop;
        x = xStart;
        y = yStart;
        setHealth(20);
        if (-1 == orgo.getPosLayer(layerName)) {// Layer doesn't exist yet; add it
            System.out.println("piranha layer doesn't yet exist");
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
        room.makePlaceNotSolid(x, y);
        boolean goodPlace = false;
        int rationality = 40000;
        while (!goodPlace) {
            int newX = x;
            int newY = y;
            if (orgo.getDebug())
                System.out.println("Not good place yet");
            if (r(5)>1){ /// Most of the time go towards closest good guy
                Mortal m = getClosestGoodGuy();
                if (r(1) == 0 && x != m.getX())
                    newX += (x < m.getX()) ? 1 : -1;
                else if (m.getY() != y)
                    newY += (y < m.getY()) ? 1 : -1;
                if (orgo.getDebug())
                    System.out.println("Going towards good guy");
            } else if (r(8)>1){
                Mortal m = room.playo;
                if (r(1) == 0 && x != m.getX())
                    newX += (x < m.getX()) ? 1 : -1;
                else if (m.getY() != y)
                    newY += (y < m.getY()) ? 1 : -1;
                if (orgo.getDebug())
                    System.out.println("Going towards player");
            }
            else if (r(moveFrq) < 1) {
                if (orgo.getDebug())
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
        orgo.editLayer("p", layerName, y, x);
        room.makePlaceSolid(x, y);

        if (abs(room.playo.getY() - y) <= 3 && abs(room.playo.getX() - x) <= 3) {
            room.playo.hurt(3, "You know, maybe you should have listened \n when your mother told you not to \n play with piranhas.");
        }
        if (checkDeath()) {
            if (orgo.getDebug())
                System.out.println("AAAAAaaaack, a piranha died.");
            room.addObject(itemOnDrop);
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
