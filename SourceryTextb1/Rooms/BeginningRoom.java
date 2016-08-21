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

    private void loop(){
        for (int ii = 0 ; ii < 1000 ; ii++){
            try {
                Thread.sleep(75);
                //System.out.println("I'm not dead yet! " + ii);

                super.playo.update();
                org.compileImage();
                if (super.playo.getX() > 51){
                    return;
                }
                if (ii == 1){
                    infoMessage(org, "If you want to lock your orientation, you can press A to toggle a Locked " +
                            "Orientation, indicated with a \"+\".  Go ahead, try it out!  Just don't press S until " +
                            "you're ready, or you will have a sad day.");
                }else if (ii == 2){
                    infoMessage(org, "Press S to use whatever is selected as your weapon.  In this case, all you have " +
                            "is an old, musty spellbook to throw.  You probably should not miss, as you don't have " +
                            "multiple copies.");
                }


            } catch (InterruptedException ex) {
                System.exit(0);
            }
        }
    }

    public void startup(Player player){
        ititHitMeshes();
        Layer spells = new Layer(new String[maxH][maxW], "Spellz", true);
        org.addLayer(spells);

        super.playo = player;
        player.castingLayer = spells;
        art arty = new art();
        String[] solids = {"╔","╗","═","╚","╝","║"};
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

    /**
     * Enter the room. IE, start loops and stuff now.
     */
    public void enter(){
        org.compileImage();
        super.playo.frozen = false;
        loop();
        super.playo.frozen = true;
        org.removeAllButPlayer(); //Cleanup, happens when loop is done.
        org.compileImage();
    }

    public BeginningRoom(ImageOrg orgo){
        org = orgo;
        maxH = 7;
        maxW = 55;
        super.roomHeight = maxH;
        super.roomWidth = maxW;
        super.index = 1;
    }

}

