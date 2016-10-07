package SourceryTextb1.GameObjects;

import SourceryTextb1.SpecialText;

import java.awt.*;
import java.util.*;

import static java.lang.StrictMath.abs;

/**
 * Base object (to extend) for Enemies
 * Created by riley on 12-Jun-2016.
 */
public class Mortal extends GameObject implements java.io.Serializable{
    protected String layerName;
    private int health = 10;
    int maxHealth = 50;
    private int attack = 0;
    private String deathMessage = "Unknown";
    private boolean isGoodGuy = false;
    private Random rand = new Random();

    protected int r(int max) {
        return r(max, 0);
    }
    protected int r(int max, int min) {
        return rand.nextInt((max - min) + 1) + min;
    }

    private SpecialText dmgIcon = new SpecialText("");


    /**
     * Mortal-specific stuff for updates.  Checks death and runs onDeath() when appropriate
     */
    @Override
    public void backgroundUpdate(){
        if (checkDeath()){
            onDeath();
        }
        if (isRanged){
            rangedUpdate();
        }
    }

    public String getLayerName(){
        return layerName;
    }

    public int getHealth(){
        return health;
    }

    public void subtractHealth(int amountLost, String message){
        if (strClass.equals("Player") ){
            int damage = (amountLost - room.getPlayer().defense); // todo: make this specific to the actual player that this happens to
            if (damage < 1) {
                damage = 1;
            }
            health -= damage;
            room.getPlayer().showPain(message);
            if (health < 0){
                health = 0;
            }
            room.getPlayer().screenRedness = Math.max((int)((1 - ((float)health / maxHealth)) * 255),50);
            //System.out.println(String.format("Setting redness: %1$d (%2$f)", room.playo.screenRedness, 1 - ((float)health / maxHealth)));
        } else if ((dmgIcon.getStr() == "")){ //Prevents spam
            //System.out.println("My health is now " + getHealth());
            health -= amountLost;
            int percentHealth = (int)(((float)health / maxHealth) * 10);
            if (percentHealth > 0) {
                dmgIcon = new SpecialText(String.valueOf(percentHealth), new Color(255, 150, 90));
            } else {
                dmgIcon = new SpecialText("X", new Color(255, 150, 90));
            }
            setDispIcon(dmgIcon);
            Timer timing = new Timer();
            timing.schedule(new dmgTimer(), 250);
        }
    }

    /**
     * Sets display icon, factoring in whether or not it should display the damage percentage
     */
    protected void setDispIcon (String icon){ setDispIcon(new SpecialText(icon));}

    protected void setDispIcon(SpecialText icon){ setDispIcon(icon, 0, 0);}

    protected void setDispIcon(SpecialText icon, int x, int y){
        if (icon.getStr().length() != 1 || dmgIcon.getStr().equals("")) {
            orgo.editLayer(icon, layerName, y, x);
        } else {
            orgo.editLayer(dmgIcon, layerName, y, x);
        }
    }

    public void subtractHealth(int amountLost){
        subtractHealth(amountLost, "You died.");
    }

    public void setHealth(int newHealth){
        health = newHealth;
        if (maxHealth > newHealth) maxHealth = newHealth;
    }

    /**
     * Should not be used in most cases!
     */
    public void addHealth(int toAdd){
        health += toAdd;
        System.out.println("RAW ADDING HEALTH: " + toAdd);
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
        if (strClass.contains("Player") && !room.getPlayer().paused.get()){
            int toSet = (int)(((float)addHP / room.getPlayer().maxHealth) * 150);
            if (toSet > 100) toSet = 100;
            room.getPlayer().screenYellowness = toSet; // todo: make this look at *which* player gets health restored.
        }
    }

    public int getAttack(){
        return attack;
    }
    public void setAttack(int newAttack){
        attack = newAttack;
    }

    protected boolean isGoodGuy(){ return isGoodGuy; }
    void makeGoodGuy(){ isGoodGuy = true; }

    private boolean checkDeath() {
        if (getHealth() <= 0){
            room.removeMortal(this);
            updateTimerInstance.cancel();
            try {
                orgo.editLayer(" ", layerName, y, x);
            }catch (NullPointerException ignore){}
            orgo.removeLayer(layerName);
            return true;
        }
        return false;
    }

    /**
     * Override this method to put fun stuff (like drops) to happen once you die.  Timer cancelling and putting
     * a space where the Mortal used to be and removing it from room is already taken care of; don't worry be happy
     */
    public void onDeath(){
    }

    public void goTo(int newX, int newY) {
        orgo.editLayer(" ", layerName, y, x);
        x = newX;
        y = newY;
    }

    protected Mortal getClosestGoodGuy(){
        int closest = 50000000;
        Mortal closestM = null;
        for (Mortal m : room.enemies) {
            if (m.isGoodGuy() && distanceTo(m) < closest) {
                closest = distanceTo(m);
                closestM = m;
            }
        }
        return closestM;
    }

    protected void pathToPos(int followDist, int gotoX, int gotoY){
        pathToPos(followDist, gotoX, gotoY, layerName);
    }

    protected boolean atPathLoc = false;

