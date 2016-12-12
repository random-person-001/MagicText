package SourceryText.GameObjects;

import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 */
public class PathingObj extends Mortal {

    public PathingObj(Room theRoom, int xStart, int yStart) {
        super.strClass = "PathingObj";
        layerName = "pathLayer";
        room = theRoom;
        org = room.org;
        x = xStart;
        y = yStart;
        setHealth(20);
        org.addLayer(new Layer(new String[1][1], layerName, x, y));
        setupTimer(200);

        Layer testTarget = new Layer(new String[1][1], "TestingLayer");
        testTarget.setSpecTxt(0, 0, new SpecialText("+", Color.BLACK, Color.WHITE));
        //org.addLayer(testTarget);

        Spell attack = new Spell(room, 0, 0, 0, 3, 10, new SpecialText("%"), new SpecialText("%"), false, "arcane");
        attack.setHostility(true);
        rangedInit(5, 5, 5, 25, attack);
    }

    @Override
    public void update() {
        //long nanoLast = System.nanoTime();
        //pathToPos(followDist, getClosestGoodGuy().getX(), getClosestGoodGuy().getX());
        //System.out.println(String.format("Time to complete: %1$d ms", (System.nanoTime() - nanoLast) / 1000000));
        /*
        Mortal enemy = getClosestGoodGuy();
        if (Math.abs(enemy.getY() - x) + Math.abs(enemy.getX() - y) > 20) {
            shouldBeWaiting = false;
        }
        if (Math.abs(enemy.getY() - x) + Math.abs(enemy.getX() - y) <= attackRange) {
            shouldBeWaiting = true;
        }
        if (!shouldBeWaiting)
            rangedPathfinding(getClosestGoodGuy(), attackRange, followDist);
            */

        Layer iconLayer = org.getLayer(layerName);
        if (iconLayer != null) iconLayer.setPos(x, y);
        org.editLayer("O", layerName, 0, 0);
    }
}
