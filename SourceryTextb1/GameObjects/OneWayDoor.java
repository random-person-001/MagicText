package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

/**
 * What it says on the tin.
 * Created by Jared on 09-Sep-16.
 */
public class OneWayDoor extends GameObject {
    private boolean facingLeft = false;
    private String layerName;
    public String strClass = "One-wayDoor";

    public OneWayDoor(boolean isLefty, int xSet, int ySet, Room creator, ImageOrg org) {
        facingLeft = isLefty;
        super.room = creator;
        super.orgo = org;
        layerName = room.makeUniqueLayerName(strClass);
        x = xSet;
        y = ySet;
        Layer icon = new Layer(new String[1][1], layerName, y, x);
        if (facingLeft) {
            icon.setStr(0, 0, "{");
        } else {
            icon.setStr(0, 0, "}");
        }
        orgo.addLayer(icon);
        room.addToObjHitMesh(x, y);
        setupTimer(150);
    }

    private void openDoor() {
        if (!showedMessage) {
            room.splashMessage("The one way door opened!", "");
            showedMessage = true;
        }
        orgo.editLayer(" ", layerName, 0, 0);
        room.removeFromObjHitMesh(x, y);
    }

    private void closeDoor() {
        if (facingLeft)
            orgo.editLayer("{", layerName, 0, 0);
        else
            orgo.editLayer("}", layerName, 0, 0);
        room.addToObjHitMesh(x, y);
        showedMessage = false;
    }

    private boolean showedMessage = false;

    @Override
    public void update() {
        for (Player player : room.players) {
            if (player.getY() == y) {
                if (facingLeft) { //Left-facing doors
                    if (player.getX() == x + 1)
                        openDoor();
                    if (player.getX() < x)
                        closeDoor();
                } else {          //Right-facing doors
                    if (player.getX() == x - 1)
                        openDoor();
                    if (player.getX() > x)
                        closeDoor();
                }
            }
        }
    }
}
