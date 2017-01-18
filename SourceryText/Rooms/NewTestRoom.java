/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryText.Rooms;

import SourceryText.Art;
import SourceryText.GameObjects.PathingObj;
import SourceryText.GameObjects.Player;
import SourceryText.Layer;

/**
 * A room to test code in
 *
 * @author 119184
 *         <p>
 *         What Generally Happens Here:
 *         > The player can interact with an experimental environment
 */


public class NewTestRoom extends Room {


    public NewTestRoom(Player player) {
        super(player);
    }

    protected String playerLoop(Player play) {

        while (exitCode.equals("")) {
            try {
                Thread.sleep(20);
                if (play.getHealth() <= 0) {
                    exitCode = "die";
                }
            } catch (InterruptedException ignored) {
            }
        }
        return exitCode;
    }

    @Override
    public void startup() {

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.testRoom);
        String[] solids = {"|", "-", "0", "#"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test", 0, 0, true, false, false);
        org.addLayer(lay1);

        initHitMeshes(lay1);
        for (Player p : players) {
            p.goTo(7, 5);
        }

        PathingObj pathor = new PathingObj(this, 18, 7);
        addMortal(pathor);

        genericRoomInitialize();
    }
}
