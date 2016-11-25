package SourceryTextb1.Rooms.MinesOfMementos;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.Rooms.Room;


/**
 * Created by riley on 21-Jun-2016.
 * <p>
 * The dungeon is procedurally generated, and might actually end someday.
 * If it doesn’t, or it’s blocked due to a ‘glitch,’ (cough cough definitely
 * not intentional) you can always consider some alternatives to smashing
 * your keyboard and screaming, such as starting back at the beginning.
 * <p>
 * When it ends as intended, you walk out next to The Source, ready to use your rope.
 */
public class ProcedurallyGeneratedDungeon extends Room {


    public ProcedurallyGeneratedDungeon(Player player) {
        super(player);
        strRoomName = "ProcedurallyGeneratedDungeon";
    }

    protected String loop(Player play) {
        int count = 0;
        while (exitCode.equals("")) {
            try {
                Thread.sleep(20);
                if (count == 0) {
                    compactTextBox("Welcome to the Procedurally Generated \n Dungeon.", "", false);
                    count++;
                }
            } catch (InterruptedException ignored) {
            }
        }
        return exitCode;
    }

    // Some sort of 2d array to keep track of things here?

    @Override
    public void startup() {
        //initHitMeshes();
        playo.goTo(5, 5);
        //String[][] base = Art.strToArray(arty.largeBoat);
        //String[] solids = {"|","-","\\", "/","_","="};
        //addToBaseHitMesh(base, solids);
        //Layer lay1 = new Layer(base, "Boat");
        //org.addLayer(lay1);

        genericRoomInitialize();
    }
}
