package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.GameObject;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;

/**
 * Created by Jared on 12/2/2016.
 */

public class FallingBeehive extends GameObject {
    public String layerName = "";

    public FallingBeehive (Room container, int startX, int startY){
        x = startX;
        y = startY;
        realY = startY;
        room = container;
        orgo = room.org;

        layerName = room.makeUniqueLayerName("Beehive");

        Layer iconLayer = new Layer (new String[1][1],layerName, y, x);
        iconLayer.setSpecTxt(0,0, new SpecialText("8", new Color(200, 200, 75)));

        orgo.addLayer(iconLayer);

        setupTimer(50);
    }

    private float speed = 0.03f;
    private float realY;

    public void update(){
        realY += speed; //The easy stuff

        float acceleration = 0.08f;
        float maxSpeed = 2;  //"terminal velocity"
        if (speed < maxSpeed)
            speed += acceleration;

        y = (int)realY; // 'y' is just an impostor

        Layer iconLayer = orgo.getLayer(layerName);
        if (iconLayer != null) iconLayer.setPos(y,x);
    }
}
