package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.*;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A character that steals an item while talking!
 * If you provoke him, he'll attack you back, and if he dies, he drops all the items he stole.
 * Created by riley on 05-Dec-2016.
 */
public class Thief extends Mortal {
    private String itemTypeToSteal = "spell"; // one of: 'spell' 'equip' 'item' 'any'
    private int followDist = 35;
    private boolean hostile = false;
    private int attackCooldown = 0;
    private List<Item> stolenItems = new ArrayList<>();

    public Thief(ImageOrg org, Room theRoom, int xStart, int yStart) {
        strClass = "Thief";
        this.org = org;
        room = theRoom;
        layerName = room.makeUniqueLayerName(strClass);
        room.addInspectable(this);

        x = xStart;
        y = yStart;
        setHealth(30);
        org.addLayer(new Layer(new String[1][1], layerName, x, y));

        setupTimer(200);
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
            if (attackCooldown > 0) attackCooldown--;
            pathToPos(followDist, closestGoodGuy.getX(), closestGoodGuy.getY());
            setDispIcon(new SpecialText("T", new Color(126, 36, 39)));
        }
        else if (getTime() > 4*1000){
            resetTime();
            room.removeFromObjHitMesh(x,y);
            if (r(100) > 25 && !room.isPlaceSolid(x+1, y)){
                x++;
            }
            else if (r(100) > 33 && !room.isPlaceSolid(x-1, y)){
                x--;
            }
            else if (r(100) > 50 && !room.isPlaceSolid(x, y+1)){
                y++;
            }
            else if (!room.isPlaceSolid(x, y-1)){
                y--;
            }
            else {
                System.out.println("[Bandit] Couldn't move - surrounded by solids!");
            }
            room.addToObjHitMesh(x,y);
            setDispIcon(new SpecialText("t", new Color(0, 255, 138)));
            org.getLayer(layerName).setY(y);
            org.getLayer(layerName).setX(x);
        }
    }

    @Override
    public void onInspect(Player inspector){
        room.compactTextBox("Hi there!  You must be a newcomer in\n these parts. Let me give you some advice:\n red things are evil. Be ruthless.",
                "Johnathan",  false, inspector.getUsername());
        Item stolen = inspector.removeRandomItem(itemTypeToSteal);
        if (stolen != null) {
            stolenItems.add(stolen);
        }
        else {
            System.out.println("The player didn't have anything left to steal of type " + itemTypeToSteal + "!");
        }
    }

    @Override
    public void onDeath() {
        org.removeLayer(layerName);
        for (Item item: stolenItems) {
            room.addObject(new DroppedItem(room, "You recovered your " + item.getName() + " " +
                    (itemTypeToSteal.equals("equip") ? "equipment" : itemTypeToSteal) +
                    "\n from the corpse of the thief.", item, x, y));
        }
    }
}
