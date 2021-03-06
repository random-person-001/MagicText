package SourceryText.GameObjects.SeaOfSurprise;

import SourceryText.GameObjects.DroppedItem;
import SourceryText.GameObjects.Mortal;
import SourceryText.ImageOrg;
import SourceryText.Layer;
import SourceryText.Rooms.Room;

import java.util.Random;

/**
 * Created by riley on 17-Jun-2016.
 */
public class SmallPiranha extends Mortal {
    private static Random rand = new Random();
    private int moveFrq = 20; //Higher is slower
    private DroppedItem itemOnDrop;

    public SmallPiranha(ImageOrg orga, Room theRoom, int xStart, int yStart, DroppedItem itemToDrop) {
        super.strClass = "SmallPiranha";
        org = orga;
        room = theRoom;
        itemOnDrop = itemToDrop;
        layerName = room.makeUniqueLayerName(super.strClass);
        x = xStart;
        y = yStart;
        setHealth(20);
        org.addLayer(new Layer(new String[1][1], layerName));
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
            if (org.getDebug())
                System.out.println("Not good place yet");
            if (r(5) > 1) { /// Most of the time go towards closest good guy
                Mortal m = getClosestGoodGuy();
                if (r(1) == 0 && x != m.getX())
                    newX += (x < m.getX()) ? 1 : -1;
                else if (m.getY() != y)
                    newY += (y < m.getY()) ? 1 : -1;
                if (org.getDebug())
                    System.out.println("Going towards good guy");
            } else if (r(8) > 1) {
                Mortal m = closestGoodGuy;
                if (r(1) == 0 && x != m.getX())
                    newX += (x < m.getX()) ? 1 : -1;
                else if (m.getY() != y)
                    newY += (y < m.getY()) ? 1 : -1;
                if (org.getDebug())
                    System.out.println("Going towards player");
            } else if (r(moveFrq) < 1) {
                if (org.getDebug())
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
                System.out.println("Piranha at x=" + x + " y=" + y + " committing suicide cuz there's a low chance it's not stuck");
                return;
            }
        }
        org.editLayer("<span color='#cc0000'>p</span>", layerName, 0, 0);
        room.addMortal(this);

        if (distanceTo(closestGoodGuy) <= 3) {
            closestGoodGuy.subtractHealth(3, "You know, maybe you should have listened \n when your mother told you not to \n play with piranhas.", "arcane");
        }
    }

    @Override
    public void onDeath() {
        org.removeLayer(layerName);
        if (org.getDebug()) {
            System.out.println("AAAAAaaaack, a piranha died.");
        }
        room.addObject(itemOnDrop);
    }

}
