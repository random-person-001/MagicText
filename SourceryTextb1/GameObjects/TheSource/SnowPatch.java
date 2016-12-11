package SourceryTextb1.GameObjects.TheSource;

import SourceryTextb1.Layer;
import SourceryTextb1.GameObjects.InteractableEnvironment;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;

/**
 * Created by riley on 07-Dec-2016.
 */
public class SnowPatch extends InteractableEnvironment {
    private Color[] stagesOfSnow = {
            new Color(225, 225, 225),
            new Color(153, 161, 176),
            new Color(115, 159, 163),
            new Color(67, 116, 129),
            new Color(35, 72, 89),
            new Color(17, 34, 42),
            new Color(1, 5, 7),
            new Color(0, 0, 0)};
    private String[] charactersOfSnow = {"O", "o", "o", "_", "_", ".", "_", "."};

    public SnowPatch (Room room, Layer layWithStuff, SpecialText[] relavantChars){
        super(room, layWithStuff, relavantChars);
        super.strClass = "SnowPatch";
        super.instaSpread = true;
        super.maxAnimation = 7;
        super.chanceOfSpreadingInEachDirection = 0;
        addSpellTypesToCareAbout("fire");
    }

    @Override
    protected void advanceAnimation(int dataC, int dataR, int currentState){
        String dispChar = charactersOfSnow[currentState];
        if (currentState == 6){
            dispChar = " ";
        }
        SpecialText newSpTxt = new SpecialText(dispChar, stagesOfSnow[currentState], stagesOfSnow[currentState + 1]);
        room.org.editLayer(newSpTxt, layerName, dataR, dataC);
        data[dataC][dataR] ++;
    }
}
