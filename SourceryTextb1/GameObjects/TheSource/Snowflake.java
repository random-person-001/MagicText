package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.GameObject;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.util.Random;

/**
 * Created by Jared on 10/22/2016.
 */
public class Snowflake extends GameObject {

    private String layerName;

    public Snowflake(ImageOrg orgSet, Room creator, int setX, int setY) {
        strClass = "Snowflake";

        room = creator;
        org = orgSet;
        x = setX;
        y = setY;
        layerName = creator.makeUniqueLayerName(strClass);
        Layer flakeLayer = new Layer(new String[1][1], layerName, y, x);

        flakeLayer.setSpecTxt(0, 0, new SpecialText("."));
        org.addLayer(flakeLayer);

        setupTimer(175);
    }

    public void update() {
        x--;
        y++;
        if (x < 0) {
            Random randal = new Random();
            y = 0;
            x = randal.nextInt(room.roomWidth);
        }
        Layer iconLayer = org.getLayer(layerName);
        if (iconLayer != null) iconLayer.setPos(y, x);
    }

    public void selfCleanup() {
        org.removeLayer(layerName);
    }

}
