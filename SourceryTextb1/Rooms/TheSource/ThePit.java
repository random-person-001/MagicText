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
 * The initial view of the Source Pit.
 * @author 119184
 *
 * So Far:
 *  > You have been introduced to the backstory
 *  > You've mastered the Tutorial Basement
 *
 * What Generally Happens Here:
 *  > The owner of the house is sitting at the edge of the hole. He convinces you to put the world back in order.
 *  > Then you move on to the next level, where a cloned witch has an identity crisis!
 */


public class ThePit extends Room {

    private ImageOrg org;
    private int maxH;
    private int maxW;

    private String loop(){
        String exitCode = "";
        int count = 0;

        while (exitCode.equals("")){
            try {
                Thread.sleep(20);
                //updateObjs(20);
                if (getPlayer().getY() <= 1){
                    exitCode = "Mountains";
                }
                if (getPlayer().getX() == 87 && getPlayer().getY() == 6){
                    System.out.println("Nooo!  You're escaping!");
                    compactTextBox(org, "Hey!  You're escaping!\n Don't you know you shouldn't\n do that?","THE CODE",false);

                }
                if (super.playo.dead){
                    exitCode = "die";
                }
                org.compileImage();

            } catch (InterruptedException ignored) {}
        }
        return exitCode;
    }

    public void startup(){
        ititHitMeshes();

        super.playo.goTo(109,10);

        art arty = new art();
        String[][] base = art.strToArray(arty.sourcePit);
        String[] solids = {".",",",":",";","^","_","#","'"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test");
        org.addLayer(lay1);

        genericRoomInitialize();
    }

    /**
     * Enter the room. IE, start loops and stuff now.
     */
    public String enter(){
        org.compileImage();
        super.playo.frozen = false;
        String exit = loop();
        super.playo.frozen = true;
        removeAllObjectsAndLayersButPlayer();
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
