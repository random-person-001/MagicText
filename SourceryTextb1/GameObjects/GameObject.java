 /*
 * Copyleft 2016 Riley.
 *
 * Licensed under the Epoch Incense, Version 2.0; you may not use this 
 * file except in compliance with the incense.  You may obtain a copy
 * of the incense at
 *
 *      http://www.epoch.org/incenses/INCENSE-2.0
 *
 * Software distributed under the incense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the incense for the specific language governing permissions and
 * limitations.
 */
package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.StrictMath.abs;


 /**
 *
 * @author 119184
 */
public class GameObject implements java.io.Serializable{
    updateTimer updateTimerInstance;
    public String strClass = "None";
    public ImageOrg orgo;
    protected Room room;
    transient Timer timer;
    int frequency;
    
    protected int x;
    protected int y;

    protected AtomicBoolean paused = new AtomicBoolean(false);

    int time; //May be useful when trying to do something asynchronous with room update timings

    /**
     *
     */
    public void sendDisplayData(String layerName, Layer image, int x, int y){
        int loc = orgo.getPosLayer(layerName);
        for (int row = 0; row < image.getRows() ; row++){
            for (int col = 0 ; col < image.getColumns() ; col++){
                orgo.editLayer(image.getStr(row, col), loc, y, x);
            }
        }
    }

    public void addTime(int add){
        time += add;
    }

    public void resetTime(){ time = 0;}

    public int getTime(){
        return time;
    }

    /**
     * Override this method with custom updates.  Usually for display updating
     */
    public void update(){
    }

     /**
      * Override this method with code that will run before it is removed.
      */
    public void selfCleanup(){
    }

    /**
    * * Override this method with custom updates, like in Mortal.java to check if it's dead.
    * I couldn't think of a good name for it.
    */
    public void backgroundUpdate(){
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

     /**
      * pathToPos, createPathTo, spreadPathPts, attemptPoint, and withinDist are all methods used in a path-finding
      * algorithm I found on the internet.
      *
      * Follow this link:
      *  https://en.wikipedia.org/wiki/Pathfinding
      *
      *  and go to "Sample Algorithm"
      *  It explains how it should work.
      */
     protected void pathToPos(int followDist, int gotoX, int gotoY, String layerName) {
         if (withinDist(gotoX, gotoY, x, y, followDist)) {
             room.removeFromObjHitMesh(x, y);
             int stepsNeeded = createPathTo(gotoX, gotoY, followDist);
             //System.out.println(stepsNeeded);
             for (PathPoint pt : pathPts) {
                 if (pt.getCntr() == stepsNeeded - 1 && pt.getCntr() != 0 && abs(pt.getX() - x) <= 1 && abs(pt.getY() - y) <= 1) {
                     x = pt.getX();
                     y = pt.getY();
                     orgo.getLayer(orgo.getPosLayer(layerName)).setPos(y, x);
                     break;
                 }
             }
             room.addToObjHitMesh(x, y);
         }
     }

     private Set<PathPoint> pathPts = new HashSet<>();
     private ArrayList<PathPoint> newPts = new ArrayList<>();
     private int[][] ptMatrix;
     private int createPathTo(int goalX, int goalY, int maxDist){
         pathPts.clear();
         pathPts.add(new PathPoint(goalX, goalY, 0));
         ptMatrix = new int[(maxDist*2) - 1][(maxDist*2) - 1];
         for (int ii = 1; ii <= maxDist; ii++){
             newPts.clear();
             for (PathPoint pt : pathPts){
                 if (pt.getCntr() == ii - 1 && spreadPathPts(pt.getX(), pt.getY(), x, y, goalX, goalY, ii)) {
                     return ii;
                 } else {
                     //orgo.editLayer(String.valueOf(pt.getCntr()).substring(0,1), "Test", pt.getY(), pt.getX());
                 }
             }
             pathPts.addAll(newPts);
         }
         return 60;
     }

     private boolean spreadPathPts(int pX, int pY, int sX, int sY, int gX, int gY, int counter){
         //if ((pX == gX && pY == gY) || (pX == gX && pY > gY) || (pX < gX && pY == gY) || (pX < gX && pY > gY)){
         attemptPoint(pX - 1, pY, counter);
         //}
         //if ((pX == gX && pY == gY) || (pX == gX && pY < gY) || (pX > gX && pY == gY) || (pX > gX && pY < gY)){
         attemptPoint(pX + 1, pY, counter);
         //}
         //if ((pX == gX && pY == gY) || (pX == gX && pY < gY) || (pX < gX && pY == gY) || (pX < gX && pY < gY)){
         attemptPoint(pX, pY - 1, counter);
         //}
         //if ((pX == gX && pY == gY) || (pX > gX && pY == gY) || (pX == gX && pY > gY) || (pX > gX && pY > gY)){
         attemptPoint(pX, pY + 1, counter);
         //}
         return (pX - 1 == sX && pY == sY) || (pX + 1 == sX && pY == sY) || (pX == sX && pY - 1 == sY) || (pX == sX && pY + 1 == sY);
     }

     private void attemptPoint(int x, int y, int counter){
         if ((!room.isPlaceSolid(x, y))){
             newPts.add(new PathPoint(x, y, counter));
         }
     }

     private boolean withinDist(int x1, int y1, int x2, int y2, int range){
         double number = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
         //System.out.println(number);
         return (number < range);
     }

     private class PathPoint implements java.io.Serializable {
         private int x;
         private int y;
         private int counter;

         PathPoint(int theX, int theY, int theCounter){
             x = theX;
             y = theY;
             counter = theCounter;
         }

         @Override
         public boolean equals(Object obj){
             if (obj == this) {
                 return true;
             }
             if (!(obj instanceof PathPoint)) {
                 return false;
             }
             PathPoint other = (PathPoint)obj;
             return (x == other.getX() && y == other.getY());
         }

         //Extremely simple hash. Numbers 47 and 29 can be replaced by any prime #, but must exist in this pattern/format
         @Override
         public int hashCode() {
             int hash = 47;
             hash = hash*29 + Integer.hashCode(x);
             hash = hash*29 + Integer.hashCode(y);
             return hash;
         }

         protected int getX(){ return x; }

         protected int getY(){ return y; }

         int getCntr(){ return counter; }
     }

    public void setupTimer(int theFrequency){
        cancelTimer();
        frequency = theFrequency;
        timer = new Timer();
        updateTimerInstance = new updateTimer(frequency);
        timer.scheduleAtFixedRate(updateTimerInstance, frequency, frequency);
    }

    public void cancelTimer(){
        try {
            timer.cancel();
            timer.purge();
        }
        catch (NullPointerException ignore){}
    }

    public void setPause(boolean set){
        paused.set(set);
    }

    class updateTimer extends TimerTask implements java.io.Serializable {
        int freq;

        public updateTimer(int frequency){
            freq = frequency;
        }

        public void run(){
            if (!paused.get()) {
                update();
                backgroundUpdate();
                addTime(freq);
            }
        }
    }
}
