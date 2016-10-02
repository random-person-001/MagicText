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
                    puddle.setSpecTxt(r, c, new SpecialText("~", null, new Color(75, 75, 255)));
                }
            }
        }
        room = creator;
        x = xSet;
        y = ySet;
        puddle.setPos(x, y);
        orgo = org;
        orgo.addLayer(puddle);

        setupTimer(100);
    }

    public void update(){
        Player player = room.getPlayer();
        if (player != null && puddleData[player.getY()][player.getX()]){
            System.out.println("Water!");
        }
    }
}
