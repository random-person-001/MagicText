package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;

/**
 * Created by Jared on 22-Nov-16.
 */
public class Ghost extends Mortal {
    private int followDist = 1;

    private boolean isRevealed = false;

    public Ghost(Room theRoom, int xStart, int yStart) {
        super.strClass = "Ghost";
        orgo = theRoom.org;
        room = theRoom;
        layerName = room.makeUniqueLayerName(super.strClass);

        x = xStart;
        y = yStart;
        setHealth(25);
        orgo.addLayer(new Layer(new String[1][1], layerName, y, x));

        setupTimer(300); // Maybe the player should check this instead
    }

    int attackCooldown = 0;

    @Override
    public void update() {
        Mortal closestGoodGuy = getClosestGoodGuy();
        if (closestGoodGuy != null) {
            int dist = Math.abs(x - closestGoodGuy.getX()) + Math.abs(y - closestGoodGuy.getY());
            if (dist <= 1 && attackCooldown <= 0) {
                closestGoodGuy.subtractHealth(5);
                attackCooldown = 5;
            }
            if (dist <= 5 && followDist == 1) {
                followDist = 35;
                isRevealed = true;
            }
            if (getHealth() < maxHealth) isRevealed = true;
            if (attackCooldown > 0) attackCooldown--;
            pathToPos(followDist, closestGoodGuy.getX(), closestGoodGuy.getY());
        }
        if (isRevealed)
            setDispIcon(new SpecialText("G", new Color(217, 184, 184)));
        else
            setDispIcon(new SpecialText(" "));
    }

    @Override
    public void onDeath() {
        orgo.removeLayer(layerName);
    }
}
