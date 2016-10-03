package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;


/**
 * A dangerous troll (not the internet kind)
 * Created by riley on 16-Jun-2016.
 */
public class Wolf extends Mortal {
    private int followDist = 20;

    public Wolf(ImageOrg orga, Room theRoom, int xStart, int yStart) {
        super.strClass = "Wolf";
        orgo = orga;
        room = theRoom;
        layerName = room.makeUniqueLayerName(super.strClass);

        x = xStart;
        y = yStart;
        setHealth(10);
        orgo.addLayer(new Layer(new String[1][1], layerName, y, x));

        setupTimer(550);
    }


    @Override
    public void update() {
        orgo.editLayer("W", layerName, 0, 0);
        Mortal closestGoodGuy = getClosestGoodGuy();
        if (Math.abs(x - closestGoodGuy.getX()) <= 1 && Math.abs(y - closestGoodGuy.getY()) <= 1){
            closestGoodGuy.subtractHealth(3);
        }
        pathToPos(followDist, closestGoodGuy.getX(), closestGoodGuy.getY());
    }

    @Override
    public void onDeath(){
        orgo.removeLayer(layerName);
        if (room.getCountOf("Wolf") == 0){
            room.splashMessage("Fhweh!  Those wolves were mean!\nHeal yourself, and brace for the road ahead.", "God");
        }
    }
}
