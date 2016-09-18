package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

/**
 * Created by Jared on 09-Sep-16.
 */
public class LockedDoor extends GameObject{
    private String layerName;
    public String strClass = "LockedDoor";
    private boolean willMessage = true;

    protected int lockpickDifficulty = 10;
    protected String keyName = "";

    public LockedDoor(String nameOfKey, int difficulty, int xSet, int ySet, Room creator, ImageOrg org){
        keyName = nameOfKey;
        lockpickDifficulty = difficulty;
        super.room = creator;
        super.orgo = org;
        layerName = room.makeUniqueLayerName(strClass);
        x = xSet;
        y = ySet;
        Layer icon = new Layer(new String[1][1], layerName, y, x);
        icon.setStr(0,0,":");
        orgo.addLayer(icon);
        room.addToObjHitMesh(x,y);
        setupTimer(150);
    }

    private void selfDestruct(){
        room.splashMessage("The locked door opened!","");
        orgo.removeLayer(layerName);
        room.removeFromObjHitMesh(x,y);
        room.removeObject(this);
        cancelTimer();
    }

    @Override
    public void update(){
        if (Math.abs(room.playo.getX() - x) == 1 && room.playo.getY() == y){
            if (room.playo.getInventory().getItem(keyName, "items") != null) {
                selfDestruct();
            } else {
                if (willMessage){
                    room.splashMessage("This door is locked!","");
                    willMessage = false;
                }
            }
        }
    }
}
