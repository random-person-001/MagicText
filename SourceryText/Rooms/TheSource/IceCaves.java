package SourceryText.Rooms.TheSource;

import SourceryText.Art;
import SourceryText.GameObjects.DroppedItem;
import SourceryText.GameObjects.Item;
import SourceryText.GameObjects.Player;
import SourceryText.GameObjects.TheSource.CaveYeti;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * A Pretty Ice Cave!  Exciting!
 * <p>
 * You come here via the Snowy Peak
 * <p>
 * Here, you:
 * > Look at pretty scenery while yetis maul you
 * > Return with a magic poatoe and a snowball spell - if you survive
 * <p>
 * Created by riley on 01-Sep-2016.
 */
public class IceCaves extends Room {

    public IceCaves(Player player) {
        super(player);
        strRoomName = "IceCaves";
    }

    @Override
    protected String loop(Player play) {
        int count = 0;

        while (exitCode.equals("")) {
            try {
                Thread.sleep(20);
                if (play.getY() == 37) {
                    setNewRoom("SnowyPeak", play, 8, 179);
                }
                if (count == 0) {
                    if (playo.getX() == 109 && playo.getY() == 10) {
                        queueMessage(new FlavorText("Most capital letters (ex: A, B, C..) out in\n the world have flavor text \n accessible through the 'F' key", ""));
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
        int[][] yetiLocs = {{37, 14}, {43, 12}}; // X and Y are switched.
        for (int[] station : yetiLocs) {
            CaveYeti yeti = new CaveYeti(org, this, station[0], station[1]); // X and Y switched.  I'm really sorry.
            addMortal(yeti);
        }

        Item snowballSpell = new Item("FluffySnowball", "Ice Spell;\n\nA ball of fluffy snow.\n\nThe snowball itself is\n very cold, so it will\n slow enemies that are hit.", "FSwBl", "spell", true);
        snowballSpell.dmgSpellDefine(1, 6, 2, 0.03f, "ice", new SpecialText("o", new Color(191, 249, 255)), new SpecialText("o", new Color(191, 249, 255)), false, 0);
        DroppedItem gSnowball = new DroppedItem(this, "Found a new spell: FluffySnowball!", snowballSpell, 21, 3);
        super.addObject(gSnowball);

        addMagicPotato(24, 1);
    }

    @Override
    public void startup() {

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.iceCave);
        Layer lay1 = new Layer(base, "Base");
        lay1.findAndReplace(new SpecialText("#"), new SpecialText("/", new Color(15, 119, 145), new Color(14, 113, 140)), 25);
        lay1.findAndReplace(new SpecialText("#"), new SpecialText(" ", null, new Color(14, 113, 140)));
        lay1.findAndReplace(new SpecialText(" "), new SpecialText("/", new Color(0, 70, 90), new Color(0, 55, 77)), 25);
        lay1.findAndReplace(new SpecialText(" "), new SpecialText(" ", null, new Color(0, 55, 77)));

        lay1.findAndReplace(new SpecialText("^"), new SpecialText("^", new Color(0, 110, 140), new Color(0, 55, 77)));

        lay1.findAndReplace(new SpecialText("1"), new SpecialText(" ", null, new Color(195, 211, 217)));
        lay1.findAndReplace(new SpecialText("2"), new SpecialText(" ", null, new Color(103, 151, 171)));
        lay1.findAndReplace(new SpecialText("3"), new SpecialText(" ", null, new Color(37, 102, 125)));
        lay1.findAndReplace(new SpecialText("4"), new SpecialText(" ", null, new Color(0, 73, 97)));

        org.addLayer(lay1);

        initHitMeshes(lay1);
        String[] solids = {"#"};
        addToBaseHitMesh(base, solids);

        org.roomBackground = new Color(14, 113, 140);

        addItems();

        genericRoomInitialize();
    }
}
