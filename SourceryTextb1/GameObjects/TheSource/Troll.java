package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.DroppedItem;
import SourceryTextb1.GameObjects.Mortal;
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
    private int moveFrq = 20; //Higher is slower
    private DroppedItem itemOnDrop;

    public Troll(ImageOrg orga, Room theRoom, int xStart, int yStart, DroppedItem itemToDrop) {
        super.strClass = "Troll";
        orgo = orga;
        room = theRoom;
        itemOnDrop = itemToDrop;
        layerName = room.makeUniqueLayerName(super.strClass);
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
        room.removeMortal(this);
        Mortal closestGoodGuy = getClosestGoodGuy();
        boolean goodPlace = false;
        int rationality = 40000;
        while (!goodPlace) {
            int newX = x;
            int newY = y;
            System.out.println("Not good place yet");
            if (distanceTo(closestGoodGuy) < 10 && r(15)>1){ /// Most of the time go towards player
                if (r(1) == 0 && x != closestGoodGuy.getX())
                    newX += (x < closestGoodGuy.getX()) ? 1 : -1;
                else if (closestGoodGuy.getY() != y)
                    newY += (y < closestGoodGuy.getY()) ? 1 : -1;
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
        orgo.editLayer("T", layerName, 0, 0);
        room.removeMortal(this);

        if (abs(closestGoodGuy.getY() - y) <= 3 && abs(closestGoodGuy.getX() - x) <= 3) {
            closestGoodGuy.subtractHealth(3, "You know, maybe you should have listened \n when your mother told you not to \n play with trolls.");
        }
    }

    @Override
    public void onDeath(){
        orgo.removeLayer(layerName);
        System.out.println("AAAAAaaaack, a troll died.");
        //room.addObject(itemOnDrop);
    }
}
