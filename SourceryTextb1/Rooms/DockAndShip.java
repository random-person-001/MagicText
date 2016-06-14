package SourceryTextb1.Rooms;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.Spike;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.art;

/**
 * Created by riley on 14-Jun-2016.
 */
public class DockAndShip extends Room{
    private ImageOrg org;
    private int maxH;
    private int maxW;


    private void loop(){
        int exitCode = 0;
        int count = 0;
        while (exitCode == 0){
            try {
                Thread.sleep(20);
                //System.out.println("I'm not dead yet! " + ii);
                updateObjs(20);
                playo.update();
                playo.addTime(20);
                if (count == 0){
                    compactTextBox(org, "You should explore the ship!", "", false);
                    count++;
                }
                if (playo.dead){
                    exitCode = 1;
                }
                //playo.reportPos();
                org.compileImage();

            } catch (InterruptedException ignored) {}
        }
    }

    public void startup(){
        Layer spells = new Layer(new String[maxH][maxW], "Spellz", true);
        org.addLayer(spells);

        playo.goTo(7,20);
        playo.castingLayer = spells;
        playo.setupForNewRoom();


        super.baseHitMesh = new boolean[super.roomHeight][super.roomWidth];
        super.objHitMesh = new boolean[super.roomHeight][super.roomWidth];
        emptyHitMesh();
        art arty = new art();
        String[][] base = art.strToArray(arty.largeBoat);
        String[] solids = {"|","-","\\", "/","_","="};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test");
        org.addLayer(lay1);

        Layer playerLayer = org.getLayer(org.getPosLayer(playo.getLayerName()));
        org.addLayer(playerLayer);
        addMortal(playo);

        addHUD(org);


        Spike spike = new Spike(org, this, 36, 20);
        spike.setMoveFrq(5);
        addMortal(spike);

        spike = new Spike(org, this, 36, 25);
        spike.setMoveFrq(5);
        addMortal(spike);
    }

    /**
     * Enter the room. IE, start loops and stuff now.
     */
    public void enter(){
        org.compileImage();
        playo.frozen = false;
        loop();
        playo.frozen = true;
        super.cleanLayersForExit(org);
    }

    public DockAndShip(ImageOrg orgo, Player player){
        playo = player;
        org = orgo;
        maxH = org.getWindow().maxH();
        maxW = org.getWindow().maxW();
        super.roomHeight = maxH;
        super.roomWidth = maxW;
        super.index = 4;
    }
}
