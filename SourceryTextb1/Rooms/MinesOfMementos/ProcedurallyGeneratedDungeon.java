package SourceryTextb1.Rooms.MinesOfMementos;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Rooms.Room;


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


    protected String loop(){
        int count = 0;
        while (exitCode == ""){
            try {
                Thread.sleep(20);
                //System.out.println("I'm not dead yet! " + ii);

                playo.update();
                playo.addTime(20);
                if (count == 0){
                    compactTextBox(org, "Welcome to the Procedurally Generated \n Dungeon.", "", false);
                    count++;
                }
                if (playo.dead){
                    exitCode = "die";
                }
                playo.reportPos();
                org.compileImage();

            } catch (InterruptedException ignored) {}
        }
        return exitCode;
    }

    // Some sort of 2d array to keep track of things here?



    public void startup(){
        ititHitMeshes();
        playo.goTo(5,5);
        //String[][] base = art.strToArray(arty.largeBoat);
        //String[] solids = {"|","-","\\", "/","_","="};
        //addToBaseHitMesh(base, solids);
        //Layer lay1 = new Layer(base, "Boat");
        //org.addLayer(lay1);

        genericRoomInitialize();
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
