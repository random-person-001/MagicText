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
        int timer = 0;
        while (exitCode == 0){
            try {
                Thread.sleep(20);
                //System.out.println("I'm not dead yet! " + ii);
                updateObjs(20);
                playo.update();
                playo.addTime(20);
                if (count == 0){
                    compactTextBox(org, "You should probs get on the ship!", "A Passerby", false);
                    count++;
                }
                if (playo.dead){
                    exitCode = 1;
                }
                playo.reportPos();
                org.compileImage();
                timer++;
                if (timer > 200 && timer % 10 == 0){
                    System.out.println("Moving one");
                    int loc = org.getPosLayer("Dock");
                    Layer docky = org.getLayer(loc);
                    docky.setX(docky.getX()+1);
                    docky.setY(docky.getY()+1);
                    System.out.println(docky.getX());
                    dockUpdate();
                    if (playo.getX() > 42){
                        playo.goTo(playo.getX()+1, playo.getY()+1);
                        if (playo.getX() > maxW || playo.getY() > maxH){ //Later, this might be good to put in Enemy.checkDead()
                            playo.subtractHealth(30);
                        }
                    }
                    for (int i = 43; i<46; i++){ // Just needed the first time, but whatevs.
                        addToBaseHitMesh(42, i);
                        org.editLayer("|", "Boat", i, 42);
                    }
                }

            } catch (InterruptedException ignored) {}
        }
    }

    private void dockUpdate(){
        art arty = new art();
        String[][] dock = art.strToArray(arty.dock);
        String[] docksolids = {"|","-","_"};

        int loc = org.getPosLayer("Dock");
        Layer docky = org.getLayer(loc);

        clearObjHitMesh();
        addToObjHitMesh(dock, docksolids, docky.getY(), docky.getX());
    }

    public void startup(){
        Layer spells = new Layer(new String[maxH][maxW], "Spellz", true);
        org.addLayer(spells);

        playo.goTo(72,51);
        playo.castingLayer = spells;
        playo.setupForNewRoom();


        super.baseHitMesh = new boolean[super.roomHeight][super.roomWidth];
        super.objHitMesh = new boolean[super.roomHeight][super.roomWidth];
        emptyHitMesh();
        art arty = new art();
        String[][] base = art.strToArray(arty.largeBoat);
        String[] solids = {"|","-","\\", "/","_","="};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Boat");
        org.addLayer(lay1);

        String[][] dock = art.strToArray(arty.dock);
        String[] docksolids = {"|","-","_"};
        addToObjHitMesh(dock, docksolids, 40, 27);
        Layer docklayer = new Layer(dock, "Dock", 27, 40);
        org.addLayer(docklayer);

        for (int i = 43; i<46; i++){
            makePlaceNotSolid(42, i);
            removeFromBaseHitMesh(42, i);
            org.editLayer("X", "Boat", i, 42);
        }



        //X: 43  Y: 37

        Layer playerLayer = org.getLayer(org.getPosLayer(playo.getLayerName()));
        org.addLayer(playerLayer);
        addMortal(playo);

        addHUD(org);


        Spike spike = new Spike(org, this, 33, 20);
        spike.setMoveFrq(15);
        addMortal(spike);

        spike = new Spike(org, this, 36, 25);
        spike.setMoveFrq(15);
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
