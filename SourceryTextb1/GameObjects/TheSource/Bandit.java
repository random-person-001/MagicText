package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;


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
        if (Math.abs(x - room.playo.getX()) <= 2 && Math.abs(y - room.playo.getY()) <= 2){
            room.playo.subtractHealth(2);
        }
        if (room.playo.getX() == getX() || room.playo.getY() == getY()){ // sprint when straight line to player
            pathToPos(followDist, room.playo.getX(), room.playo.getY());
        }
        pathToPos(followDist, room.playo.getX(), room.playo.getY());
        orgo.editLayer("<span style='color:#990000'>B</span>", layerName, 0, 0); // with hex
    }

    @Override
    protected void onDeath(){
        orgo.removeLayer(layerName);
    }
}
