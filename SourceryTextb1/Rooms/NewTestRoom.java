/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.*;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;

/**
 * A room to test code in
 * @author 119184
 *
 * What Generally Happens Here:
 *      > The player can interact with an experimental environment
 */


public class NewTestRoom extends Room {

    protected String loop(Player play){
    public NewTestRoom(Player player){
        super(player);
    }

    protected String loop(){

        while (exitCode.equals("")){
            try {
                Thread.sleep(20);
                if (play.getHealth() <= 0){
                    exitCode = "die";
                }
            } catch (InterruptedException ignored) {}
        }
        return exitCode;
    }

    @Override
    public void startup(){
        ititHitMeshes();
        for (Player p : players) {
            p.goTo(7, 5);
        }

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.testRoom);
        String[] solids = {"|","-","0","#"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test", 0, 0, true, false, false);
        org.addLayer(lay1);

        PathingObj pathor = new PathingObj(this, 18, 7);
        addMortal(pathor);

        genericRoomInitialize();
    }

    public NewTestRoom(Player player){
        constructor(player);
    }
}
