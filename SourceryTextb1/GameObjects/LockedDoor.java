package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

/**
 * Created by Jared on 17-Sep-16.
 */
public class LockedDoor extends GameObject{
    String keyName;
    int lockpickDifficulty;
    String layerName;

    public LockedDoor(ImageOrg org, Room container, String nameOfKey, int xSet, int ySet, int difficulty){
        x = xSet;
        y = ySet;
        keyName = nameOfKey;
        lockpickDifficulty = difficulty;
        orgo = org;
        room = container;
        strClass = "LockedDoor";

        layerName = room.makeUniqueLayerName(strClass);
        Layer selfLayer = new Layer(new String[1][1], layerName, y, x);
        selfLayer.setStr(0,0, ":");
        orgo.addLayer(selfLayer);
    }
}
