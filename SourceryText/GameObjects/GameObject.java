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
package SourceryText.GameObjects;

import SourceryText.ImageOrg;
import SourceryText.Layer;
import SourceryText.Rooms.Room;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.StrictMath.abs;


/**
 * @author 119184
 */
public class GameObject implements java.io.Serializable {
    updateTimer updateTimerInstance;
    public String strClass = "None";
    public ImageOrg org;
    public Room room;
    private transient Timer timer;
    private int targetUpdateInterval;
    private int currentUpdateInterval = targetUpdateInterval; // Almost always == target one.  Temporal dilation sometimes changes this, though

    protected int x;
    protected int y;

    protected AtomicBoolean paused = new AtomicBoolean(false);

    int time; //May be useful when trying to do something asynchronous with room update timings

    public void addTime(int add) {
        time += add;
    }

    public void resetTime() {
        time = 0;
    }

    public int getTime() {
        return time;
    }

    /**
     * Override this method with custom updates.  Usually for display updating
     */
    public void update() {
    }

    /**
     * Override this method with code that will run before it is removed.
     */
    public void selfCleanup() {
    }

    /**
     * * Override this method with custom updates, like in Mortal.java to check if it's dead.
     * I couldn't think of a good name for it.
     */
    public void backgroundUpdate() {
    }

