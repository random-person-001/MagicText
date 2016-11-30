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
public class CaveYeti extends Mortal {
    private int followDist = 1;

    private int originalX;
    private int originalY;

    public CaveYeti(ImageOrg orga, Room theRoom, int xStart, int yStart) {
        super.strClass = "CaveYeti";
        orgo = orga;
        room = theRoom;
        layerName = room.makeUniqueLayerName(super.strClass);

        x = xStart;
        y = yStart;
        setHealth(30);
        orgo.addLayer(new Layer(new String[1][1], layerName, y, x));

        originalX = xStart;
        originalY = yStart;

        setupTimer(200); // Maybe the player should check this instead
    }

    int attackCooldown = 0;

    @Override
    public void update() {
        Mortal closestGoodGuy = getClosestGoodGuy();
        int dist = Math.abs(x - closestGoodGuy.getX()) + Math.abs(y - closestGoodGuy.getY());
        if (dist <= 1 && attackCooldown <= 0) {
            closestGoodGuy.subtractHealth(6);
            attackCooldown = 5;
        }
        if (dist <= 6 && followDist == 1) followDist = 35;
        if (attackCooldown > 0) attackCooldown--;
        pathToPos(followDist, closestGoodGuy.getX(), closestGoodGuy.getY());
        setDispIcon(new SpecialText("Y", new Color(245, 235, 255)));
    }

    @Override
    public void onDeath() {
        orgo.removeLayer(layerName);
    }
}
