package SourceryTextb1.GameObjects;

import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

/**
 */
public class PathingObj extends Mortal {
    int followDist = 30;

    public PathingObj(Room theRoom, int xStart, int yStart) {
        super.strClass = "PathingObj";
        layerName = "pathLayer";
        room = theRoom;
        orgo = room.org;
        x = xStart;
        y = yStart;
        setHealth(20);
        orgo.addLayer(new Layer(new String[1][1], layerName, y, x));
        setupTimer(750);
    }

    @Override
    public void update() {
        orgo.editLayer("O", layerName, 0, 0);
        //long nanoLast = System.nanoTime();
        pathToPos(followDist, getClosestGoodGuy().getX(), getClosestGoodGuy().getY());
        //System.out.println(String.format("Time to complete: %1$d ms", (System.nanoTime() - nanoLast) / 1000000));
        orgo.editLayer("O", layerName, 0, 0);
    }
}
