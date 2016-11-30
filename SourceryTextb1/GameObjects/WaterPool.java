package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;
import java.util.Random;

/**
 * Created by Jared on 10/1/2016.
 */
public class WaterPool extends GameObject {
    private Layer puddle;
    private boolean[][] puddleData;

    private int[] waveXs;
    private int[] waveYs; //Welcome to wavey's, how can I take your order?
    private int[] waveTimers;

    public WaterPool(Room creator, Layer set, ImageOrg org, int xSet, int ySet) {
        puddle = set;
        int puddleSize = 0;
        puddleData = new boolean[puddle.getColumns()][puddle.getRows()];
        for (int c = 0; c < puddle.getColumns(); c++) {
            for (int r = 0; r < puddle.getRows(); r++) {
                if (puddle.getStr(r, c).equals("W")) {
                    puddleData[c][r] = true;
                    puddle.setSpecTxt(r, c, new SpecialText(" ", null, new Color(65, 65, 200)));
                    puddleSize++;
                }
            }
        }
        int waveCount = (puddleSize / 10) + 2; //Scales number of waves present with size of pool
        waveXs = new int[waveCount];
        waveYs = new int[waveCount];
        waveTimers = new int[waveCount];

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
        room = creator;
        x = xSet;
        y = ySet;
        puddle.setPos(y, x);
        orgo = org;
        orgo.addLayer(puddle);

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
                    puddle.setSpecTxt(waveYs[ii], waveXs[ii], new SpecialText(" ", null, new Color(65, 65, 200)));
                }
                waveTimers[ii] = rand.nextInt(6) + 2; //Then gives the wave a new (pseudo-random) time to wait before 'despawning' again
                waveXs[ii] = rand.nextInt(puddleData.length); //Creates new x and y coords for pool
                waveYs[ii] = rand.nextInt(puddleData[0].length);
                if (puddleData[waveXs[ii]][waveYs[ii]]) { //Checks if new location is in the pool
                    puddle.setSpecTxt(waveYs[ii], waveXs[ii], new SpecialText("~", new Color(0, 117, 200), new Color(65, 65, 200)));
                }
            } else {
                waveTimers[ii]--; //Otherwise counts the timer down
            }
        }
        for (Player player : room.players) {
            //System.out.printf("[Water]: xdif %1$d ydif %2$d (%3$d,%4$d)\n", player.getX() - x, player.getY() - y, x, y);
            int xdif = player.getX() - x;
            int ydif = player.getY() - y;

            if (xdif < puddleData.length && xdif >= 0 && ydif < puddleData[0].length && ydif >= 0 && puddleData[xdif][ydif]) {
                //System.out.printf("[Water] Instance %1$d\n", iter);
                //iter++;
                if (!player.swimming) {
                    player.waterEntry = 2;
                    player.swimming = true;
                }
            } else {
                player.swimming = false;
            }
        }
    }

    /**
     * Returns if there is water at a given x and y coordinate
     *
     * @param absX "absolute X" as in x,y of rooms.
     * @param absY "absolute Y" (see above)
     */
    public boolean isWaterHere(int absX, int absY) {
        int xdif = absX - x;
        int ydif = absY - y;

        return (xdif < puddleData.length && xdif >= 0 && ydif < puddleData[0].length && ydif >= 0 && puddleData[xdif][ydif]);
    }
}
