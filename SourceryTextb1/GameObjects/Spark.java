package SourceryTextb1.GameObjects;

/** A Spark
 * Created by Jared on 6/9/2016.
 */

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

public class Spark extends GameObject{
    String layerName;
    int orientation = 0;

    public Spark (ImageOrg org, Room theRoom, Layer place, int setX, int setY, int setOr){
        super.strClass = "HUD";
        orgo = org;
        room = theRoom;
        layerName = place.getName();

        super.x = setX;
        super.y = setY;

        orientation = setOr;
    }

    public void update(){
        int drawTo = orgo.getPosLayer(layerName);
        orgo.editLayer(" ", drawTo, y, x);

        switch(orientation){
            case 0:
                super.y -= 1;
                break;
            case 1:
                super.y += 1;
                break;
            case 2:
                super.x -= 1;
                break;
            case 3:
                super.x += 1;
                break;
            default:
                break;
        }

        if (x%2 == 0 ^ y%2 == 0) { //A really lazy way to alternate between two characters ('^' means XOR)
            orgo.editLayer("x", drawTo, y, x);
        } else {
            orgo.editLayer("+", drawTo, y, x);
        }

        if (room.isPlaceSolid(x,y)){
            room.removeObject(this);
        }
    }
}
