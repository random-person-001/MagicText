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
    public RangedBandit (Room theRoom, int startX, int startY){
        super.strClass = "PathingObj";
        layerName = "pathLayer";
        room = theRoom;
        orgo = room.org;
        x = startX;
        y = startY;
        setHealth(20);
        orgo.addLayer(new Layer(new String[1][1], layerName, y, x));
        setupTimer(200);

        Layer testTarget = new Layer(new String[1][1], "TestingLayer");
        testTarget.setSpecTxt(0,0,new SpecialText("+", Color.BLACK, Color.WHITE));

        Spell attack = new Spell (room, 0, 0, 0, 5, 10, new SpecialText("|"), new SpecialText("-"), false);
        attack.setHostility(true);
        rangedInit(5, 5, 5, 25, attack);
    }

    @Override
    public void update() {
        Layer iconLayer = orgo.getLayer(layerName);
        if (iconLayer != null) iconLayer.setPos(y, x);
        orgo.editLayer("R", layerName, 0, 0);
    }
}
