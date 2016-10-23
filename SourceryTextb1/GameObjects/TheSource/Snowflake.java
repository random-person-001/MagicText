package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.GameObject;
import SourceryTextb1.*;
import SourceryTextb1.Rooms.Room;

import java.util.Random;

/**
 * Created by Jared on 10/22/2016.
 */
public class Snowflake extends GameObject {

    private String layerName;

    public Snowflake(ImageOrg orgSet, Room creator, int setX, int setY){
        strClass = "snowflake";

        room = creator;
        orgo = orgSet;
        x = setX;
        y = setY;
        layerName = creator.makeUniqueLayerName(strClass);
        Layer flakeLayer = new Layer(new String[1][1], layerName, y, x);

        flakeLayer.setSpecTxt(0, 0, new SpecialText("."));
        orgo.addLayer(flakeLayer);

        setupTimer(200);
    }

    public void update(){
        x--;
        y++;
        if (x < 0){
            room.removeObject(this);
        } else {
            orgo.getLayer(layerName).setPos(y, x);
        }
    }

    public void selfCleanup(){
        orgo.removeLayer(layerName);
    }

}
