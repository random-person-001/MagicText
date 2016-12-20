package SourceryText.GameObjects.ForestOfFondant;

import SourceryText.GameObjects.InteractableEnvironment;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * A Class that handles making trees (technically, certain SpecialTexts) of a Layer flammable.
 * Created by riley on 02-Dec-2016.
 */
public class FlammableTree extends InteractableEnvironment {
    private Color[] stagesOfFire = {new Color(184, 222, 85), new Color(222, 138, 0), new Color(222, 202, 0), new Color(222, 61, 24),
            new Color(167, 83, 1), new Color(106, 48, 0), new Color(70, 44, 31), new Color(40, 40, 40)};
    private String[] charactersOfFire = {"W", "M", "w", "m", "_", "w", ".", " "};

    public FlammableTree(Room room, Layer layWithTrees, SpecialText[] treeChars) {
        super(room, layWithTrees, treeChars);
        super.strClass = "FlammableTree";
        super.chanceOfSpreadingInEachDirection = 5; //  40;
        addSpellTypesToCareAbout("fire");
    }

    protected void onCustomSpellOver(int relativeX, int relativeY) {
        room.removeFromBaseHitMesh(relativeX, relativeY);
    }

    @Override
    protected void advanceAnimation(int dataC, int dataR, int currentState){
        String dispChar = charactersOfFire[currentState];
        if (currentState == 6){
            if (r(100)<= 30)
                dispChar = "_";
            else if (r(100) <= 30)
                dispChar = ".";
            else
                dispChar = " ";
        }
        if (currentState < 4){
            int absX = dataC + layerWithStuff.getX();
            int absY = dataR + layerWithStuff.getY();
            room.hurtSomethingAt(absX, absY ,2,"You were burnt to a crisp", "fire");
            // Maybe start a forest fire!
            if (r(100) <= chanceOfSpreadingInEachDirection){
                onSpellOver(absX+1, absY, "fire");
            }
            if (r(100) <= chanceOfSpreadingInEachDirection){
                onSpellOver(absX-1, absY, "fire");
            }
            if (r(100) <= chanceOfSpreadingInEachDirection){
                onSpellOver(absX, absY+1, "fire");
            }
            if (r(100) <= chanceOfSpreadingInEachDirection){
                onSpellOver(absX, absY-1, "fire");
            }
        }
        SpecialText newSpTxt = new SpecialText(dispChar, stagesOfFire[currentState], stagesOfFire[currentState + 1]);
        room.org.editLayer(newSpTxt, layerName, dataR, dataC);
        if (r(9) <= 2 && currentState != 1 && currentState < 5){
            data[dataC][dataR] --;
        }
        else {
            data[dataC][dataR] ++;
        }
    }
}
