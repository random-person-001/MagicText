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

        x = setX;
        y = setY;

        Layer potLayer = new Layer(new String[1][1], layerName, y, x);
        potLayer.setStr(0,0,"V");
        org.addLayer(potLayer);

        setHealth(1);

        setupTimer(100);
    }

    public void update(){
        setDispIcon("V");
    }

}
