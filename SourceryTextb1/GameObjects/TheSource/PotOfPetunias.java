package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

/**
 * An extraordinarily weak enemy.
 * Created by riley-ubuntu on 11-Jun-2016.
 */
public class PotOfPetunias extends Mortal {
//    private boolean sentMessageBefore = false;

    public PotOfPetunias (ImageOrg org, Room theRoom, int setX, int setY){
        strClass = "PotOfPetunias";
        orgo = org;
        room = theRoom;
        layerName = "Petunias";
        Layer staffLay = new Layer(new String[room.roomHeight][room.roomWidth], layerName);
        org.addLayer(staffLay);

        x = setX;
        y = setY;
        setHealth(1);
        //orgo.editLayer("V", layerName, y, x);

        setupTimer(100);
    }

    public void update(){
        orgo.editLayer("V", layerName, y, x);
//  if (room.playo.x + 3 == x && room.playo.y == y && !sentMessageBefore) {
//            room.infoMessage(orgo, "It appears you have come across an enemy.  It is suggested that you chose to be " +
//                    "the survivor in this encounter.  Remember, A is lock orientation and S is fire.  Good luck.");
//            sentMessageBefore = true;
//
    }

}
