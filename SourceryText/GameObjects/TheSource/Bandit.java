package SourceryText.GameObjects.TheSource;

import SourceryText.GameObjects.Mortal;
import SourceryText.ImageOrg;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;


/**
 * A dangerous bandit!
 * Created by riley on 31-Aug-2016
 */
public class Bandit extends Mortal {
    private int followDist = 8;

    public Bandit(ImageOrg orga, Room theRoom, int xStart, int yStart) {
        super.strClass = "Bandit";
        org = orga;
        room = theRoom;
        layerName = room.makeUniqueLayerName(super.strClass);

        x = xStart;
        y = yStart;
        setHealth(15);
        org.addLayer(new Layer(new String[1][1], layerName, x, y));

        setupTimer(600); // Maybe the player should check this instead
    }


    @Override
    public void update() {
        Mortal closestGoodGuy = getClosestGoodGuy();
        if (closestGoodGuy != null) {
            if (Math.abs(x - closestGoodGuy.getX()) <= 2 && Math.abs(y - closestGoodGuy.getY()) <= 2) {
                closestGoodGuy.subtractHealth(2);
            }
            if (closestGoodGuy.getX() == getX() || closestGoodGuy.getY() == getY()) { // sprint when straight line to player
                pathToPos(followDist, closestGoodGuy.getX(), closestGoodGuy.getY());
            }
            pathToPos(followDist, closestGoodGuy.getX(), closestGoodGuy.getY());
        } else {
            System.out.println("Bandit could not find a nearest good guy :(");
        }
        setDispIcon(new SpecialText("B", new Color(255, 160, 160)));
    }

    @Override
    public void onDeath() {
        org.removeLayer(layerName);
    }
}
