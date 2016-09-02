package SourceryTextb1.Rooms;

import SourceryTextb1.GameObjects.*;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.art;

/**
 * Created by riley-ubuntu on 09-Jun-2016.
 * Mostly obsolete.
 */

public class BeginningRoom extends Room {
    ImageOrg org;
    int maxH;
    int maxW;

    @Override
    protected String loop() {
        int count = 0;
        while (exitCode.isEmpty()) {
            try {
                Thread.sleep(75);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (super.playo.getX() > 51) {
                return "die";
            }
            if (count == 0) {
                infoMessage(org, "If you want to lock your orientation, you can press A to toggle a Locked " +
                        "Orientation, indicated with a \"+\".  Go ahead, try it out!  Just don't press S until " +
                        "you're ready, or you will have a sad day.");
                count++;
            } else if (count == 1) {
                infoMessage(org, "Press S to use whatever is selected as your weapon.  In this case, all you have " +
                        "is an old, musty spellbook to throw.  You probably should not miss, as you don't have " +
                        "multiple copies.");
                count++;
            }
        }
        return exitCode;
    }

    public void startup(Player player) {
        ititHitMeshes();
        Layer spells = new Layer(new String[maxH][maxW], "Spellz", true);
        org.addLayer(spells);

        super.playo = player;
        player.castingLayer = spells;
        art arty = new art();
        String[] solids = {"╔", "╗", "═", "╚", "╝", "║"};
        String[][] roomArr = art.strToArray(arty.smallRoom);
        addToBaseHitMesh(roomArr, solids);
        Layer lay1 = new Layer(roomArr, "base");
        org.addLayer(lay1);

        Staff staff = new Staff(org, this, 38, 2);
        addObject(staff);

        PotOfPetunias flowers = new PotOfPetunias(org, this, 30, 2);
        addMortal(flowers);

        Layer playerLayer = new Layer(new String[maxH][maxW], player.getLayerName());
        org.addLayer(playerLayer);
        player.goTo(6, 2);

        genericRoomInitialize();
    }

    public BeginningRoom(ImageOrg orgo) {
        org = orgo;
        maxH = 7;
        maxW = 55;
        super.roomHeight = maxH;
        super.roomWidth = maxW;
        super.index = 1;
    }

}

