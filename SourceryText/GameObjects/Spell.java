package SourceryText.GameObjects;

import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;
import sun.text.normalizer.UCharacterProperty;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.toRadians;

/**
 * Create a new spell.  Attributes such as its character representation, damage, and range can be customized.
 * Created by riley on 13-Jun-2016.
 */
public class Spell extends GameObject {
    private Random rand = new Random();
    private String name = "Generic";
    private int range = -1;
    private SpecialText char1 = new SpecialText("X");
    private SpecialText char2 = new SpecialText("x");
    private boolean onChar1 = true;
    private int splashRadius = 0;

    private int damage = 10;
    private String killMessage = "You were electrocuted by a rouge spell";

    private String layerName;
    protected int orientation = 0;
    private boolean dispAlting = false;
    private boolean enemySeeking = false;

    private boolean isHostile = false;
    private String type = ""; // like 'fire' or 'arcane'

    private Item baseItem = null;

    //Convenience variables
    private final int UP = 0;
    private final int LEFT = 1;
    private final int DOWN = 2;
    private final int RIGHT = 3;

    /**
     * You can name and customize different spells.  Some presets are:
     * Name    damage   range   char1   char2
     * "Book",    1        3       b       B
     * "Spark",   5        4       X       x
     * "Flame"    9        5       M       m
     * "Wanderer" 9        9*      W       w
     *
     * @param theRoom the Room
     * @param setX    X starting point
     * @param setY    Y starting point
     * @param setOr   integer orientation.  0: up  1:left  2:down  3:right
     */
    public Spell(Room theRoom, int setX, int setY, int setOr, int setDmg, int setRng, SpecialText set1, SpecialText set2, boolean alting, boolean tracking, String spellName) {
        this(theRoom, setX, setY, setOr, setDmg, setRng, set1, set2, alting, spellName);
        enemySeeking = tracking;
    }

    public Spell(Room theRoom, int setX, int setY, int setOr, int setDmg, int setRng, SpecialText set1, SpecialText set2, boolean alting, String type) {
        strClass = "Spell";
        room = theRoom;
        org = room.org;

        x = setX;
        y = setY;

        layerName = room.makeUniqueLayerName("Spell");
        Layer effect = new Layer(new String[1][1], layerName);
        effect.setStr(0, 0, " ");
        effect.setPos(x, y);
        org.addLayer(effect);

        orientation = setOr;
        name = type;

        define(setDmg, setRng, set1, set2);
        dispAlting = alting;

        if (org.getDebug())
            System.out.println(name + " spell cast!");

        setupTimer(30);
    }

    public Spell(Room theRoom, int setX, int setY, int setOr, int setDmg, Item setBase){
        strClass = "Spell";
        room = theRoom;
        org = room.org;

        x = setX;
        y = setY;

        layerName = room.makeUniqueLayerName("Spell");
        Layer effect = new Layer(new String[1][1], layerName);
        effect.setStr(0, 0, " ");
        effect.setPos(x, y);
        org.addLayer(effect);

        orientation = setOr;

        baseItem = setBase;

        name = baseItem.getName();
        define(setDmg, baseItem.range, baseItem.animation1, baseItem.animation2);
        dispAlting = baseItem.getAlting();
        enemySeeking = baseItem.getPathfinding();

        type = baseItem.getDescMode();

        if (org.getDebug())
            System.out.println(name + " spell cast!");

        setupTimer(30);
    }

    public String getType(){
        return type;
    }

    public int getSpellType(){
        switch (type){
            case "healing": return 0;
            case "arcane": return 1;
            case "fire": return 2;
            case "ice": return 3;
            case "dark": return 4;
            default: return Integer.MAX_VALUE;
        }
    }

