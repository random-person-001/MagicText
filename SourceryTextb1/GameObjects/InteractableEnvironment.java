package SourceryTextb1.GameObjects;

import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Things like burnable trees and snow extend this class
 * Created by riley on 07-Dec-2016.
 */
public class InteractableEnvironment extends GameObject{
    private Random rand = new Random();
    protected int [][] data; // -1=no thing   0=pristine thing   1-7=various stages of going away
    protected String layerName;
    protected Layer layerWithStuff;
    protected int chanceOfSpreadingInEachDirection = 10;
    protected int maxRelX;
    protected int maxRelY;
    protected boolean instaSpread = true;
    protected int maxAnimation = 7;
    protected List<String> spellTypesToCareAbout = new ArrayList<>();

    public InteractableEnvironment(Room room, Layer layWithStuff, SpecialText[] relavantChars) {
        this.room = room;
        super.strClass = "SnowPatch";
        this.layerName = layWithStuff.getName();
        this.layerWithStuff = layWithStuff;
        this.data = new int [layWithStuff.getColumns()][layWithStuff.getRows()];
        for (int c = 0; c < layWithStuff.getColumns(); c++) {
            for (int r = 0; r < layWithStuff.getRows(); r++) {
                for (SpecialText treeChar : relavantChars) {
                    if (layWithStuff.getSpecTxt(r, c).equals(treeChar)) {
                        data[c][r] = 0;
                        break;
                    } else {
                        data[c][r] = -1;
                    }
                }
            }
        }
        maxRelX = data.length;
        maxRelY = data[0].length;
        setupTimer(100);
        room.addAnInteractableEnvironment(this);
    }

    /**
     * Method called for each place in the data, called from the GameObject.update()
     * Override this.
     */
    protected void advanceAnimation(int c, int r, int currentState){}

    @Override
    public void update(){
        for (int c = 0; c < data.length; c++) {
            for (int r = 0; r < data[0].length; r++) {
                if (data[c][r] >= 1 && data[c][r] < maxAnimation) {
                    advanceAnimation(c,r, data[c][r]);
                }
            }
        }
    }

    public void onSpellOver(int x, int y, String spellType){
        //System.out.println("[" + strClass + "] Checking a " + spellType + " spell at " + x + ", " + y);
        if (!spellTypesToCareAbout.contains(spellType)){
            //System.out.println("Never mind, I don't care about *that* spell type.");
            return;
        }
        int relativeY = y-layerWithStuff.getY();
        int relativeX = x-layerWithStuff.getX();
        boolean withinBounds = relativeX >= 0 && relativeX < maxRelX && relativeY >= 0 && relativeY < maxRelY;
        //System.out.println("[InteractableEnvironment] attempting (abs " + x + ", " + y + ") rel " + relativeX + ", " + relativeY + " for " + layerName +
        //        " | max relative = " + maxRelX + ", " + maxRelY + ", so " + withinBounds );
        if (withinBounds && data[relativeX][relativeY] == 0) {
            //System.out.println("And it melts/burns/whatevers!");
            data[relativeX][relativeY] = 1;
            if (instaSpread){
                // Maybe do nearby things too!
                if (r(100) <= chanceOfSpreadingInEachDirection){
                    onSpellOver(x+1, y, spellType);
                }
                if (r(100) <= chanceOfSpreadingInEachDirection){
                    onSpellOver(x-1, y, spellType);
                }
                if (r(100) <= chanceOfSpreadingInEachDirection){
                    onSpellOver(x, y+1, spellType);
                }
                if (r(100) <= chanceOfSpreadingInEachDirection){
                    onSpellOver(x, y-1, spellType);
                }
            }
            onCustomSpellOver(relativeX, relativeY);
        }
    }

    /**
     * Overridable- it's called when a spell successfully hits a pristine element in the data array.  Lots of stuff
     * is taken care of by an earlier method, so this one can be minimal or even empty
     * @param relativeX the x coord relative to corner of the layer
     * @param relativeY the y coord relative to the corner of the layer
     */
    protected void onCustomSpellOver(int relativeX, int relativeY) {
    }

    /**
     * @param type a string like 'fire' or 'arcane' that, when a spell comes across this InteractableEnvironment, should
     *             change the data there from a zero to a one (or a 'pristine' state to the first stage of animation)
     */
    protected void addSpellTypesToCareAbout(String type){
        spellTypesToCareAbout.add(type);
    }

    protected int r(int max) {
        return r(max, 0);
    }

    protected int r(int max, int min) {
        return rand.nextInt((max - min) + 1) + min;
    }
}
