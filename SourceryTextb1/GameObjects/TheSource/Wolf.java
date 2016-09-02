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
        if (Math.abs(x - room.playo.getX()) <= 1 && Math.abs(y - room.playo.getY()) <= 1){
            room.playo.subtractHealth(3);
        }
        pathToPos(followDist, room.playo.getX(), room.playo.getY());
        orgo.editLayer("W", layerName, 0, 0);
    }

    @Override
    protected void onDeath(){
        orgo.removeLayer(layerName);
        if (room.getCountOf("Wolf") == 0){
            Room.FlavorText ft = null;
            //ft = new Room.FlavorText("Fhweh!  Those wolves were mean!\nHeal yourself, and brace for the bandits ahead.", "God");

            room.textBox(ft);
        }
    }
}
