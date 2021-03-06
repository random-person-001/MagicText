package SourceryText.GameObjects.TheSource;

import SourceryText.GameObjects.Mortal;
import SourceryText.ImageOrg;
import SourceryText.Layer;
import SourceryText.Rooms.Room;

/**
 * An extraordinarily weak enemy.
 * Created by riley-ubuntu on 11-Jun-2016.
 */
public class PotOfPetunias extends Mortal {
//    private boolean sentMessageBefore = false;

    public PotOfPetunias(ImageOrg org, Room theRoom, int setX, int setY) {
        strClass = "PotOfPetunias";
        this.org = org;
        room = theRoom;
        layerName = "Petunias";

        x = setX;
        y = setY;

        Layer potLayer = new Layer(new String[1][1], layerName, x, y);
        potLayer.setStr(0, 0, "V");
        org.addLayer(potLayer);

        setHealth(1);

        setupTimer(100);
    }

    public void update() {
        org.editLayer("<span color='#cc0000'>V</span>", layerName, y, x);
//  if (room.playo.x + 3 == x && room.playo.y == y && !sentMessageBefore) {
//            room.infoMessage(org, "It appears you have come across an enemy.  It is suggested that you chose to be " +
//                    "the survivor in this encounter.  Remember, A is lock orientation and S is fire.  Good luck.");
//            sentMessageBefore = true;
//
    }

}
