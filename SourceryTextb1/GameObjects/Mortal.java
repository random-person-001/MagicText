package SourceryTextb1.GameObjects;

/**
 * Base object (to extend) for Enemies
 * Created by riley on 12-Jun-2016.
 */
public class Mortal extends GameObject {
    String layerName;
    private int health = 10;
    private int attack = 0;
    private String deathMessage = "Unknown";

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

    boolean checkDeath() {
        if (getHealth() <= 0){
            room.playo.addItem(super.strClass);
            room.removeMortal(this);
            try {
                orgo.editLayer(" ", layerName, y, x);
                orgo.removeLayer(layerName);
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
}
