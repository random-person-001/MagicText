package SourceryText.GameObjects.TheSource;

import SourceryText.GameObjects.Mortal;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;


/**
 * A dangerous troll (not the internet kind)
 * Created by riley on 16-Jun-2016.
 */
public class Spider extends Mortal {
    private int followDist = 20;

    public Spider(Room theRoom, int xStart, int yStart) {
        super.strClass = "Spider";
        room = theRoom;
        layerName = room.makeUniqueLayerName(super.strClass);
        org = room.org;
        x = xStart;
        y = yStart;
        setHealth(8);
        org.addLayer(new Layer(new String[1][1], layerName, x, y));

        setupTimer(750);
    }

    @Override
    public void update() {
        Mortal closestGoodGuy = getClosestGoodGuy();
        if (closestGoodGuy != null) {
            if (Math.abs(x - closestGoodGuy.getX()) <= 1 && Math.abs(y - closestGoodGuy.getY()) <= 1) {
                closestGoodGuy.subtractHealth(2);
            }
            pathToPos(followDist, closestGoodGuy.getX(), closestGoodGuy.getY());
        } else {
            System.out.println("Spider could not find a nearest good guy :(");
        }
        setDispIcon(new SpecialText("S", new Color(255, 100, 90)));
    }

    @Override
    public void onDeath() {
        org.removeLayer(layerName);
    }
}
