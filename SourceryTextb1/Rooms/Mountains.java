package SourceryTextb1.Rooms;

import SourceryTextb1.GameObjects.*;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.art;

/**
 * Created by riley on 15-Jun-2016.
 */
public class Mountains extends Room {
    Layer baseLayer;
    private int maxH;
    private int maxW;


    private void loop() {
        int exitCode = 0;
        int count = 0;
        boolean warnedOfEdge = false;
        while (exitCode == 0) {
            try {
                org.compileImage();
                Thread.sleep(20);
                //System.out.println("I'm not dead yet! " + ii);
                updateObjs(20);
                playo.update();
                playo.reportPos();
                playo.addTime(20);
                if (count == 0) {
                    compactTextBox(org, "Welcome outside!  Breathe a breath of \n fresh air, and don't fall.", "A Sign", false);
                    count++;
                }
                if (baseLayer.getStr(playo.getY(), playo.getX()).equals(".")){ // Walking the critical edge
                    playo.hurt(1, "Do.  Not.  Walk.  The critical edge.");
                    if (!warnedOfEdge) {
                        compactTextBox(org, "Don't walk the critical edge!", "", false);
                        warnedOfEdge = true;
                    }
                }
                enemies.forEach(this::checkMortalBelowEdge); // OK, intelliJ.  Sure.

                if (playo.getX() == 91 && playo.getY() == 44){
                    exitCode = 1;
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void checkMortalBelowEdge(Mortal m){
        int numberOfDotsAbove = 0;
        for (int i=0; i<m.getY(); i++){
            if (baseLayer.getStr(i, m.getX()).equals(".")){
                System.out.println("You're below the edge");
                numberOfDotsAbove ++;
            }
        }
        if (numberOfDotsAbove % 2 == 1) {
            m.goTo(m.getX(), m.getY() + 2);
            m.subtractHealth(3, "You fell off of a cliff!");
        }
    }

    public void startup() {
        ititHitMeshes();
        String[][] base = art.strToArray(arty.mountainPlace);
        String[] solids = {":", "^", "#"};
        addToBaseHitMesh(base, solids);
        baseLayer = new Layer(base, "backgronud");
        org.addLayer(baseLayer);

        /*
        makeTrollAt(58, 7);
        makeTrollAt(56, 7);
        makeTrollAt(36, 15);
        makeTrollAt(62, 7);
        makeTrollAt(70, 4);
        makeTrollAt(72, 4);
        */

        genericRoomInitialize();
        playo.goTo(9, 6);
    }

    /*
    private void makeTrollAt(int x, int y){
        DroppedItem d = new DroppedItem(this, org, "None", "TrollDrop", "droppedTrollStuff", " ", x, y);
        addMortal(new Troll(org, this, x, y, d));
    }
    */


    /**
     * Enter the room. IE, start loops and stuff now.
     */
    public void enter() {
        org.compileImage();
        playo.frozen = false;
        loop();
        playo.frozen = true;
        super.cleanLayersForExit(org);
    }

    public Mountains(ImageOrg orgo, Player player) {
        playo = player;
        org = orgo;
        super.org = orgo;
        maxH = org.getWindow().maxH();
        maxW = org.getWindow().maxW();
        super.roomHeight = maxH;
        super.roomWidth = maxW;
        super.index = 5;
    }
}
