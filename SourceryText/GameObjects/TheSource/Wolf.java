package SourceryText.GameObjects.TheSource;

import SourceryText.GameObjects.Mortal;
import SourceryText.ImageOrg;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;


/**
 * A dangerous troll (not the internet kind)
 * Created by riley on 16-Jun-2016.
 */
public class Wolf extends Mortal {
    private int followDist = 20;

    public Wolf(ImageOrg orga, Room theRoom, int xStart, int yStart) {
        super.strClass = "Wolf";
        org = orga;
        room = theRoom;
        layerName = room.makeUniqueLayerName(super.strClass);

        x = xStart;
        y = yStart;
        setHealth(10);
        Layer wolfLayer = new Layer(new String[1][1], layerName, x, y);
        org.addLayer(wolfLayer);

        setupTimer(550);
    }


    @Override
    public void update() {
        setDispIcon(new SpecialText("W", new Color(255, 200, 200)));
        Mortal closestGoodGuy = getClosestGoodGuy();
        if (closestGoodGuy != null) {
            if (Math.abs(x - closestGoodGuy.getX()) <= 1 && Math.abs(y - closestGoodGuy.getY()) <= 1) {
                closestGoodGuy.subtractHealth(3);
            }
            pathToPos(followDist, closestGoodGuy.getX(), closestGoodGuy.getY());
        } else {
            System.out.println("Wolf in " + room.strRoomName + " couldn't find player to hunt");
        }
    }

    @Override
    public void onDeath() {
        org.removeLayer(layerName);
        /*
        if (room.getCountOf("Wolf") == 0){
            room.splashMessage("Fhweh!  Those wolves were mean!\nHeal yourself, and brace for the road ahead.", "God");
        }
        */
    }
}
