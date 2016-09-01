package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

import static SourceryTextb1.GameObjects.Spike.r;

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
    private String killMessage = "You were electrocuted by a rouge spell";

    private String layerName;
    private int orientation = 0;
    private boolean dispAlting = false;

    /**
     * You can name and customize different spells.  Some presets are:
     * Name    damage   range   char1   char2
     * "Book",    1        3       b       B
     * "Spark",   5        4       X       x
     * "Flame"    9        5       M       m
     * "Wanderer" 9        9*      W       w
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

        if (orgo.getDebug())
            System.out.println(name + " spell cast!");

        setupTimer(30);
    }
    public Spell (ImageOrg org, Room theRoom, int setX, int setY, int setOr, int setDmg, int setRng, String set1, String set2, boolean alting){
        this(org, theRoom, "Spellz", setX, setY, setOr, setDmg, setRng, set1, set2, alting);
    }
    public Spell (ImageOrg org, Room theRoom, Layer place, int setX, int setY, int setOr, int setDmg, int setRng, String set1, String set2, boolean alting){
        this(org, theRoom, place.getName(), setX, setY, setOr, setDmg, setRng, set1, set2, alting);
    }
    public Spell (ImageOrg org, Room theRoom, String setLayerName, int setX, int setY, int setOr, int setDmg, int setRng, String set1, String set2, boolean alting){
        strClass = "Spell";
        orgo = org;
        room = theRoom;
        layerName = setLayerName;

        x = setX;
        y = setY;

        orientation = setOr;

        define(setDmg, setRng, set1, set2);
        dispAlting = alting;

        if (orgo.getDebug())
            System.out.println(name + " spell cast!");

        setupTimer(30);
    }

    public void setName(String newName){
        name = newName;
        switch (name){
            case "Book":
                setKillMessage("You managed to get murdered by an old\n book.  Not a common fate, and \n I must say I am impressed.");
                setDamage(1);
                setRange(3);
                setChar1("B");
                setChar2("b");
                break;
            case "Spark":
                setKillMessage("You were electrocuted by a spark spell. \n Honestly, try harder next time.");
                setDamage(3);
                setRange(6);
                setChar1("X");
                setChar2("+");
                break;
            case "Flame":
                setKillMessage("You were burnt to a crisp with a Flame spell. \n At least it is a more noble death \n than dying by a Spark, like some.");
                setDamage(9);
                setRange(6);
                setChar1("M");
                setChar2("m");
                break;
            case "Wanderer":
                setKillMessage("");
                setDamage(9);
                setRange(9);
                setChar1("W");
                setChar2("w");
                orientation = -1;
                break;
            case "None":
                setKillMessage("");
                setDamage(0);
                setRange(0);
                setChar1(" ");
                setChar2(" ");
                break;
            default:
                System.out.println("No default set for spells of name " + name);
        }
    }

    public void define(int damage, int range, String charOne, String charTwo){
        setKillMessage("You died!");
        setDamage(damage);
        setRange(range);
        setChar1(charOne);
        setChar2(charTwo);
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
    public void setKillMessage(String newKillMessage){
        killMessage = newKillMessage;
    }

    @Override
    public void update(){
        orgo.editLayer(" ", layerName, y, x);

        if (orientation >= 0 && orientation <= 3){
            //System.out.println("A spell is traveling normally... (" + x + "," + y + ")");
        }

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
            case -1: // Random dir
                x += r(1)*2-1; // does not stay still; must move L or R
                y += r(1)*2-1;
                break;
            default:
                break;
        }

        if (dispAlting) {
            if (x % 2 == 0 ^ y % 2 == 0) { //A really clever way to alternate between two characters ('^' means XOR)
                orgo.editLayer(char1, layerName, y, x);
            } else {
                orgo.editLayer(char2, layerName, y, x);
            }
        } else {
            if (orientation <= 1) { //Orientation-sensitive display
                orgo.editLayer(char1, layerName, y, x);
            } else {
                orgo.editLayer(char2, layerName, y, x);
            }
        }

        boolean hitSomeOne = room.hurtSomethingAt(x, y, damage, killMessage);
        if (room.isPlaceSolid(x,y) || hitSomeOne || range == 0){
            orgo.editLayer(" ", layerName, y, x);
            room.removeObject(this);
        }
        if (range > 0){
            range --;
        }
    }

    public void setAlting(boolean setTo) {
        dispAlting = setTo;
    }
}
