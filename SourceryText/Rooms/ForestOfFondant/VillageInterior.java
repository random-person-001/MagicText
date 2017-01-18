package SourceryText.Rooms.ForestOfFondant;

import SourceryText.Art;
import SourceryText.GameObjects.DroppedItem;
import SourceryText.GameObjects.Item;
import SourceryText.GameObjects.Player;
import SourceryText.GameObjects.TheSource.Bandit;
import SourceryText.GameObjects.TheSource.Spider;
import SourceryText.GameObjects.TheSource.Wolf;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * The interiors of the houses in FondantVillage
 * Created by Jared on 15-Jan-2017.
 */
public class VillageInterior extends Room {

    public VillageInterior(Player player) {
        super(player);
        strRoomName = "VillageInterior";
    }

    private int vcQuestProgress = 0;

    private Layer roomLayer;
    private int fireplaceFireState = 0;

    protected String playerLoop(Player play) {
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

    @Override
    protected void specialInspect(int x, int y, Player observer){
        if (observer.getWeapon().getName().equals("Shaman Staff") && vcQuestProgress == 0 && x == 10 && y == 59){
            String[] message = {"Hello there!","Wait, is that the walking stick that\n was tossed in the shack a few days ago?","That thing's cursed! It's evil!",
                    "That idiot nerd with that big bookshelf\n tried to make a flying walking stick,\n but it's become a bad luck charm.",
                    "You should give it back to him...\nGive him a taste of his own medicine!"};
            for (String str : message){
                splashMessage(str, "", observer);
            }
            vcQuestProgress++;
        }
        if (vcQuestProgress == 1 && x == 10 && y == 27){
            String[] message = {"Oh, good! You found the walking stick!","I got it as gift from...uhhh,\n an anonmyous group. That doesn't really matter though...",
                    "Anyways, it just blasting people with\n magic all by itself. It even laughed\n at its victims...I swear I didn't do it!",
                    "It also summoned cats to annoy people\n while they tried to sleep.","That thing is pure evil!",
                    "I've got to give it back to them!\n But I've lost my only map to\n find their secret base..."};
            for (String str : message){
                splashMessage(str, "", observer);
            }
            vcQuestProgress++;
        }
        if (vcQuestProgress == 2 && x == 13 && y == 24){
            queueMessage(new FlavorText("You find an oddly crumpled bookmark\n in one of the books...",""), observer);
            queueMessage(new FlavorText("You found a mysterious map!",""), observer);
            observer.addItem(new Item("Mysterious Map",
                    "A 1:1 scale map of an\n" +
                    " entrance to a secret base.\n" +
                    "The Map:\n" +
                    "~~~~~~~~~~~~~~~~~~~~\n" +
                    ":         ooooooooo:\n" +
                    ":  X               :\n" +
                    ":       ooooooo    :\n" +
                    ":           ooOooo :\n" +
                    ":###          oo   :\n" +
                    ":#########         :\n" +
                    "~~~~~~~~~~~~~~~~~~~~\n"
                    ,"item"));
            vcQuestProgress++;
        }
    }

    /**
     * Everything that self-updates and can be paused (and acts nonexistent during paused) should go here.
     */
    @Override
    public void addItems() {
        Item shamanStaff = new Item("Shaman Staff","A staff used in dark\n rituals. It's both useful\n for arcane and dark magic." +
                "\n\nIt's very worn out though,\n since it also doubled\n as a walking stick.","equip");
        shamanStaff.setEquipvals(0, 0, 0, 5, 0, 0, 4, "weapon");
        DroppedItem dStaff = new DroppedItem(this, "You found a wizard staff!", shamanStaff, 14, 1);
        addObject(dStaff);
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

        Color[] bookColors =
                {new Color(148, 130, 74),
                new Color(128, 75, 38),
                new Color(148, 62, 44)};
        colorBooks(bookColors, baseLayer);

        org.addLayer(baseLayer);
        roomLayer = baseLayer;

        initHitMeshes(baseLayer);

        addItems();
        String[] solids = {"|","-","O","o","#","F","f","x","u","m","l"};
        addToBaseHitMesh(base, solids);

        genericRoomInitialize();
    }

    private void colorBooks (Color[] colors, Layer lay){
        for (int ii = 0; ii < colors.length; ii++){
            int prob = (int)(100f / (float)(colors.length - ii));
            lay.findAndReplace(new SpecialText("l"), new SpecialText("m", colors[ii]),prob);
        }
    }
}
