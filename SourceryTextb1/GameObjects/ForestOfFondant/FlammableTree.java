package SourceryTextb1.GameObjects.ForestOfFondant;

import SourceryTextb1.GameObjects.GameObject;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;
import java.util.Random;

/**
 * A Class that handles making trees (technically, certain SpecialTexts) of a Layer flammable.
 * Created by riley on 02-Dec-2016.
 */
public class FlammableTree extends GameObject {
    private Color[] stagesOfFire = {new Color(184, 222, 85), new Color(222, 138, 0), new Color(222, 202, 0), new Color(222, 61, 24),
            new Color(167, 83, 1), new Color(106, 48, 0), new Color(70, 44, 31), new Color(40, 40, 40)};
    private String[] charactersOfFire = {"W", "M", "w", "m", "_", "w", ".", " "};

    private Random rand = new Random();
    private int [][] treeData; // -1=no tree   0=pristine tree   1-7=various stages of burning
    private String layerName;
    private int chanceOfSpreadingInEachDirection = 30; //  40;
    public FlammableTree(Room room, Layer layWithTrees, SpecialText[] treeChars) {
        this.room = room;
        super.strClass = "FlammableTree";
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
        setupTimer(100);
    }

    public void burn(int x, int y) {
        Layer treeLayer = room.org.getLayer(layerName);
        if (treeLayer != null) {
            int relativeY = y-treeLayer.getX();
            int relativeX = x-treeLayer.getY();
            int maxRelX = treeData.length;
            int maxRelY = treeData[0].length;
            boolean withinBounds = relativeX >= 0 && relativeX < maxRelX && relativeY >= 0 && relativeY < maxRelY;
            //System.out.println("[FlammableTree] attempting (abs " + x + ", " + y + ") rel " + relativeX + ", " + relativeY + " for " + layerName +
            //        " | max relative = " + maxRelX + ", " + maxRelY + ", so " + withinBounds );
            if (withinBounds && treeData[relativeX][relativeY] == 0) {
                //System.out.println("And it burns!");
                room.removeFromBaseHitMesh(x,y);
                treeData[relativeX][relativeY] = 1;
                // Maybe start a forest fire!
                if (r(100) <= chanceOfSpreadingInEachDirection){
                    burn(x+1, y);
                }
                if (r(100) <= chanceOfSpreadingInEachDirection){
                    burn(x-1, y);
                }
                if (r(100) <= chanceOfSpreadingInEachDirection){
                    burn(x, y+1);
                }
                if (r(100) <= chanceOfSpreadingInEachDirection){
                    burn(x, y-1);
                }
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
            if (currentState < 3){
                room.hurtSomethingAt(dataC + treeLayer.getY(), dataR + treeLayer.getX(),2,"You were burnt to a crisp");
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
