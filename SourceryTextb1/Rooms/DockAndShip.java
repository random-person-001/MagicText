package SourceryTextb1.Rooms;

import SourceryTextb1.GameObjects.Mortal;
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
                if (count == 1 && playo.getX() == 23 && playo.getY() == 1){
                    compactTextBox(org, "Ahoy!  This is the front of the ship. \n ", "Seagull", false);
                    count++;
                }
                if (playo.dead){
                    exitCode = 1;
                }
                playo.reportPos();
                org.compileImage();
                timer++;
                if (timer > 200 && timer < 1000 && timer % 10 == 0){
                    System.out.println("Moving one");
                    int loc = org.getPosLayer("Dock");
                    Layer docky = org.getLayer(loc);
                    docky.setX(docky.getX()+1);
                    docky.setY(docky.getY()+1);
                    System.out.println(docky.getX());
                    dockUpdate();
                    for (Mortal m : enemies) {
                        if (m.getX() > 42) {
                            m.goTo(m.getX() + 1, m.getY() + 1);
                            if (m.getX() > maxW || m.getY() > maxH) { //Later, this might be good to put in Enemy.checkDead()
                                m.subtractHealth(30, "You were not long for this world. \n You *Were* told to get on the ship.");
                            }
                            if (count == 1 && m.strClass.equals("Player")) {
                                compactTextBox(org, "You missed the ship!  I told you not to.", "THE PASSERBY", false);
                                count++;
                            }
                        }
                    }
                    for (int i = 43; i<46; i++){ // Just needed the first time, but whatevs.
                        addToBaseHitMesh(42, i);
                        addToBaseHitMesh(43, i); // So you can't walk off the side
                        org.editLayer("|", "Boat", i, 42);
                    }
                }
                if (timer > 2000){
                    System.out.println("It's been a long while.");
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

        makeSpikeAt(30, 20); // Around front of boat
        makeSpikeAt(36, 25);

        makeSpikeAt(51, 43);
        makeSpikeAt(52, 45);

    }

    private void makeSpikeAt(int x, int y){
        Spike spike = new Spike(org, this, x, y);
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
