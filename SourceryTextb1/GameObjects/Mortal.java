package SourceryTextb1.GameObjects;

import static java.lang.StrictMath.abs;

/**
 * Base object (to extend) for Enemies
 * Created by riley on 12-Jun-2016.
 */
public class Mortal extends GameObject {
    protected String layerName;
    private int health = 10;
    private int attack = 0;
    private String deathMessage = "Unknown";
    private boolean isGoodGuy = false;

    public String getLayerName(){
        return layerName;
    }
    public int getHealth(){
        return health;
    }
    public void subtractHealth(int amountLost, String message){
        health -= amountLost;
        if (strClass.equals("Player")){
            room.playo.hurt(message);
        } else {
            System.out.println("My health is now " + getHealth());
        }
    }
    public void subtractHealth(int amountLost){
        subtractHealth(amountLost, "Your killer is unknown.");
    }

    public void setHealth(int newHealth){
        health = newHealth;
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
}
