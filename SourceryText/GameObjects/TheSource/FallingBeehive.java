package SourceryText.GameObjects.TheSource;

import SourceryText.GameObjects.GameObject;
import SourceryText.GameObjects.Spell;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;
import java.util.Random;

/**
 * Created by Jared on 12/2/2016.
 */

public class FallingBeehive extends GameObject {
    public String layerName = "";
    private long kickOffTime = 0;

    public FallingBeehive (Room container, int startX, int startY){
        x = startX;
        y = startY;
        realY = startY;
        room = container;
        org = room.org;

        super.strClass = "Beehive";

        layerName = room.makeUniqueLayerName(super.strClass);

        Layer iconLayer = new Layer (new String[1][1],layerName, x, y);
        iconLayer.setSpecTxt(0,0, new SpecialText("8", new Color(200, 200, 75)));

        org.addLayer(iconLayer);

        setupTimer(50);
        kickOffTime = System.currentTimeMillis();
    }

    private float speed = 0.03f;
    private float realY;

    public int beesInside = 20;

    public void update(){
        if (y < 59) {
            realY += speed; //The easy stuff

            float acceleration = 0.07f;
            //float acceleration = 0.01f;
            float maxSpeed = 2;  //"terminal velocity"
            if (speed < maxSpeed)
                speed += acceleration;

            y = (int) realY; // 'y' is just an impostor
            if (y >= 60) y = 59;

            Layer iconLayer = org.getLayer(layerName);
            if (iconLayer != null) iconLayer.setPos(x, y);
        } else {
            if (kickOffTime > 0){
                System.out.printf("Beehive falling time: %1$dms\n", System.currentTimeMillis() - kickOffTime);
                kickOffTime = 0;
            }
            if (getTime() > 250 && beesInside > 0){
                Random dice = new Random();
                int randX = 2 - dice.nextInt(4);
                int randY = 2 - dice.nextInt(4);
                System.out.printf("Buzz @ (delta) %1$d, %2$d\n", randX, randY);
                Color beeColor = new Color(224, 224, 135);
                Spell bee = new Spell(room, x + randX, y + randY, -1, 25, 40, new SpecialText("v", beeColor), new SpecialText(".", beeColor), true, true, "bee");
                room.addObject(bee);
                resetTime();
                beesInside--;
            }
        }
    }
}
