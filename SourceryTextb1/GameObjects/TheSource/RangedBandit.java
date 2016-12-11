package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.GameObjects.Mortal;
import SourceryTextb1.GameObjects.Spell;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;

/**
 * Created by Jared on 24-Nov-16.
 */
public class RangedBandit extends Mortal {
    public RangedBandit(Room theRoom, int startX, int startY) {
        super.strClass = "RangedBandit";

        room = theRoom;
        org = room.org;
        x = startX;
        y = startY;
        setHealth(18);
        layerName = room.makeUniqueLayerName(strClass);
        org.addLayer(new Layer(new String[1][1], layerName, x, y));
        setupTimer(200);

        /*  I don't think this is needed.  --Riley
        Layer testTarget = new Layer(new String[1][1], "TestingLayer");
        testTarget.setSpecTxt(0,0,new SpecialText("+", Color.BLACK, Color.WHITE));
        */

        Spell attack = new Spell(room, 0, 0, 0, 5, 10, new SpecialText("|"), new SpecialText("-"), false, "arcane");
        attack.setHostility(true);
        rangedInit(5, 5, 5, 25, attack);
    }

    @Override
    public void update() {
        //Layer iconLayer = org.getLayer(layerName);
        //if (iconLayer != null) iconLayer.setPos(y, x);

        setDispIcon(new SpecialText("R", new Color(255, 160, 200)));
    }
}
