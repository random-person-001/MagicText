package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;

/**
 * He steals an item while talking!
 * Created by riley on 05-Dec-2016.
 */
public class Thief extends Mortal {
    private int followDist = 1;
    private boolean hostile = false;
    private int attackCooldown = 0;

    public Thief(ImageOrg org, Room theRoom, int xStart, int yStart) {
        strClass = "Thief";
        this.org = org;
        room = theRoom;
        layerName = room.makeUniqueLayerName(strClass);

        x = xStart;
        y = yStart;
        setHealth(30);
        org.addLayer(new Layer(new String[1][1], layerName, y, x));

        setupTimer(200); // Maybe the player should check this instead
    }

    @Override
    public void update() {
        if (getHealth() < 24){
            hostile = true;
        }
        if (hostile) {
            Mortal closestGoodGuy = getClosestGoodGuy();
            int dist = Math.abs(x - closestGoodGuy.getX()) + Math.abs(y - closestGoodGuy.getY());
            if (dist <= 2 && attackCooldown <= 0) {
                closestGoodGuy.subtractHealth(2);
                attackCooldown = 3;
            }
            if (dist <= 6 && followDist == 1) followDist = 35;
            if (attackCooldown > 0) attackCooldown--;
            pathToPos(followDist, closestGoodGuy.getX(), closestGoodGuy.getY());
            setDispIcon(new SpecialText("T", new Color(126, 36, 39)));
        }
        else {
            if (r(100) > 25 && room.isPlaceSolid(x+1, y)){
                x++;
            }
            else if (r(100) > 33 && room.isPlaceSolid(x-1, y)){
                x--;
            }
            else if (r(100) > 50 && room.isPlaceSolid(x, y+1)){
                y++;
            }
            else if (room.isPlaceSolid(x, y-1)){
                y--;
            }
            setDispIcon(new SpecialText("t", new Color(0, 255, 138)));
            org.getLayer(layerName).setX(x);
            org.getLayer(layerName).setY(y);
        }
    }

    @Override
    public void onDeath() {
        org.removeLayer(layerName);
    }
}
