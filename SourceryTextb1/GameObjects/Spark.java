package SourceryTextb1.GameObjects;

/** A Spark
 * Created by Jared on 6/9/2016.
 */

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

public class Spark extends GameObject{
    private String layerName;
    private int orientation = 0;

    public Spark (ImageOrg org, Room theRoom, Layer place, int setX, int setY, int setOr){
        strClass = "HUD";
        orgo = org;
        room = theRoom;
        layerName = place.getName();

        x = setX;
        y = setY;

        orientation = setOr;
    }

    public void update(){
        orgo.editLayer(" ", layerName, y, x);

        switch(orientation){
            case 0:
                y -= 1;
                break;
            case 1:
                y += 1;
                break;
            case 2:
                x -= 1;
                break;
            case 3:
                x += 1;
                break;
            default:
                break;
        }

        if (x%2 == 0 ^ y%2 == 0) { //A really ~~~lazy~~~ clever way to alternate between two characters ('^' means XOR)
            orgo.editLayer("x", layerName, y, x);
        } else {
            orgo.editLayer("+", layerName, y, x);
        }

        if (room.isPlaceSolid(x,y)){
            room.removeObject(this);
        }
    }
}
