package SourceryText.GameObjects;

import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * Created by Jared on 22-Jan-17.
 */
public class SavePoint extends GameObject{

    private String layerName;

    public SavePoint (Room creator, int setX, int setY){
        super.strClass = "SavePoint";

        room = creator;
        org = creator.org;
        x = setX;
        y = setY;

        room.addInspectable(this);

        layerName = room.makeUniqueLayerName(strClass);
        Layer selfIcon = new Layer(new String[1][3], layerName, x - 1, y);
        selfIcon.setSpecTxt(0, 0, new SpecialText("["));
        selfIcon.setSpecTxt(0, 1, new SpecialText("S"));
        selfIcon.setSpecTxt(0, 2, new SpecialText("]"));

        for (int ii = -1; ii <= 1; ii++){
            room.addToObjHitMesh(x + ii, y);
        }

        org.addLayer(selfIcon);

        setupTimer(100);
    }

    private int hue = 150;
    private boolean hueIncreasing = true;

    public void update(){
        int MIN_HUE = 100;
        int MAX_HUE = 250;

        org.editLayer(new SpecialText("S", Color.getHSBColor((float)hue / 360, .7f, 1f)), layerName, 0, 1);
        if (hueIncreasing) hue += 2;
        else hue -= 2;
        if (hue == MIN_HUE) hueIncreasing = true;
        if (hue == MAX_HUE) hueIncreasing = false;
    }

    @Override
    public void onInspect(Player inspector){
        room.splashQuestion("Save the game?", inspector, 255);
    }
}
