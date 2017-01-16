package SourceryText.Rooms.ForestOfFondant;

import SourceryText.Art;
import SourceryText.GameObjects.Player;
import SourceryText.GameObjects.TheSource.Bandit;
import SourceryText.GameObjects.TheSource.Spider;
import SourceryText.GameObjects.TheSource.Wolf;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * Some mountains, where you fight some enemies
 * Created by riley on 15-Jun-2016.
 */
public class VillageInterior extends Room {

    public VillageInterior(Player player) {
        super(player);
        strRoomName = "VillageInterior";
    }

    protected String loop(Player play) {
        while (exitCode.equals("")) {
            try {
                if (play.getX() == 14 && play.getY() == 6) {
                    setNewRoom("FondantVillage", play, 36, 56);
                }
                Thread.sleep(20);
            } catch (InterruptedException ignored) {
            }
        }
        return exitCode;
    }

    /**
     * Everything that self-updates and can be paused (and acts nonexistent during paused) should go here.
     */
    @Override
    public void addItems() {

    }

    @Override
    public void startup() {

        String[][] base = Art.strToArray(arty.villageInterior);

        Layer baseLayer = new Layer(base, "roomLayer");

        Color woodFg =    new Color(87, 51, 26);
        Color woodBkg =   new Color(51, 33, 20);
        Color bricksFg =  new Color(102, 102, 102);
        Color bricksBkg = new Color(38, 38, 38);
        baseLayer.findAndReplace(new SpecialText("_"), new SpecialText("_", new Color(33, 18, 5), new Color(38, 19, 6)));

        baseLayer.findAndReplace(new SpecialText("O"), new SpecialText("O", woodFg, woodBkg));
        baseLayer.findAndReplace(new SpecialText("|"), new SpecialText("|", woodFg, woodBkg));
        baseLayer.findAndReplace(new SpecialText("-"), new SpecialText("-", woodFg, woodBkg));
        baseLayer.findAndReplace(new SpecialText("#"), new SpecialText("#", bricksFg, bricksBkg));

        baseLayer.findAndReplace(new SpecialText("o"), new SpecialText("o", new Color(100, 64, 39), new Color(38, 19, 6)));

        org.addLayer(baseLayer);

        initHitMeshes(baseLayer);

        addItems();
        String[] solids = {":", "^", "#", ".", "0", "o"};
        addToBaseHitMesh(base, solids);

        genericRoomInitialize();
    }
}
