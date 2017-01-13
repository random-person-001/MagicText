package SourceryText.GameObjects.TheSource;

import SourceryText.GameObjects.GameObject;
import SourceryText.ImageOrg;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

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
        Layer flakeLayer = new Layer(new String[1][1], layerName, x, y);

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
        if (iconLayer != null) iconLayer.setPos(x, y);
    }

    public void selfCleanup() {
        org.removeLayer(layerName);
    }

    public void respawn() {
        x = -1;
    }
}
