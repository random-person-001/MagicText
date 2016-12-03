package SourceryTextb1.GameObjects.ForestOfFondant;

import SourceryTextb1.GameObjects.GameObject;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;
import java.util.Random;

/**
 * A tree that you can burn down.
 * Created by riley on 02-Dec-2016.
 */
public class FlammableTree extends GameObject {
    Color[] stagesOfFire = {new Color(222, 101, 0), new Color(222, 138, 0), new Color(222, 202, 0), new Color(222, 61, 24),
            new Color(167, 83, 1), new Color(106, 48, 0), new Color(70, 44, 31), new Color(40, 40, 40)};
    String[] charactersOfFire = {"W", "M", "w", "m", "_", "w", ".", " "};

    private Random rand = new Random();
    private int [][] treeData;
    private String layerName;
    public FlammableTree(Room room, Layer layWithTrees, SpecialText[] treeChars) {
        this.room = room;
        super.strClass = "FlammableTree";
        super.frequency = 100;
        layerName = layWithTrees.getName();
        treeData = new int [layWithTrees.getColumns()][layWithTrees.getRows()];
        for (int c = 0; c < layWithTrees.getColumns(); c++) {
            for (int r = 0; r < layWithTrees.getRows(); r++) {
                for (SpecialText treeChar : treeChars) {
                    if (layWithTrees.getSpecTxt(r, c).equals(treeChar)) {
                        treeData[c][r] = 0;
                        room.addToBaseHitMesh(c + x, r + y);
                        break;
                    } else {
                        treeData[c][r] = -1;
                    }
                }
            }
        }
        setupTimer(super.frequency);
    }

    public void burn(int x, int y) {
        if (x >= 0 && x < treeData.length && y >= 0 && y < treeData[0].length && treeData[x][y] == 0) {
            Layer treeLayer = room.org.getLayer(layerName);
            if (treeLayer != null) {
                room.removeFromBaseHitMesh(x + treeLayer.getX(), y + treeLayer.getY());
                treeData[x-treeLayer.getY()][y-treeLayer.getX()] = 1;
            }
        }
    }

    public void update(){
        for (int c = 0; c < treeData.length; c++) {
            for (int r = 0; r < treeData[0].length; r++) {
                if (treeData[c][r] >= 1 && treeData[c][r] < 7) {
                    advanceAnimation(c,r);
                }
            }
        }
    }

    private void advanceAnimation(int dataC, int dataR){
        Layer treeLayer = room.org.getLayer(layerName);
        if (treeLayer != null) {
            int currentState = treeData[dataC][dataR];
            String dispChar = charactersOfFire[currentState];
            if (currentState == 6){
                if (r(100)<= 30)
                    dispChar = "_";
                else if (r(100) <= 30)
                    dispChar = ".";
                else
                    dispChar = " ";
            }
            SpecialText newSpTxt = new SpecialText(dispChar, stagesOfFire[currentState], stagesOfFire[currentState + 1]);
            room.org.editLayer(newSpTxt, layerName, dataR, dataC);
        }
        treeData[dataC][dataR] ++;
    }

    protected int r(int max) {
        return r(max, 0);
    }

    protected int r(int max, int min) {
        return rand.nextInt((max - min) + 1) + min;
    }
}
