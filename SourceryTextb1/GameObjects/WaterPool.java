package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;

/**
 * Created by Jared on 10/1/2016.
 */
public class WaterPool extends GameObject{
    private Layer puddle;
    private boolean[][] puddleData;

    public WaterPool(Room creator, Layer set , ImageOrg org, int xSet, int ySet){
        puddle = set;
        puddleData = new boolean[puddle.getColumns()][puddle.getRows()];
        for (int c = 0; c < puddle.getColumns(); c++){
            for (int r = 0; r < puddle.getRows(); r++){
                if (puddle.getStr(r,c).equals("W")) {
                    puddleData[c][r] = true;
                    puddle.setSpecTxt(r, c, new SpecialText("~", null, new Color(65, 65, 200)));
                }
            }
        }
        String debugOutput = "[Water] Boolean matrix:\n";

        for (int r = 0; r < puddleData[0].length; r++){
            for (int c = 0; c < puddleData.length ; c++){
                if (puddleData[c][r]){
                    debugOutput += "1";
                } else {
                    debugOutput += "0";
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

        setupTimer(100);
    }

    //int iter = 0;
    public void update(){
        Player player = room.getPlayer();
        //System.out.printf("[Water]: xdif %1$d ydif %2$d (%3$d,%4$d)\n", player.getX() - x, player.getY() - y, x, y);
        int xdif = player.getX() - x;
        int ydif = player.getY() - y;

        if (xdif < puddleData.length && xdif >= 0 && ydif < puddleData[0].length && ydif >= 0 && puddleData[xdif][ydif]){
            //System.out.printf("[Water] Instance %1$d\n", iter);
            //iter++;
            if (!player.swimming){
                player.waterEntry = 3;
                player.swimming = true;
            }
        } else {
            player.swimming = false;
        }

    }
}
