package SourceryText.GameObjects;

import SourceryText.ImageOrg;
import SourceryText.Layer;
import SourceryText.Rooms.Room;

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
        super.org = org;
        layerName = room.makeUniqueLayerName(strClass);
        x = xSet;
        y = ySet;
        Layer icon = new Layer(new String[1][1], layerName, x, y);
        if (facingLeft) {
            icon.setStr(0, 0, "{");
        } else {
            icon.setStr(0, 0, "}");
        }
        this.org.addLayer(icon);
        room.addToObjHitMesh(x, y);
        setupTimer(150);
    }

    private void openDoor() {
        if (!showedMessage) {
            Player player = room.getClosestPlayerTo(x, y);
            if (player != null)
                room.splashMessage("The one way door opened!", "", player);
            showedMessage = true;
        }
        org.editLayer(" ", layerName, 0, 0);
        room.removeFromObjHitMesh(x, y);
    }

    private void closeDoor() {
        if (facingLeft)
            org.editLayer("{", layerName, 0, 0);
        else
            org.editLayer("}", layerName, 0, 0);
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
