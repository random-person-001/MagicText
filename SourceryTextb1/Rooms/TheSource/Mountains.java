package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.GameObjects.*;
import SourceryTextb1.GameObjects.TheSource.Wolf;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.art;

/**
 * Created by riley on 15-Jun-2016.
 */
public class Mountains extends Room {
    Layer baseLayer;
    private int maxH;
    private int maxW;


    private String loop() {
        int count = 0;
        boolean warnedOfEdge = false;
        while (exitCode.equals("")) {
            try {
                org.compileImage();
                Thread.sleep(20);

                if (count == 0) {
                    textBox(new FlavorText("Ah, the air is nice and fresh\n outside.", ""));
                    textBox(new FlavorText("Endless rows of trees covered in sunset\n sitting beyond your view distance....", ""));
                    textBox(new FlavorText("A picture taken here on this cliff\n would definitely make a great postcard.", ""));
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

                if (playo.getX() == 91 && playo.getY() == 44){
                    playo.dead = true;
                }
                if (playo.dead){
                    exitCode = "die";
                }
                if (playo.getY() < 0){
                    setNewRoom("SourcePit",57,42);
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

    public void startup() {
        ititHitMeshes();
        String[][] base = art.strToArray(arty.mountainPlace);
        String[] solids = {":", "^", "#",".","0","o"};
        addToBaseHitMesh(base, solids);
        baseLayer = new Layer(base, "backgronud");
        org.addLayer(baseLayer);

        int[][] locs = {{106, 115},{14, 17}};
        for (int ii = 0 ; ii < locs[0].length ; ii++){
            Wolf wolf = new Wolf(org, this, locs[0][ii], locs[1][ii]);
            addMortal(wolf);
        }

        genericRoomInitialize();

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
    public String enter() {
        org.compileImage();
        playo.frozen = false;
        String exit = loop();
        playo.frozen = true;
        if (!exit.equals("die")) {
            removeAllObjectsAndLayersButPlayer();
        }
        return exit;
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
