package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

/**
 * What it says on the tin.
 * Created by Jared on 09-Sep-16.
 */
public class OneWayDoor extends GameObject{
    private boolean facingLeft = false;
    private String layerName;
    public String strClass = "One-wayDoor";

    public OneWayDoor(boolean isLefty, int xSet, int ySet, Room creator, ImageOrg org){
        facingLeft = isLefty;
        super.room = creator;
        super.orgo = org;
        layerName = room.makeUniqueLayerName(strClass);
        x = xSet;
        y = ySet;
        Layer icon = new Layer(new String[1][1], layerName, y, x);
        if (facingLeft){
            icon.setStr(0,0,"{");
        } else {
            icon.setStr(0,0,"}");
        }
        orgo.addLayer(icon);
        room.addToObjHitMesh(x,y);
        setupTimer(150);
    }

    private void selfDestruct(){
        room.splashMessage("The one way door opened!","");
        orgo.removeLayer(layerName);
        room.removeFromObjHitMesh(x,y);
        room.removeObject(this);
        cancelTimer();
    }

    @Override
    public void update(){
        for (Player player : room.players) {
            if (facingLeft && player.getX() == x + 1 && player.getY() == y) {
                selfDestruct();
            }
            if (!facingLeft && player.getX() == x - 1 && player.getY() == y) {
                selfDestruct();
            }
        }
    }
}
