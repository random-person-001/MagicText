package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.DroppedItem;
import SourceryTextb1.GameObjects.Item;
import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

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
        orgo = room.org;
        x = xStart;
        y = yStart;
        setHealth(8);
        orgo.addLayer(new Layer(new String[1][1], layerName, y, x));

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
    public void onDeath(){
        orgo.removeLayer(layerName);
    }
}
