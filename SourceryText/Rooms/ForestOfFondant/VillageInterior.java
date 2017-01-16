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

    private Layer roomLayer;
    private int fireplaceFireState = 0;

    protected String loop(Player play) {
        while (exitCode.equals("")) {
            try {
                if (play.getX() == 14 && play.getY() == 6) {
                    setNewRoom("FondantVillage", play, 36, 56);
                }
                if (play.getX() == 63 && play.getY() == 6) {
                    setNewRoom("FondantVillage", play, 32, 85);
                }
                if ((play.getX() == 100 || play.getX() == 101) && play.getY() == 6) {
                    setNewRoom("FondantVillage", play, 35, play.getX() + 19);
                }
                if (play.isAt(10, 29)){
                    setNewRoom("FondantVillage", play, 28, 145);
                }
                if (play.isAt(25, 61)){
                    setNewRoom("FondantVillage", play, 17, 86);
                }
                if (play.isAt(61, 29)){
                    setNewRoom("FondantVillage", play, 20, 112);
                }
                Color fireBkg = new Color(120, 25, 0);
                switch (fireplaceFireState) {
                    case 0:
                        roomLayer.findAndReplace(new SpecialText("w", new Color(150, 40, 0), fireBkg), new SpecialText("w", new Color(165, 70, 0), fireBkg));
                        break;
                    case 1:
                        roomLayer.findAndReplace(new SpecialText("w", new Color(165, 70, 0), fireBkg), new SpecialText("W", new Color(180, 100, 0), fireBkg));
                        break;
                    case 2:
                        roomLayer.findAndReplace(new SpecialText("W", new Color(180, 100, 0), fireBkg), new SpecialText("W", new Color(195, 130, 0), fireBkg));
                        break;
                    case 3:
                        roomLayer.findAndReplace(new SpecialText("W", new Color(195, 130, 0), fireBkg), new SpecialText("W", new Color(180, 100, 0), fireBkg));
                        break;
                    case 4:
                        roomLayer.findAndReplace(new SpecialText("W", new Color(180, 100, 0), fireBkg), new SpecialText("w", new Color(165, 70, 0), fireBkg));
                        break;
                    case 5:
                        roomLayer.findAndReplace(new SpecialText("w", new Color(165, 70, 0), fireBkg), new SpecialText("w", new Color(150, 40, 0), fireBkg));
                        break;
                }
                fireplaceFireState++;
                if (fireplaceFireState > 5) fireplaceFireState = 0;
                Thread.sleep(200);
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
        baseLayer.findAndReplace(new SpecialText("m"), new SpecialText("m", new Color(100, 64, 39), new Color(38, 19, 6)));

        baseLayer.findAndReplace(new SpecialText("f"), new SpecialText("w", new Color(150, 40, 0), new Color(120, 25, 0)));
        baseLayer.findAndReplace(new SpecialText("F"), new SpecialText("#", new Color(130, 110, 100), new Color(55, 55, 55)));
        baseLayer.findAndReplace(new SpecialText("t"), new SpecialText("+", new Color(55, 55, 55), new Color(40, 40, 40)));
        baseLayer.findAndReplace(new SpecialText("T"), new SpecialText("T", new Color(90, 85, 85), new Color(40, 40, 40)));

        baseLayer.findAndReplace(new SpecialText("u"), new SpecialText("u", new Color(110, 135, 135), new Color(38, 19, 6)));

        baseLayer.findAndReplace(new SpecialText("x"), new SpecialText(" ", null, Color.BLACK));

        org.addLayer(baseLayer);
        roomLayer = baseLayer;

        initHitMeshes(baseLayer);

        addItems();
        String[] solids = {"|","-","O","o","#","F","f","x","u","m"};
        addToBaseHitMesh(base, solids);

        genericRoomInitialize();
    }
}
