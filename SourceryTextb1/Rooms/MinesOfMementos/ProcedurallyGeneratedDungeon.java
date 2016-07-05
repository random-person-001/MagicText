package SourceryTextb1.Rooms.MinesOfMementos;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.art;


/**
 * Created by riley on 21-Jun-2016.
 *
 * The dungeon is procedurally generated, and might actually end someday.
 * If it doesn’t, or it’s blocked due to a ‘glitch,’ (cough cough definitely
 * not intentional) you can always consider some alternatives to smashing
 * your keyboard and screaming, such as starting back at the beginning.
 *
 * When it ends as intended, you walk out next to The Source, ready to use your rope.
 */
public class ProcedurallyGeneratedDungeon extends Room{
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
                    compactTextBox(org, "Welcome to the Procedurally Generated \n Dungeon.", "", false);
                    count++;
                }
                if (playo.dead){
                    exitCode = 1;
                }
                playo.reportPos();
                org.compileImage();

            } catch (InterruptedException ignored) {}
        }
    }

    // Some sort of 2d array to keep track of things here?



    public void startup(){
        ititHitMesh();
        playo.goTo(5,5);
        //String[][] base = art.strToArray(arty.largeBoat);
        //String[] solids = {"|","-","\\", "/","_","="};
        //addToBaseHitMesh(base, solids);
        //Layer lay1 = new Layer(base, "Boat");
        //org.addLayer(lay1);

        genericInitialize();
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

    public ProcedurallyGeneratedDungeon(ImageOrg orgo, Player player){
        playo = player;
        org = orgo;
        maxH = org.getWindow().maxH();
        maxW = org.getWindow().maxW();
        super.roomHeight = maxH;
        super.roomWidth = maxW;
        super.index = 4;
    }

}
