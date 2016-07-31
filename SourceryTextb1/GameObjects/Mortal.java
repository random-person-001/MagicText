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
    public ArrayList<PathPoint> newPts = new ArrayList<>();
    protected void createPathTo(int goalX, int goalY, int maxDist){
        pathPts.clear();
        pathPts.add(new PathPoint(goalX, goalY, 0));
        for (int ii = 1; ii <= maxDist; ii++){
            newPts.clear();
            for (PathPoint pt : pathPts){
                if (pt.getCntr() == ii - 1) {
                    spreadPathPts(pt.getX(), pt.getY(), x, y, ii);
                    orgo.editLayer(String.valueOf(pt.getCntr()), "Test", pt.getY(), pt.getX());
                }
            }
            pathPts.addAll(newPts);
        }
    }

    protected void spreadPathPts(int pX, int pY, int sX, int sY, int counter){
        attemptPoint(pX - 1, pY, counter);
        attemptPoint(pX + 1, pY, counter);
        attemptPoint(pX, pY - 1, counter);
        attemptPoint(pX, pY + 1, counter);
    }

    private void attemptPoint(int x, int y, int counter){
        boolean yes = true;
        for (PathPoint pt : pathPts){
            if (pt.getX() == x && pt.getY() == y){
                yes = false;
            }
        }
        if (yes && (!room.isPlaceSolid(x, y))){ //This line of code is very close to being grammatically correct
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

        protected int getX(){ return x; }

        protected int getY(){ return y; }

        protected int getCntr(){ return counter; }
    }
}
