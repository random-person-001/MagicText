package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.DroppedItem;
import SourceryTextb1.GameObjects.Item;
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
public class PolarBear extends Mortal {
    private int followDist = 16;
    private boolean hasKey = false;

    private int originalX;
    private int originalY;

    public PolarBear(ImageOrg orga, Room theRoom, int xStart, int yStart, boolean giveKey) {
        super.strClass = "PolarBear";
        orgo = orga;
        room = theRoom;
        layerName = room.makeUniqueLayerName(super.strClass);

        x = xStart;
        y = yStart;
        setHealth(50);
        orgo.addLayer(new Layer(new String[1][1], layerName, y, x));

        hasKey = giveKey;
        originalX = xStart;
        originalY = yStart;

        setupTimer(150); // Maybe the player should check this instead
    }


    int chargeTime = 0;
    int chargeRechargeTime = 0;
    int biteCooldown = 0;

    @Override
    public void update() {
        if (chargeTime > 0 || getTime() >= 450) {
            Mortal closestGoodGuy = getClosestGoodGuy();
            int dist = Math.abs(x - closestGoodGuy.getX()) + Math.abs(y - closestGoodGuy.getY());
            if (dist <= 1 && biteCooldown <= 0) {
                closestGoodGuy.subtractHealth(6);
                closestGoodGuy.slowedTimer = 300;
                biteCooldown = 18;
            }
            if (biteCooldown > 0) biteCooldown -= (chargeTime == 0) ? 1 : 3;
            if (dist > followDist || chargeRechargeTime > 5) {
                chargeTime = 40;
                chargeRechargeTime = 0;
            }
            pathToPos(followDist, closestGoodGuy.getX(), closestGoodGuy.getY());
            if (chargeTime > 0) chargeTime--;
            else chargeRechargeTime++;
            resetTime();
            setDispIcon(new SpecialText("B", new Color(235, 215, 255)));
        }
    }

    @Override
    public void onDeath() {
        if (hasKey) {
            Item keyDrop = new Item("Witch Hut Key", "With polar bears runnin'\n around, it's a good idea\n to lock yourself in a hut.", "item");
            DroppedItem theDrop = new DroppedItem(room, "You found a key!", keyDrop, originalX, originalY);
            room.addObject(theDrop);
        }
        orgo.removeLayer(layerName);
    }
}
