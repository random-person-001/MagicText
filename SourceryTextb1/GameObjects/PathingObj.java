package SourceryTextb1.GameObjects;

import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;

/**
 */
public class PathingObj extends Mortal {

    public PathingObj(Room theRoom, int xStart, int yStart) {
        super.strClass = "PathingObj";
        layerName = "pathLayer";
        room = theRoom;
        orgo = room.org;
        x = xStart;
        y = yStart;
        setHealth(20);
        orgo.addLayer(new Layer(new String[1][1], layerName, y, x));
        setupTimer(200);

        Layer testTarget = new Layer(new String[1][1], "TestingLayer");
        testTarget.setSpecTxt(0,0,new SpecialText("+", Color.BLACK, Color.WHITE));
        //orgo.addLayer(testTarget);

        Spell attack = new Spell (room, 0, 0, 0, 3, 10, new SpecialText("%"), new SpecialText("%"), false);
        attack.setHostility(true);
        rangedInit(5, 5, 5, 25, attack);
    }

    @Override
    public void update() {
        //long nanoLast = System.nanoTime();
        //pathToPos(followDist, getClosestGoodGuy().getX(), getClosestGoodGuy().getY());
        //System.out.println(String.format("Time to complete: %1$d ms", (System.nanoTime() - nanoLast) / 1000000));
        /*
        Mortal enemy = getClosestGoodGuy();
        if (Math.abs(enemy.getX() - x) + Math.abs(enemy.getY() - y) > 20) {
            shouldBeWaiting = false;
        }
        if (Math.abs(enemy.getX() - x) + Math.abs(enemy.getY() - y) <= attackRange) {
            shouldBeWaiting = true;
        }
        if (!shouldBeWaiting)
            rangedPathfinding(getClosestGoodGuy(), attackRange, followDist);
            */

        Layer iconLayer = orgo.getLayer(layerName);
        if (iconLayer != null) iconLayer.setPos(y, x);
        orgo.editLayer("O", layerName, 0, 0);
    }
}
