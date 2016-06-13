package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

/**
 * Create a new spell.  Attributes such as its character representation, damage, and range can be customized.
 * Created by riley on 13-Jun-2016.
 */
public class Spell extends GameObject{

    private String name = "Generic";
    private int range = -1;
    private String char1 = "X";
    private String char2 = "x";
    private int damage = 10;

    private String layerName;
    private int orientation = 0;

    /**
     * You can name and customize different spells.  Some presets are:
     * Name    damage   range   char1   char2
     * "Book",    1        3       b       B
     * "Spark",   5        4       X       x
     * "Flame"    9        5       M       m
     * @param org the ImageOrganizer
     * @param theRoom the Room
     * @param place a Layer for this piece of magic to reside in
     * @param setX X starting point
     * @param setY Y starting point
     * @param setOr integer orientation.  0: up  1:down  2:left  3:right
     * @param name a String name for the
     */
    public Spell (ImageOrg org, Room theRoom, Layer place, int setX, int setY, int setOr, String name){
        strClass = "Spell";
        orgo = org;
        room = theRoom;
        layerName = place.getName();

        x = setX;
        y = setY;

        orientation = setOr;
        setName(name);
        System.out.println("You cast a " + name + " Spell.");
    }

    public void setName(String newName){
        name = newName;
        switch (name){
            case "Book":
                setDamage(1);
                setRange(3);
                setChar1("B");
                setChar2("b");
                break;
            case "Spark":
                setDamage(5);
                setRange(5);
                setChar1("X");
                setChar2("+");
                break;
            case "Flame":
                setDamage(9);
                setRange(6);
                setChar1("M");
                setChar2("m");
                break;
            case "None":
                setDamage(0);
                setRange(0);
                setChar1(" ");
                setChar2(" ");
                break;
            default:
                System.out.println("No default set for spells of name " + name);
        }
    }
    public void setChar1(String newChar1){
        char1 = newChar1;
    }
    public void setChar2(String newChar2){
        char2 = newChar2;
    }
    public void setDamage(int newDamage){
        damage = newDamage;
    }
    public void setRange(int newRange){
        range = newRange;
    }

    public void update(){
        orgo.editLayer(" ", layerName, y, x);

        switch(orientation){
            case 0:
                y -= 1;
                break;
            case 1:
                y += 1;
                break;
            case 2:
                x -= 1;
                break;
            case 3:
                x += 1;
                break;
            default:
                break;
        }

        if (x%2 == 0 ^ y%2 == 0) { //A really clever way to alternate between two characters ('^' means XOR)
            orgo.editLayer(char1, layerName, y, x);
        } else {
            orgo.editLayer(char2, layerName, y, x);
        }

        boolean hitSomeOne = room.hurtSomethingAt(x, y, damage);
        if (room.isPlaceSolid(x,y) || hitSomeOne || range == 0){
            orgo.editLayer(" ", layerName, y, x);
            room.removeObject(this);
        }
        if (range > 0){
            range --;
        }
    }
}
