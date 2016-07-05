package SourceryTextb1.Rooms;

import SourceryTextb1.GameObjects.*;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.art;

/**
 * Created by riley-ubuntu on 09-Jun-2016.
 * 
 * So Far:
 *  > You have been introduced to the backstory
 *  > That's it
 *
 * KEY: V: complete  >: to do  _: partial
 *
 * What Happens Here:
 *  V You become familiar with moving around the character and the format of the world
 *  V You're informed of how to lock orientation and attack
 *       "If you want to lock your orientation, you can press A to toggle a Locked Orientation, indicated with a "+".
 *       Go ahead, try it out!  Just don't press S until you're ready, or you will have a sad day."
 *       "Press S to use whatever is selected as your weapon.  In this case, all you have is an old, musty spellbook to
 *       throw.  You probably should not miss, as you don't have multiple copies."
 *  _ You kill a small enemy by throwing [whatever you have] at them,
 *       "It appears you have come across an enemy.  It is suggested that you chose to be the survivor in this
 *       encounter.  Remember, A is lock orientation and S is fire.  Good luck."
 *      > actual mechanism of killing
 *  V Loot: what's needed to cast normal spells (for the next level) [A Wooden Staff]
 *  V You're informed of how to equip that into your inventory
 *       "It would probably behoove you to place that into your inventory, such that you are more civilized than tossing
 *       blunt objects, and can cast spells like a decent member of our society.  Press enter to learn how."
 *  V Then you move on to the next level!
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
                updateObjs(75);
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
        Layer spells = new Layer(new String[maxH][maxW], "Spellz", true);
        org.addLayer(spells);

        super.playo = player;
        player.castingLayer = spells;
        super.baseHitMesh = new boolean[super.roomHeight][super.roomWidth];
        super.objHitMesh = new boolean[super.roomHeight][super.roomWidth];
        emptyAllHitMeshes();
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

        Layer HUDd = new Layer(new String[maxH][maxW], "HUD", false);
        org.addLayer(HUDd);
        HUD hud = new HUD(org, this, HUDd);
        addObject(hud);
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

