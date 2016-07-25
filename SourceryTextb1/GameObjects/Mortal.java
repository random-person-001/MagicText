package SourceryTextb1.GameObjects;

import java.util.ArrayList;

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
     * createPathTo, spreadPathPts, and withinDist are all methods used in a path-finding algorithm I found on the
     *  internet.
     *
     * Follow this link:
     *  https://en.wikipedia.org/wiki/Pathfinding
     *
     *  and go to "Sample Algorithm"
     *  It explains how it should work.
     */
    public ArrayList<PathPoint> pathPts = new ArrayList<>();
    protected void createPathTo(int goalX, int goalY, int maxDist){
        pathPts.clear();
        pathPts.add(new PathPoint(goalX, goalY, 0));
        boolean pathSuccess = false;
        for (int ii = 0; ii < maxDist; ii++){
            for (PathPoint pt : pathPts){
                if (spreadPathPts(pt.getX(), pt.getY(), x, y, ii)){
                    ii = maxDist;
                    pathSuccess = true;
                    break;
                }
            }
        }
        if (!pathSuccess){
            pathPts.clear();
        }
    }

    protected boolean spreadPathPts(int pX, int pY, int sX, int sY, int counter){
        boolean stop = false;
        if (room.isPlaceSolid(pX - 1, pY)){
            pathPts.add(new PathPoint(pX - 1, pY, counter));
        }
        if (room.isPlaceSolid(pX + 1, pY)){
            pathPts.add(new PathPoint(pX + 1, pY, counter));
        }
        if (room.isPlaceSolid(pX, pY - 1)){
            pathPts.add(new PathPoint(pX, pY - 1, counter));
        }
        if (room.isPlaceSolid(pX, pY + 1)){
            pathPts.add(new PathPoint(pX, pY + 1, counter));
        }
        if ((pX - 1 == sX && pY == sY) || (pX + 1 == sX && pY == sY)
                || (pX == sX && pY - 1 == sY) || (pX == sX && pY + 1 == sY)){
            stop = true;
        }
        return stop;
    }

    protected boolean withinDist(int x1, int y1, int x2, int y2, int range){
        return (Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)) < range);
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

        protected int getX(){ return x; }

        protected int getY(){ return y; }

        protected int getCntr(){ return counter; }
    }
}
