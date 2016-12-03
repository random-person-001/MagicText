package SourceryTextb1.GameObjects.ForestOfFondant;

import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;

/**
 * Created by riley on 02-Dec-2016.
 */
public class Alligator extends Mortal {
    private boolean isFollowing = false;
    private int attackCooldown = 0;
    private int followDist = 1;

    public Alligator (Room theRoom, int xStart, int yStart) {
        super.strClass = "Alligator";
        org = theRoom.org;
        room = theRoom;
        layerName = room.makeUniqueLayerName(super.strClass);

        x = xStart;
        y = yStart;
        setHealth(27);
        org.addLayer(new Layer(new String[1][1], layerName, y, x));

        setupTimer(200);
    }

    @Override
    public void update() {
        Mortal closestGoodGuy = getClosestGoodGuy();
        if (closestGoodGuy != null) {
            int dist = Math.abs(x - closestGoodGuy.getX()) + Math.abs(y - closestGoodGuy.getY());
            if (dist <= 1 && attackCooldown <= 0) {
                closestGoodGuy.subtractHealth(7);
                attackCooldown = 4;
            }
            if ((dist <= 3 && followDist == 1) || getHealth() < maxHealth) {
                followDist = 25;
                isFollowing = true;
            }
            if (attackCooldown > 0) attackCooldown--;
            pathToPos(followDist, closestGoodGuy.getX(), closestGoodGuy.getY());
        }
        if (isFollowing)
            setDispIcon(new SpecialText("A", new Color(217, 58, 0)));
        else
            setDispIcon(new SpecialText("a", new Color(75, 19, 0)));
    }

    @Override
    public void onDeath() {
        org.removeLayer(layerName);
    }
}
