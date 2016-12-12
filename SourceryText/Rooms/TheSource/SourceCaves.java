package SourceryText.Rooms.TheSource;

import SourceryText.Art;
import SourceryText.GameObjects.DroppedItem;
import SourceryText.GameObjects.Item;
import SourceryText.GameObjects.Player;
import SourceryText.GameObjects.TheSource.Bandit;
import SourceryText.GameObjects.TheSource.Ghost;
import SourceryText.GameObjects.TheSource.Spider;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * Caves!  Exciting!
 * <p>
 * You generally come here via the Cliffside
 * <p>
 * Here, you:
 * > battle scary ghosts and spiders and bandits
 * > find an entrance to the Hidden Bunker of the bandits
 * Created by riley on 01-Sep-2016.
 */
public class SourceCaves extends Room {

    public SourceCaves(Player player) {
        super(player);
        strRoomName = "SourceCaves";
    }

    @Override
    protected String loop(Player play) {
        int count = 0;

        while (exitCode.equals("")) {
            try {
                Thread.sleep(20);
                if (play.getY() <= 0) {
                    setNewRoom("Cliffside", play, 31, 134);
                }
                if (play.getY() == 1 && play.getX() >= 190 && play.getX() <= 191) {
                    setNewRoom("HiddenBunker", play, 35, 5);
                }
                if (count == 0) {
                    if (playo.getX() == 109 && playo.getY() == 10) {
                        queueMessage(new Room.FlavorText("Most capital letters (ex: A, B, C..) out in\n the world have flavor text \n accessible through the 'F' key", ""));
                    }
                    count++;
                }

            } catch (InterruptedException ignored) {
            }
        }
        return exitCode;
    }

    @Override
    public void addItems() {
        int[][] ghostStations = {{99, 53}, {195, 42}, {208, 43}, {212, 48}, {241, 47}, {253, 51}, {154, 11}}; // X and Y are NOT switched.
        for (int[] station : ghostStations) {
            Ghost spookyScary = new Ghost(this, station[0], station[1]); // X and Y NOT switched.  I'm really NOT sorry.
            addMortal(spookyScary);
        }


        int[][] spiderStations = {{102, 19}, {104, 18}, {103, 18}, {105, 17}, {93, 12}, {74, 14}, {50, 8}, {32, 11}, {19, 20}, {84, 26},
                {87, 23}, {75, 29}, {46, 55}, {62, 52}, {197, 47}, {227, 42}, {225, 52}, {237, 43}, {242, 54}, {249, 45}, {249, 55}, {259, 47},
                {283, 42}, {271, 39}, {275, 34}, {284, 31}, {293, 8}, {262, 12}, {254, 13}, {243, 12}, {233, 14}, {224, 12}}; // X and Y are switched.
        for (int[] station : spiderStations) {
            Spider itsyBitsy = new Spider(this, station[0], station[1]); // X and Y switched.  I'm really sorry.
            addMortal(itsyBitsy);
        }

        int[][] banditStations = {{81, 21}, {75, 24}, {69, 22}, {64, 33}, {56, 33}, {172, 34}, {183, 34}, {181, 35}, {176, 7}, {177, 10}, {184, 3}}; // X and Y are switched.
        for (int[] station : banditStations) {
            Bandit roughBill = new Bandit(org, this, station[0], station[1]); // X and Y switched.  I'm really sorry.
            addMortal(roughBill);
        }

        addMagicPotato(26, 57);

        Item furCoat = new Item("Fur Coat", "A fluffy and warm\n fur coat.\n\nThe coat is huge, and the \n envy of all during\n the winter.", "equipment");
        furCoat.setEquipvals(0, 5, 0, 0, 0, 0, 0, "armor");
        DroppedItem dCoat = new DroppedItem(this, "You found... a fur coat? In a cave?", furCoat, 413, 3);
        addObject(dCoat);
    }

    @Override
    public void startup() {
        roomWidth = 420; //Accidental, I swear!

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.sourceCaves);
        Layer lay1 = new Layer(base, "Base");
        lay1.findAndReplace(new SpecialText("#"), new SpecialText(";", new Color(45, 39, 35), new Color(43, 38, 33)));
        lay1.findAndReplace(new SpecialText(";", new Color(45, 39, 35), new Color(43, 38, 33)), new SpecialText(" ", null, new Color(43, 38, 33)), 25);
        lay1.findAndReplace(new SpecialText(" "), new SpecialText(" ", null, new Color(0, 0, 10)));

        lay1.findAndReplace(new SpecialText("1"), new SpecialText(" ", null, new Color(30, 30, 30)));
        lay1.findAndReplace(new SpecialText("2"), new SpecialText(" ", null, new Color(20, 20, 20)));

        lay1.findAndReplace(new SpecialText("H"), new SpecialText("#", new Color(175, 175, 175), new Color(25, 25, 25)));

        org.addLayer(lay1);

        initHitMeshes(lay1);
        String[] solids = {"#", "H", "-"};
        addToBaseHitMesh(base, solids);

        Layer exitLayer = new Layer(new String[2][2], "exit", 61, 0);
        exitLayer.setSpecTxt(0, 0, new SpecialText(" ", null, new Color(100, 100, 90)));
        exitLayer.setSpecTxt(0, 1, new SpecialText(" ", null, new Color(100, 100, 90)));

        org.addLayer(exitLayer);

        org.roomBackground = new Color(43, 38, 33);

        addItems();

        genericRoomInitialize();
    }
}
