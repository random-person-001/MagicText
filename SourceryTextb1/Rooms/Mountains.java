package SourceryTextb1.Rooms;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.Spike;
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
                    compactTextBox(org, "Don't walk the critical edge!", "", false);
                    playo.hurt(1, "Do.  Not.  Walk.  The critical edge.");
                }
                if (playerBelowEdge()){
                    playo.goTo(playo.getX(), playo.getY()+2);
                    playo.hurt(3, "You fell off of a cliff!");
                }
                if (playo.getX() == 91 && playo.getY() == 44){
                    exitCode = 1;
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    private boolean playerBelowEdge(){
        int numberOfDotsAbove = 0;
        for (int i=0; i<playo.getY(); i++){
            if (baseLayer.getStr(i, playo.getX()).equals(".")){
                System.out.println("You're below the edge");
                numberOfDotsAbove ++;
            }
        }
        return numberOfDotsAbove % 2 == 1;
    }

    public void startup() {
        ititHitMesh();
        String[][] base = art.strToArray(arty.mountainPlace);
        String[] solids = {":", "^", "#"};
        addToBaseHitMesh(base, solids);
        baseLayer = new Layer(base, "backgronud");
        org.addLayer(baseLayer);

        genericInitialize();
        playo.goTo(9, 6);
    }


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
