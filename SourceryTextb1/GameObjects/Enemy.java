package SourceryTextb1.GameObjects;

/**
 * Base object (to extend) for Enemies
 * Created by riley on 12-Jun-2016.
 */
public class Enemy extends GameObject {
    String layerName;
    private int health = 10;
    private int attack = 0;

    public int getHealth(){
        return health;
    }
    public void subtractHealth(int amountLost){
        health -= amountLost;
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
            room.playo.inventory.put(super.strClass, 1);
            room.removeEnemy(this);
            try {
                orgo.editLayer(" ", layerName, y, x);
                orgo.removeLayer(layerName);
            }catch (NullPointerException ignore){}
            return true;
        }
        return false;
    }
}
