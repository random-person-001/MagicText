package SourceryTextb1.GameObjects;

/**
 * Base object (to extend) for Enemies
 * Created by riley on 12-Jun-2016.
 */
public class Enemy extends GameObject {
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
}
