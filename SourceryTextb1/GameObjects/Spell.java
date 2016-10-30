package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.util.Random;

/**
 * Create a new spell.  Attributes such as its character representation, damage, and range can be customized.
 * Created by riley on 13-Jun-2016.
 *
 * TODO : passing a layer name in the constructors is useless!  We should either not pretend it does anything or remove the misleading feature
 */
public class Spell extends GameObject{
    private Random rand = new Random();
    private String name = "Generic";
    private int range = -1;
    private SpecialText char1 = new SpecialText("X");
    private SpecialText char2 = new SpecialText("x");
    private int damage = 10;
    private String killMessage = "You were electrocuted by a rouge spell";

    private String layerName;
    private int orientation = 0;
    private boolean dispAlting = false;
    private boolean enemySeeking = false;

    private boolean isHostile = false;

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
    public Spell (ImageOrg org, Room theRoom, int setX, int setY, int setOr, int setDmg, int setRng, SpecialText set1, SpecialText set2, boolean alting){
        this(org, theRoom, "Spellz", setX, setY, setOr, setDmg, setRng, set1, set2, alting);
    }
    public Spell (ImageOrg org, Room theRoom, Layer place, int setX, int setY, int setOr, int setDmg, int setRng, SpecialText set1, SpecialText set2, boolean alting){
        this(org, theRoom, place.getName(), setX, setY, setOr, setDmg, setRng, set1, set2, alting);
    }
    public Spell (ImageOrg org, Room theRoom, Layer place, int setX, int setY, int setOr, int setDmg, int setRng, SpecialText set1, SpecialText set2, boolean alting, boolean tracking){
        this(org, theRoom, place.getName(), setX, setY, setOr, setDmg, setRng, set1, set2, alting);
        enemySeeking = tracking;
    }
    public Spell (ImageOrg org, Room theRoom, String setLayerName, int setX, int setY, int setOr, int setDmg, int setRng, SpecialText set1, SpecialText set2, boolean alting){
        strClass = "Spell";
        orgo = org;
        room = theRoom;

        x = setX;
        y = setY;

        layerName = room.makeUniqueLayerName("Spell");
        Layer effect = new Layer(new String[1][1], layerName);
        effect.setStr(0,0, " ");
        effect.setPos(y, x);
        orgo.addLayer(effect);

        orientation = setOr;

        define(setDmg, setRng, set1, set2);
        dispAlting = alting;

        if (orgo.getDebug())
            System.out.println(name + " spell cast!");

        setupTimer(30);
    }

    public void setHostility(boolean set){ isHostile = set;}

    public int getDamage(){ return damage; }

    public int getRange(){ return range; }

    public SpecialText getAnim1(){ return char1; }

    public SpecialText getAnim2(){ return char2; }

    public boolean getAlting(){ return dispAlting; }

    public void setName(String newName){
        name = newName;
        /*
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
        */
    }

    public void define(int damage, int range, SpecialText charOne, SpecialText charTwo){
        setKillMessage("You died!");
        setDamage(damage);
        setRange(range);
        setChar1(charOne);
        setChar2(charTwo);
    }

    public void setChar1(SpecialText newChar1){
        char1 = newChar1;
    }
    public void setChar2(SpecialText newChar2){
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
    public void update() {
        //orgo.editLayer(" ", layerName, 0, 0);

        if (orientation >= 0 && orientation <= 3) {
            //System.out.println("A spell is traveling normally... (" + x + "," + y + ")");
        }

        boolean hitSomeOne = false;

        if (enemySeeking) {
            Mortal target = getClosestBadGuy(range);
            if (target != null) {
                pathToPos(range, target.getX(), target.getY(), layerName, false);
                if (Math.abs(target.getX() - x) + Math.abs(target.getY() - y) <= 1){
                    target.subtractHealth(damage, killMessage);
                    hitSomeOne = true;
                }
            } else {
                doMovement();
            }
        } else {
            doMovement();
        }

        if (dispAlting) {
            if (x % 2 == 0 ^ y % 2 == 0) { //A really clever way to alternate between two characters ('^' means XOR)
                orgo.editLayer(char1, layerName, 0, 0);
            } else {
                orgo.editLayer(char2, layerName, 0, 0);
            }
        } else {
            if (orientation <= 1) { //Orientation-sensitive display
                orgo.editLayer(char1, layerName, 0, 0);
            } else {
                orgo.editLayer(char2, layerName, 0, 0);
            }
        }

        orgo.getLayer(layerName).setPos(y,x);

        if (!hitSomeOne)
            hitSomeOne = room.hurtSomethingAt(x, y, damage, killMessage, !isHostile);
        if (room.isPlaceSolid(x,y) || hitSomeOne || range == 0){
            orgo.editLayer(" ", layerName, 0, 0);
            orgo.removeLayer(layerName);
            room.removeObject(this);
        }
        if (range > 0){
            range --;
        }
    }

    protected void doMovement(){
        switch (orientation) {
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
                x += r(1) * 2 - 1; // does not stay still; must move L or R
                y += r(1) * 2 - 1;
                break;
            default:
                break;
        }
    }

    public void setAlting(boolean setTo) {
        dispAlting = setTo;
    }

    int r(int max) {
        return r(max, 0);
    }
    int r(int max, int min) {
        return rand.nextInt((max - min) + 1) + min;
    }
}