    protected void refinedPathToPos(int followDist, int gotoX, int gotoY){
        if (Math.abs(x - gotoX) + Math.abs(y - gotoY) <= 1){
            //System.out.printf("Jumping to %1$d,%2$d (dif: %3$d,%4$d, curr: %5$d,%6$d)\n", gotoX, gotoY, x - gotoX, y - gotoY, x, y);
            atPathLoc = true;
            room.removeFromObjHitMesh(x,y);
            x = gotoX;
            y = gotoY;
            room.addToObjHitMesh(x,y);
        } else {
            pathToPos(followDist, gotoX, gotoY);
            atPathLoc = false;
        }
    }

    protected void rangedPathfinding(Mortal target, int attackRange, int followDist){
        int xdif = target.getX() - x;
        int ydif = target.getY() - y;
        if (xdif == 0 && ydif == 0){
            return;
        }
        if (Math.abs(ydif) > Math.abs(xdif)){
            if (ydif < 0){
                int dist = raycastDistance(target.getX(), target.getY(), attackRange, "down");
                //orgo.getLayer("TestingLayer").setPos(target.getY() + dist, target.getX());
                refinedPathToPos(followDist, target.getX(), target.getY() + dist);
            } else {
                int dist = raycastDistance(target.getX(), target.getY(), attackRange, "up");
                //orgo.getLayer("TestingLayer").setPos(target.getY() - dist, target.getX());
                refinedPathToPos(followDist, target.getX(), target.getY() - dist);
            }
        } else {
            if (xdif < 0){
                int dist = raycastDistance(target.getX(), target.getY(), attackRange, "right");
                //orgo.getLayer("TestingLayer").setPos(target.getY(), target.getX() + dist);
                refinedPathToPos(followDist, target.getX() + dist, target.getY());
            } else {
                int dist = raycastDistance(target.getX(), target.getY(), attackRange, "left");
                //orgo.getLayer("TestingLayer").setPos(target.getY(), target.getX() - dist);
                refinedPathToPos(followDist, target.getX() - dist, target.getY());
            }
        }
    }

    protected int raycastDistance(int startX, int startY, int maxDist, String direction){
        int total = 0;
        int currX = startX;
        int currY = startY;
        for (int ii = 0; ii < maxDist ; ii++){
            switch (direction){
                case "up":
                    if (!room.isPlaceSolid(currX, currY - 1)){
                        total++;
                        currY--;
                    } else {
                        return total;
                    }
                    break;
                case "down":
                    if (!room.isPlaceSolid(currX, currY + 1)){
                        total++;
                        currY++;
                    } else {
                        return total;
                    }
                    break;
                case "left":
                    if (!room.isPlaceSolid(currX - 1, currY)){
                        total++;
                        currX--;
                    } else {
                        return total;
                    }
                    break;
                case "right":
                    if (!room.isPlaceSolid(currX + 1, currY)){
                        total++;
                        currX++;
                    } else {
                        return total;
                    }
                    break;
            }
        }
        return total;
    }

    protected Spell rangedAttack;
    protected int rangedPreShotDelay;
    protected int rangedPostShotDelay;
    protected int attackingRange;
    protected int followingDist;
    protected boolean isRanged = false;

    protected int rangedCounter;

    protected void rangedInit(int preShotDelay, int postShotDelay, int attackRange, int followDist, Spell weapon){
        rangedAttack = weapon;
        rangedPreShotDelay = preShotDelay;
        rangedPostShotDelay = postShotDelay;
        attackingRange = attackRange;
        isRanged = true;
        rangedCounter = preShotDelay + postShotDelay;
        followingDist = followDist;
    }

    private int setOr = -1;
    protected void rangedUpdate(){
        //System.out.println("I'm alive");

        Mortal target = getClosestGoodGuy();
        //rangedPathfinding(target, attackingRange, followingDist);


        if (rangedCounter == rangedPostShotDelay + rangedPreShotDelay){ //Done shooting
            if (!atPathLoc) {
                //System.out.println("Pathing....");
                rangedPathfinding(target, attackingRange, followingDist);
            }
            else
                rangedCounter = 0;
        } else {
            //System.out.println(rangedCounter);
            if (rangedCounter == 0){
                acquireTarget(target);
            }
            if (rangedCounter == rangedPreShotDelay){
                int damage = rangedAttack.getDamage();
                int range = rangedAttack.getRange();
                SpecialText anim1 = rangedAttack.getAnim1();
                SpecialText anim2 = rangedAttack.getAnim2();
                boolean alting = rangedAttack.getAlting();
                if (setOr == -1)
                    acquireTarget(target);
                if (setOr != -1) {
                    System.out.printf("[TestRanged] Orientation: %d\n", setOr);
                    Spell toFire = new Spell(orgo, room, x, y, setOr, damage, range, anim1, anim2, alting);
                    toFire.setHostility(true);
                    room.addObject(toFire);
                    setOr = -1;
                }
                atPathLoc = false;
            }
            if (rangedCounter < rangedPostShotDelay + rangedPreShotDelay)
                rangedCounter++;
        }
    }

    private void acquireTarget (Mortal target){
        if (target.getX() == x && target.getY() < y) { setOr = 0; }
        if (target.getX() == x && target.getY() > y) { setOr = 1; }
        if (target.getX() < x && target.getY() == y) { setOr = 2; }
        if (target.getX() > x && target.getY() == y) { setOr = 3; }
    }

    private class dmgTimer extends TimerTask {
        @Override
        public void run(){
            dmgIcon = new SpecialText("");
        }
    }
}