    /**
     * Override this to do custom actions on inspection.  The inspections x and y coord will be this object's x and y
     * coordinates, so I figured I wouldn't bother passing that along.  For this to be called, don't forget to register
     * with the room as an inspectable first!
     * @param inspector the Player who inspected you
     */
    public void onInspect(Player inspector){
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected int distanceTo(GameObject m) {
        return abs(x - m.getX()) + abs(y - m.getY());
    }

    Mortal getClosestBadGuy(int range) {
        int closest = range;
        Mortal closestM = null;
        for (Mortal m : room.enemies) {
            if (!m.isGoodGuy() && distanceTo(m) < closest) {
                closest = distanceTo(m);
                closestM = m;
            }
        }
        return closestM;
    }

    public void setupTimer(){
        cancelTimer();
        timer = new Timer();
        updateTimerInstance = new updateTimer(currentUpdateInterval);
        timer.scheduleAtFixedRate(updateTimerInstance, currentUpdateInterval, currentUpdateInterval);
    }

    public void setupTimer(int theFrequency) {
        targetUpdateInterval = theFrequency;
        currentUpdateInterval = theFrequency;
        setupTimer();
    }

    /**
     * Are you a potion?  Do you want to speed up or slow down the game?  Call this weird method now!
     * @param intervalMultiplier a coefficient for targetUpdateInterval (the delay in the timer between update() calls)
     */
    public void setTimerToWeirdFrequency(float intervalMultiplier){
        int oldInterval = currentUpdateInterval; // Cuz will probs change
        currentUpdateInterval = (int) (targetUpdateInterval * intervalMultiplier);
        currentUpdateInterval = (currentUpdateInterval > 0) ? currentUpdateInterval : 1;
        if (oldInterval != currentUpdateInterval) {
            setupTimer();
        }
    }

    public void cancelTimer() {
        if (timer != null){
            timer.cancel();
            timer.purge();
        }
    }

    void setPause(boolean set) {
        paused.set(set);
    }

    class updateTimer extends TimerTask implements java.io.Serializable {
        int freq;

        updateTimer(int frequency) {
            freq = frequency;
        }

        public void run() {
            if (!paused.get()) {
                update();
                backgroundUpdate();
                addTime(freq);
            }
        }
    }

    /**
     * pathToPos, createPathTo, spreadPathPts, attemptPoint, and withinDist are all methods used in a path-finding
     * algorithm I found on the internet.
     * <p>
     * Follow this link:
     * https://en.wikipedia.org/wiki/Pathfinding
     * <p>
     * and go to "Sample Algorithm"
     * It explains how it should work.
     */

    protected void pathToPos(int followDist, int gotoX, int gotoY, String layerName) {
        pathToPos(followDist, gotoX, gotoY, layerName, true);
    }

    /**
     * Top-level method (use this one for nearly automatic pathfinding).
     */
    protected void pathToPos(int followDist, int gotoX, int gotoY, String layerName, boolean isSolid) {
        if (isSolid)
            room.removeFromObjHitMesh(x, y);
        int stepsNeeded = createPathTo(gotoX, gotoY, followDist);
        //System.out.println(stepsNeeded);
        for (PathPoint pt : pathPts) {
            if (pt.getCntr() == stepsNeeded - 1 && pt.getCntr() != 0 && abs(pt.getX() - x) <= 1 && abs(pt.getY() - y) <= 1) {
                x = pt.getX();
                y = pt.getY();
                Layer selfLayer = org.getLayer(layerName);
                if (selfLayer != null)
                    selfLayer.setPos(x, y);
                break;
            }
        }
        if (isSolid)
            room.addToObjHitMesh(x, y);
    }

    private Set<PathPoint> pathPts = new HashSet<>();
    private ArrayList<PathPoint> newPts = new ArrayList<>();
    private int[][] ptMatrix;

    /**
     * Creates a long list of points that represent reachable locations within a distance (<= maxDist) of the coordinate (goalX, goalY)
     * @return the amount of steps required to reach a point from where the object stands
     */
    private int createPathTo(int goalX, int goalY, int maxDist) {
        pathPts.clear();
        pathPts.add(new PathPoint(goalX, goalY, 0));
        ptMatrix = new int[(maxDist * 2) - 1][(maxDist * 2) - 1];
        for (int ii = 1; ii <= maxDist; ii++) {
            newPts.clear();
            for (PathPoint pt : pathPts) {
                if (pt.getCntr() == ii - 1 && spreadPathPts(pt.getX(), pt.getY(), x, y, ii))
                    return ii;
            }
            pathPts.addAll(newPts);
        }
        return 60;
    }

    private boolean withinDist(int x1, int y1, int x2, int y2, int range) {
        double number = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        //System.out.println(number);
        return (number < range);
    }

    /**
     * Takes a PathPoint already in the list and creates 4 new PathPoints placed adjacent to it
     * @param pX x-coord of PathPoint
     * @param pY y-coord of PathPoint
     * @param sX x-coord of object trying to pathfind ('self-x')
     * @param sY y-coord of object trying to pathfind ('self-y')
     * @param counter counter integer of PathPoint
     * @return If any point placed is adjacent to object trying to pathfind
     */
    private boolean spreadPathPts(int pX, int pY, int sX, int sY, int counter) {
        attemptPoint(pX - 1, pY, counter);
        attemptPoint(pX + 1, pY, counter);
        attemptPoint(pX, pY - 1, counter);
        attemptPoint(pX, pY + 1, counter);
        return (pX - 1 == sX && pY == sY) || (pX + 1 == sX && pY == sY) || (pX == sX && pY - 1 == sY) || (pX == sX && pY + 1 == sY);
    }

    /**
     * Only places a point if the place is open for movement
     */
    private void attemptPoint(int x, int y, int counter) {
        if ((!room.isPlaceSolid(x, y) && !room.checkForWater(x, y))) {
            newPts.add(new PathPoint(x, y, counter));
        }
    }

    private class PathPoint implements java.io.Serializable {
        private int x;
        private int y;
        private int counter;

        PathPoint(int theX, int theY, int theCounter) {
            x = theX;
            y = theY;
            counter = theCounter;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PathPoint)) {
                return false;
            }
            PathPoint other = (PathPoint) obj;
            return (x == other.getX() && y == other.getY());
        }

        //Extremely simple hash. Numbers 47 and 29 can be replaced by any prime #, but must exist in this pattern/format
        @Override
        public int hashCode() {
            int hash = 47;
            hash = hash * 29 + Integer.hashCode(x);
            hash = hash * 29 + Integer.hashCode(y);
            return hash;
        }

        protected int getX() {
            return x;
        }

        protected int getY() {
            return y;
        }

        int getCntr() {
            return counter;
        }
    }
}
