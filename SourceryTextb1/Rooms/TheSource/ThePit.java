/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.GameObjects.*;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.art;

/**
 * A place to begin a tutorial, guiding you through stuff.
 * @author 119184
 *
 *
 * So Far:
 *  > You have been introduced to the backstory
 *  > That's it
 *
 * What Generally Happens Here:
 *  > You become familiar with moving around the character and the format of the world
 *  > You're informed of how to lock orientation and attack
 *       "If you want to lock your orientation, you can press A to toggle a Locked Orientation, indicated with a "+".
 *       Go ahead, try it out!  Just don't press S until you're ready, or you will have a sad day."
 *       "Press S to use whatever is selected as your weapon.  In this case, all you have is an old, musty spellbook to
 *       throw.  You probably should not miss, as you don't have multiple copies."
 *  > You kill a small enemy by throwing [whatever you have] at them,
 *       "It appears you have come across an enemy.  It is suggested that you chose to be the survivor in this
 *       encounter.  Remember, A is lock orientation and S is fire.  Good luck."
 *  > Loot: what's needed to cast normal spells (for the next level) [Spark spell]
 *  > You're informed of how to equip that into your inventory
 *       "It would probably behoove you to place that into your inventory, such that you are more civilized than tossing
 *       blunt objects, and can cast spells like a decent member of our society.  Press enter to learn how."
 *  > Then you move on to the next level!
 */


public class ThePit extends Room {

    private ImageOrg org;
    private int maxH;
    private int maxW;

    private String loop(){
        String exitCode = "";
        int count = 0;
        boolean foundSpell1 = false;
        boolean foundSpell2 = false;
        boolean inMaze = false;
        boolean leavingEarly = false;

        while (exitCode.equals("")){
            try {
                Thread.sleep(20);
                //System.out.println("I'm not dead yet! " + ii);
                updateObjs(20);
                if (getPlayer().getX() > 73){
                    //exitCode = 4;
                }
                if (super.playo.dead){
                    exitCode = "die";
                }
                //super.playo.reportPos();
                org.compileImage();
                //playo.reportPos();

            } catch (InterruptedException ignored) {}
        }
        return exitCode;
    }

    public void startup(){
        ititHitMesh();

        super.playo.goTo(109,10);

        emptyHitMesh();
        art arty = new art();
        String[][] base = art.strToArray(arty.sourcePit);
        String[] solids = {".",",",":",";","^","_","#","'"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test");
        org.addLayer(lay1);

        genericInitialize();
    }

    /**
     * Enter the room. IE, start loops and stuff now.
     */
    public String enter(){
        org.compileImage();
        super.playo.frozen = false;
        String exit = loop();
        super.playo.frozen = true;
        super.cleanLayersForExit(org);
        return exit;
    }

    public ThePit(ImageOrg orgo, Player player){
        super.playo = player;
        super.org = orgo;
        org = orgo;
        maxH = org.getWindow().maxH();
        maxW = org.getWindow().maxW();
        super.roomHeight = maxH;
        super.roomWidth = maxW;
        super.index = 1;
    }

}