    public void setHostility(boolean set) {
        isHostile = set;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public SpecialText getAnim1() {
        return char1;
    }

    public SpecialText getAnim2() {
        return char2;
    }

    public boolean getAlting() {
        return dispAlting;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void define(int damage, int range, SpecialText charOne, SpecialText charTwo) {
        setKillMessage("You died!");
        setDamage(damage);
        setRange(range);
        setChar1(charOne);
        setChar2(charTwo);
    }

    public void setChar1(SpecialText newChar1) {
        char1 = newChar1;
    }

    public void setChar2(SpecialText newChar2) {
        char2 = newChar2;
    }

    public void setDamage(int newDamage) {
        damage = newDamage;
    }

    public void setRange(int newRange) {
        range = newRange;
    }

    public void setKillMessage(String newKillMessage) {
        killMessage = newKillMessage;
    }

    public void setSplashRadius(int splashRadius){
        this.splashRadius = splashRadius;
    }

    @Override
    public void update() {
        overridableUpdate();
        move();
        takeCareOfHittingStuff();
    }

    protected void move(){
        if (enemySeeking) {
            Mortal target = getClosestBadGuy(range);
            if (target != null) {
                int oldX = x;
                int oldY = y;
                pathToPos(range, target.getX(), target.getY(), layerName, false);
                if (y < oldY) orientation = UP;
                if (y > oldY) orientation = DOWN;
                if (x < oldX) orientation = LEFT;
                if (x > oldX) orientation = RIGHT;
            } else {
                doMovement();
            }
        } else {
            doMovement();
        }

        if (range > 0) {
            range--;
        }
    }

    protected void takeCareOfHittingStuff(){
        boolean hitSomeOne = room.hurtSomethingAt(x, y, damage, killMessage, !isHostile, name);
        if (enemySeeking) {
            switch (orientation){
                case UP:
                    hitSomeOne |= room.hurtSomethingAt(x, y - 1, damage, killMessage, !isHostile, name);
                    break;
                case DOWN:
                    hitSomeOne |= room.hurtSomethingAt(x, y + 1, damage, killMessage, !isHostile, name);
                    break;
                case LEFT:
                    hitSomeOne |= room.hurtSomethingAt(x - 1, y, damage, killMessage, !isHostile, name);
                    break;
                case RIGHT:
                    hitSomeOne |= room.hurtSomethingAt(x + 1, y, damage, killMessage, !isHostile, name);
                    break;
            }
        }

        if (dispAlting) {
            if (onChar1) { //A really clever way to alternate between two characters ('^' means XOR)
                org.editLayer(char1, layerName, 0, 0);
                onChar1 = false;
            } else {
                org.editLayer(char2, layerName, 0, 0);
                onChar1 = true;
            }
        } else {
            if (orientation%2 == 0) { //Orientation-sensitive display
                org.editLayer(char1, layerName, 0, 0);
            } else {
                org.editLayer(char2, layerName, 0, 0);
            }
        }

        if (room.isPlaceSolid(x, y)) {
            onHitNonMortal();
        }
        if (hitSomeOne) {
            onHitMortal();
        }
        if (range == 0) {
            onRangeExhausted();
        }

        room.onSpellAt(x, y, type);

        Layer iconLayer = org.getLayer(layerName);
        if (iconLayer != null) iconLayer.setPos(x, y);
    }

    protected void overridableUpdate() {}

    protected void onHitMortal(){
        splashDamage();
        room.removeObject(this);
    }
    protected void onHitNonMortal(){
        splashDamage();
        room.removeObject(this);
    }
    protected void onRangeExhausted(){
        splashDamage();
        room.removeObject(this);
    }

    protected void doMovement() {
        switch (orientation) {
            case UP:
                y -= 1;
                break;
            case DOWN:
                y += 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
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

    private void splashDamage() {
        for (int xi = -splashRadius; xi <= splashRadius; xi++) {
            for (int yi = -splashRadius; yi <= splashRadius; yi++) {
                float xDamageMult = abs(abs(xi) - splashRadius) / (float) splashRadius; // 0 to 1, peaking when xi=0 (center)
                float yDamageMult = abs(abs(yi) - splashRadius) / (float) splashRadius;
                int totalDamage = (int) (damage * .5 * (xDamageMult + yDamageMult));
                if (xi==0 && yi==0) totalDamage = 0; // We already hit the thing in the middle, earlier in the code.
                room.hurtSomethingAt(xi + x, yi + y, totalDamage, "Hit by the splash damage of a\n spell! Better be more careful.", name);
                //System.out.println(x + xi + " " + (yi + y) + " given " + totalDamage + " damage");
            }
            //System.out.println();
        }
    }

    @Override
    public void selfCleanup(){
        org.editLayer(" ", layerName, 0, 0);
        org.removeLayer(layerName);
    }

    public void setAlting(boolean setTo) {
        dispAlting = setTo;
    }

    private int r(int max) {
        return r(max, 0);
    }

    private int r(int max, int min) {
        return rand.nextInt((max - min) + 1) + min;
    }

    public void setType(String type) {
        this.type = type;
    }
}
