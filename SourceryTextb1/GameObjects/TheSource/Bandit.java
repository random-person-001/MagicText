package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;


/**
 * A dangerous bandit!
 * Created by riley on 31-Aug-2016
 */
public class Bandit extends Mortal {
    private int followDist = 6;

    public Bandit(ImageOrg orga, Room theRoom, int xStart, int yStart) {
        super.strClass = "Bandit";
        orgo = orga;
        room = theRoom;
        layerName = room.makeUniqueLayerName(super.strClass);

        x = xStart;
        y = yStart;
        setHealth(15);
        orgo.addLayer(new Layer(new String[1][1], layerName, y, x));

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
    public void onDeath(){
        orgo.removeLayer(layerName);
    }
}
