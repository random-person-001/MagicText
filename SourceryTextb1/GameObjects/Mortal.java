package SourceryTextb1.GameObjects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static java.lang.StrictMath.abs;

/**
 * Base object (to extend) for Enemies
 * Created by riley on 12-Jun-2016.
 */
public class Mortal extends GameObject {
    protected String layerName;
    private int health = 10;
    public int maxHealth = 50;
    int attack = 0;
    private String deathMessage = "Unknown";
    private boolean isGoodGuy = false;

    public String getLayerName(){
        return layerName;
    }

    public int getHealth(){
        return health;
    }

    public void subtractHealth(int amountLost, String message){
        if (strClass.equals("Player")){
            int damage = (amountLost - room.playo.defense);
            if (damage < 1) {
                damage = 1;
            }
            health -= damage;
            room.playo.showPain(message);
        } else {
            //System.out.println("My health is now " + getHealth());
            health -= amountLost;
        }
    }

    public void subtractHealth(int amountLost){
        subtractHealth(amountLost, "Your killer is unknown.");
    }

    public void setHealth(int newHealth){
        health = newHealth;
    }

    public void restoreHealth( int addHP){
        restoreHealth(addHP, 0);
    }

    public void restoreHealth (int addHP, int maxOverHeal) {
        health += addHP;
        int newMax = maxHealth + maxOverHeal;
        if (newMax > maxHealth * 2){
            newMax = maxHealth * 2;
        }
        if (health > newMax){
            health = newMax;
        }
    }

    public int getAttack(){
        return attack;
    }
    public void setAttack(int newAttack){
        attack = newAttack;
    }

    public boolean isGoodGuy(){ return isGoodGuy; }
    public void makeGoodGuy(){ isGoodGuy = true; }

    protected boolean checkDeath() {
        if (getHealth() <= 0){
            room.removeMortal(this);
            try {
                orgo.editLayer(" ", layerName, y, x);
            }catch (NullPointerException ignore){}
            return true;
        }
        return false;
    }

    public void goTo(int newX, int newY) {
        orgo.editLayer(" ", layerName, y, x);
        x = newX;
        y = newY;
    }

    protected int distanceTo(GameObject m){
        return abs(x-m.getX()) + abs(y-m.getY());
    }


    protected Mortal getClosestGoodGuy(){
        int closest = 50000000;
        Mortal closestM = null;
        for (Mortal m : room.enemies) {
            if (m.isGoodGuy()) {
                if (distanceTo(m) < closest) {
                    closest = distanceTo(m);
                    closestM = m;
                }
            }
        }
        return closestM;
    }

    /**
     * pathToPos, createPathTo, spreadPathPts, attemptPoint, and withinDist are all methods used in a path-finding algorithm I found on the
     *  internet.
     *
     * Follow this link:
     *  https://en.wikipedia.org/wiki/Pathfinding
     *
     *  and go to "Sample Algorithm"
     *  It explains how it should work.
     */
    protected void pathToPos(int followDist, int gotoX, int gotoY) {
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

    public Set<PathPoint> pathPts = new HashSet<>();
    public ArrayList<PathPoint> newPts = new ArrayList<>();
    protected int[][] ptMatrix;
    protected int createPathTo(int goalX, int goalY, int maxDist){
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

    protected void setPtMatrix (int x, int y, int counter, int matrixSize){
        int matrixAdjust = matrixSize;
    }

    /*
        if ((pX == gX && pY == gY) || (pX == gX && pY > gY) || (pX < gX && pY == gY) || (pX < gX && pY > gY)){
            attemptPoint(pX - 1, pY, counter);
        }
        if ((pX == gX && pY == gY) || (pX == gX && pY < gY) || (pX > gX && pY == gY) || (pX > gX && pY < gY)){
            attemptPoint(pX + 1, pY, counter);
        }
        if ((pX == gX && pY == gY) || (pX == gX && pY < gY) || (pX < gX && pY == gY) || (pX < gX && pY < gY)){
            attemptPoint(pX, pY - 1, counter);
        }
        if ((pX == gX && pY == gY) || (pX > gX && pY == gY) || (pX == gX && pY > gY) || (pX > gX && pY > gY)){
            attemptPoint(pX, pY + 1, counter);
        }
     */

    protected boolean spreadPathPts(int pX, int pY, int sX, int sY, int gX, int gY, int counter){
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
        boolean yes = true;
        /*
        for (PathPoint pt : pathPts){
            if (pt.getX() == x && pt.getY() == y){
                yes = false;
            }
        }
        */
        if (yes && (!room.isPlaceSolid(x, y))){
            newPts.add(new PathPoint(x, y, counter));
        }
    }

    protected boolean withinDist(int x1, int y1, int x2, int y2, int range){
        double number = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        //System.out.println(number);
        return (number < range);
    }

    protected class PathPoint{
        private int x;
        private int y;
        private int counter;

        protected PathPoint(int theX, int theY, int theCounter){
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

        protected int getCntr(){ return counter; }
    }
}
