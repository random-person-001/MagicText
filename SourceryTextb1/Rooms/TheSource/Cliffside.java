package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.GameObjects.*;
import SourceryTextb1.GameObjects.TheSource.Bandit;
import SourceryTextb1.GameObjects.TheSource.Spider;
import SourceryTextb1.GameObjects.TheSource.WeakTower;
import SourceryTextb1.GameObjects.TheSource.Wolf;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.Art;

import java.awt.*;

/**
 * Some mountains, where you fight some enemies
 * Created by riley on 15-Jun-2016.
 */
public class Cliffside extends Room {
    private Layer baseLayer;

    protected String loop() {
        int count = 0;
        boolean warnedOfEdge = false;
        while (exitCode.equals("")) {
            try {
                org.compileImage();
                Thread.sleep(20);

                if (count == 0) {
                    queueMessage(new FlavorText("Ah, the air is nice and fresh\n outside.", ""));
                    queueMessage(new FlavorText("Endless rows of trees covered in sunset\n sitting beyond your view distance....", ""));
                    queueMessage(new FlavorText("A picture taken here on this cliff\n would definitely make a great postcard.", ""));
                    // ^ This should be an item in The Mines of Mementos
                    count++;
                }
                if (baseLayer.getStr(playo.getY(), playo.getX()).equals(".")){ // Walking the critical edge
                    playo.subtractHealth(1, "Do.  Not.  Walk.  The critical edge.");
                    if (!warnedOfEdge) {
                        compactTextBox(org, "Don't walk the critical edge!", "", false);
                        warnedOfEdge = true;
                    }
                }
                enemies.forEach(this::checkMortalBelowEdge); // OK, intelliJ.  Sure.
//
//                if (playo.getX() == 91 && playo.getY() == 44){
//                    playo.dead = true;
//                }
                if (playo.getY() <= 0 && playo.getX() < 20){
                    setNewRoom("SourcePit",57,42);
                }
                if (playo.getY() <= 0 && playo.getX() > 20){
                    setNewRoom("InnerMountains",10,45);
                }
                if (playo.getY() >= 32){
                    setNewRoom("SourceCaves",62,1);
                }
            } catch (InterruptedException ignored) {
            }
        }
        return exitCode;
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

    /**
     * Everything that self-updates and can be paused (and acts nonexistent during paused) should go here.
     */
    @Override
    public void addItems(){
        ititHitMeshes();

        int[][] locs = {{106, 115}, {14, 17}};
        for (int ii = 0 ; ii < locs[0].length ; ii++){
            Wolf wolf = new Wolf(org, this, locs[0][ii], locs[1][ii]);
            addMortal(wolf);
        }

        Spider hiddenMenace = new Spider(this, 56, 7);
        addMortal(hiddenMenace);

        Bandit john = new Bandit(org, this, 132, 11);
        addMortal(john);
        Bandit jack = new Bandit(org, this, 135, 13);
        addMortal(jack);

        int[][] towerLocs = {{124, 14}, {130, 8}};
        for (int[] towerLoc : towerLocs) {
            WeakTower t = new WeakTower(org, this, towerLoc[0], towerLoc[1]);
            addMortal(t);
        }
    }

    @Override
    public void startup() {

        addItems();
        String[][] base = Art.strToArray(arty.mountainPlace);
        String[] solids = {":", "^", "#",".","0","o"};
        addToBaseHitMesh(base, solids);
        baseLayer = new Layer(base, "backgronud");
        baseLayer.influenceAll(new Color(255, 241, 183));
        org.addLayer(baseLayer);

        genericRoomInitialize();
    }

    public Cliffside(Player player) {
        constructor(player);
        org = player.orgo;
    }
}
