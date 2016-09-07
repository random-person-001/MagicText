/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms;

import SourceryTextb1.GameObjects.*;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
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
 *
 *      > The player familiarizes with the player-controlled character and learns how to navigate a text-based environment
 *      (Hence the pile of junk)
 *      > An innocent pot of petunias is murdered by you, only because the developers felt like playing a cruel god
 *      > Some starter spells are aquired
 */


public class NewTestRoom extends Room {
    private ImageOrg org;
    private int maxH;
    private int maxW;

    protected String loop(){

        while (exitCode == ""){
            try {
                Thread.sleep(20);
                if (getPlayer().getHealth() <= 0){
                    exitCode = "die";
                }
            } catch (InterruptedException ignored) {}
        }
        return exitCode;
    }

    @Override
    public void startup(){
        ititHitMeshes();
        super.playo.goTo(7,5);

        art arty = new art();
        String[][] base = art.strToArray(arty.testRoom);
        String[] solids = {"|","-","0","#"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test", 0, 0, true, false, false);
        org.addLayer(lay1);

        PathingObj pathor = new PathingObj(this, 20, 4);
        addMortal(pathor);

        genericRoomInitialize();
    }

    public NewTestRoom(Player player){
        constructor(player);
        org = player.orgo;
    }
}
