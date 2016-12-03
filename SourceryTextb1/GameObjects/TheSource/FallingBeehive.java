package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.GameObject;
import SourceryTextb1.GameObjects.Spell;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;
import java.util.Random;

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
        org = room.org;

        layerName = room.makeUniqueLayerName("Beehive");

        Layer iconLayer = new Layer (new String[1][1],layerName, y, x);
        iconLayer.setSpecTxt(0,0, new SpecialText("8", new Color(200, 200, 75)));

        org.addLayer(iconLayer);

        setupTimer(50);
    }

    private float speed = 0.03f;
    private float realY;

    private int beesInside = 20;

    public void update(){
        if (y < 59) {
            realY += speed; //The easy stuff

            //float acceleration = 0.08f;
            float acceleration = 0.01f;
            float maxSpeed = 2;  //"terminal velocity"
            if (speed < maxSpeed)
                speed += acceleration;

            y = (int) realY; // 'y' is just an impostor

            Layer iconLayer = org.getLayer(layerName);
            if (iconLayer != null) iconLayer.setPos(y, x);
        } else {
            if (getTime() > 250 && beesInside > 0){
                Random dice = new Random();
                int randX = 2 - dice.nextInt(4);
                int randY = 2 - dice.nextInt(4);
                System.out.printf("Buzz @ (delta) %1$d, %2$d\n", randX, randY);
                Color beeColor = new Color(224, 224, 135);
                Spell bee = new Spell(room, x + randX, y + randY, -1, 5, 40, new SpecialText("v", beeColor), new SpecialText(".", beeColor), true, true);
                room.addObject(bee);
                resetTime();
                beesInside--;
            }
        }
    }
}
