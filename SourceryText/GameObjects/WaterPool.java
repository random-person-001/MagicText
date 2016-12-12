package SourceryText.GameObjects;

import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;
import java.util.Random;

/**
 * Created by Jared on 10/1/2016.
 */
public class WaterPool extends GameObject {
    private Layer puddle;
    private Color waveColor = new Color(3, 188, 222);
    private Color thisWaterColor= new Color(19, 91, 145); // same as depth=2

    private boolean[][] puddleData;

    private int[] waveXs;
    private int[] waveYs; //Welcome to wavey's, how can I take your order?
    private int[] waveTimers;

    public WaterPool(Room creator, Layer set, String waterChar, int depth){
        this(creator, set, set.getX(), set.getY(), waterChar);
        switch (depth){
            case 1:
                thisWaterColor = new Color(19, 103, 145);
                break;
            case 2:
                thisWaterColor = new Color(19, 91, 145);
                break;
            case 3:
                thisWaterColor = new Color(24, 67, 145);
                break;
            case 4:
                thisWaterColor = new Color(21, 47, 145);
                break;
            default:
                System.out.println("[WaterPool constructor]: depth of " + depth + " not supported!");
        }
        // Update our layer stuff.
        for (int r = 0; r < puddleData[0].length; r++) {
            for (int c = 0; c < puddleData.length; c++) {
                if (puddleData[c][r]) {
                    puddle.setSpecTxt(r, c, new SpecialText(" ", waveColor, thisWaterColor));
                }
            }
        }
    }

    public WaterPool(Room creator, Layer set, int xSet, int ySet) {
        this(creator, set, xSet, ySet, "W");
    }
    public WaterPool(Room creator, Layer set, int xSet, int ySet, String waterChar) {
        super.strClass = "WaterPool";
        room = creator;
        x = xSet;
        y = ySet;
        org = room.org;

        puddle = set;
        int puddleSize = 0;
        puddleData = new boolean[puddle.getColumns()][puddle.getRows()];
        for (int c = 0; c < puddle.getColumns(); c++) {
            for (int r = 0; r < puddle.getRows(); r++) {
                if (puddle.getStr(r, c).equals(waterChar)) {
                    puddleData[c][r] = true;
                    room.addWaterAt(c+x,r+y);
                    puddle.setSpecTxt(r, c, new SpecialText(" ", null, thisWaterColor));
                    puddleSize++;
                }
            }
        }
        int waveCount = (puddleSize / 10) + 2; //Scales number of waves present with size of pool
        waveXs = new int[waveCount];
        waveYs = new int[waveCount];
        waveTimers = new int[waveCount];

        /*
        String debugOutput = "[Water] Boolean matrix:\n";

        for (int r = 0; r < puddleData[0].length; r++) {
            for (int c = 0; c < puddleData.length; c++) {
                if (puddleData[c][r]) {
                    debugOutput += "~";
                } else {
                    debugOutput += "#";
                }
            }
            debugOutput += "\n";
        }
        System.out.println(debugOutput);
*/
        puddle.setPos(x, y);
        org.addLayer(puddle);

        super.strClass = "WaterPool";

        setupTimer(100);
    }

    //int iter = 0;
    public void update() {
        // Waves move across surface
        Random rand = new Random();
        for (int ii = 0; ii < waveTimers.length; ii++) {
            if (waveTimers[ii] == 0) { //Checks for every wave if it supposed to 'despawn'
                if (puddleData[waveXs[ii]][waveYs[ii]]) { //First off removes the wave from where it is now
                    puddle.setSpecTxt(waveYs[ii], waveXs[ii], new SpecialText(" ", null, thisWaterColor));
                }
                waveTimers[ii] = rand.nextInt(6) + 2; //Then gives the wave a new (pseudo-random) time to wait before 'despawning' again
                waveXs[ii] = rand.nextInt(puddleData.length); //Creates new x and y coords for pool
                waveYs[ii] = rand.nextInt(puddleData[0].length);
                if (puddleData[waveXs[ii]][waveYs[ii]]) { //Checks if new location is in the pool
                    puddle.setSpecTxt(waveYs[ii], waveXs[ii], new SpecialText("~", waveColor, thisWaterColor));
                }
            } else {
                waveTimers[ii]--; //Otherwise counts the timer down
            }
        }
    }
}
