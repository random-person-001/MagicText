package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

/**
 * A locked door!  Needs a key (or lock picking) to unlock
 * Created by Jared on 17-Sep-16.
 */
public class LockedDoor extends GameObject {
    private String layerName;
    public String strClass = "LockedDoor";
    private boolean willMessage = true;

    protected int lockpickDifficulty = 10;
    protected String keyName = "";

    public LockedDoor(String nameOfKey, int difficulty, int xSet, int ySet, Room creator, ImageOrg org) {
        keyName = nameOfKey;
        lockpickDifficulty = difficulty;
        super.room = creator;
        super.org = org;
        layerName = room.makeUniqueLayerName(strClass);
        x = xSet;
        y = ySet;
        Layer icon = new Layer(new String[1][1], layerName, x, y);
        icon.setStr(0, 0, ":");
        this.org.addLayer(icon);
        room.addToObjHitMesh(x, y);
        setupTimer(150);
    }

    private void selfDestruct() {
        Player player = room.getClosestPlayerTo(x, y);
        if (player != null)
            room.splashMessage("The locked door opened!", "", player);
        org.removeLayer(layerName);
        room.removeFromObjHitMesh(x, y);
        room.removeObject(this);
        cancelTimer();
    }

    @Override
    public void update() {
        for (Player player : room.players) {
            if (Math.abs(player.getX() - x) == 1 && player.getY() == y) {
                if (player.getInventory().getItem(keyName, "items") != null) {
                    selfDestruct();
                } else {
                    if (willMessage) {
                        room.splashMessage("This door is locked!\n   (Key Needed: \'" + keyName + "\')", "", player);
                        willMessage = false;
                    }
                }
            }
        }
    }
}
