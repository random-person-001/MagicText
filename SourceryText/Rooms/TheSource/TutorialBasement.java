/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryText.Rooms.TheSource;

import SourceryText.Art;
import SourceryText.GameObjects.*;
import SourceryText.GameObjects.TheSource.PotOfPetunias;
import SourceryText.GameObjects.TheSource.Spider;
import SourceryText.Layer;
import SourceryText.GameObjects.MagicHole;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * A place to begin a the game, guiding you through the basics of the game and starting you off.
 *
 * @author jared
 *         <p>
 *         This is the starting point of gameplay.
 *         <p>
 *         What Generally Happens Here:
 *         > You familiarize yourself with your character and learn how to navigate a text-based environment
 *         > You learn the basics of the game, like using the menu.
 *         > An innocent pot of petunias is murdered by you, only because the developers felt like playing a cruel god
 *         > Some starter spells are aquired
 */


public class TutorialBasement extends Room {

    public TutorialBasement(Player player) {
        super(player);
        strRoomName = "TutorialBasement";
    }

    @Override
    protected String playerLoop(Player play) {
        int count = 0;
        boolean foundSpell1 = false;
        boolean foundSpell2 = false;
        boolean inMaze = false;
        boolean leavingEarly = false;
        boolean holdDownReminder = false;

        if (play.getX() == 0 && play.getY() == 0) {
            play.goTo(20, 29);
        }

        while (exitCode.equals("")) {
            try {
                Thread.sleep(20);

                if (count == 0) {
                    queueMessage(new FlavorText("You've woken up in a basement somewhere.\nWoah, there's now lots of text everywhere!", "", true), play);
                    queueMessage(new FlavorText("You should explore the basement!\nUse the arrow keys to navigate the place.", ""), play);
                    queueMessage(new FlavorText("Want some pie??", true, 0), play);
                    count++;
                }
                if (count == 1 && play.getX() == 5 && play.getY() == 23) {
                    queueMessage(new FlavorText("Ahead of you is a pot of petunias. \nPretty, isn't it?\nHowever, it's in the way...", ""), play);
                    queueMessage(new FlavorText("Luckily, you were a student at\n The Magic Academy, so you must know\n plenty of spells to help you, right?", ""), play);
                    queueMessage(new FlavorText("WRONG. You dropped out of magic school.\nYou know absolutely nothing. Nothing.\nYou have no memory of how to cast any spell", ""), play);
                    queueMessage(new FlavorText("Most spells ARE written down, so if you\n stumble upon some spare magic literature,\n you can just follow the instructions.", ""), play);
                    queueMessage(new FlavorText("Unfortunately, your coat pockets\n are empty. Or in this case, robe pockets.\n", ""), play);
                    count++;
                }
                if (!holdDownReminder && play.getX() > 14 && play.getX() < 20 && play.getY() == 22) {
                    queueMessage(new FlavorText("Hold SPACE while moving to sprint.\n However you aren't that very athletic,\n so you get tired very quickly", ""), play);
                    holdDownReminder = true;
                }
                if (play.getX() == 87 && play.getY() == 35) {
                    foundSpell1 = true;
                }
                if (play.getX() == 80 && play.getY() == 29) {
                    foundSpell2 = true;
                }
                if (play.getX() == 67 && play.getY() == 32) {
                    if (!inMaze) {
                        queueMessage(new FlavorText("Wow, there's a lot of junk here!\nDoes the owner of this basement\n realize there are other rooms...", ""), play);
                        queueMessage(new FlavorText("...in this basement that he can also\n put stuff in?\nSeriously, you woke up in an empty room.", ""), play);
                    }
                    inMaze = true;
                    //System.out.println("IN THE MAZE....");
                }
                if (count == 2 && play.getX() > 14 && play.getX() < 20 && play.getY() == 22 && (foundSpell1 || foundSpell2)) {
                    queueMessage(new FlavorText("Now that you are armed with\n some magic scrolls, you can\n defeat the pot of petunias!", ""), play);
                    queueMessage(new FlavorText("Push 'W' to open the menu.\nPush 'A' to confirm an option that\n the cursor is selecting", ""), play);
                    queueMessage(new FlavorText("Go to 'Spells' and Push either 'S' or 'D'\n to bind a spell to to keys 'S' and 'D'", ""), play);
                    count++;
                }
                if (inMaze && (!foundSpell1 ^ !foundSpell2) && !leavingEarly && play.getX() == 66 && play.getY() == 32) {
                    queueMessage(new FlavorText("There may be other spells hidden in the maze.\n You may want to head back.", ""), play);
                    leavingEarly = true;
                }
                if (inMaze && !(foundSpell1 || foundSpell2) && !leavingEarly && play.getX() == 66 && play.getY() == 32) {
                    queueMessage(new FlavorText("There are some spells hidden in the maze.\n You may want to head back.", ""), play);
                    leavingEarly = true;
                }
                if (count == 3 && play.getX() == 5 && play.getY() == 22) {
                    queueMessage(new FlavorText("Casting spells is simple:\nPush the 'S' and 'D' key to cast the\n spell bound to its respective key", ""), play);
                    count++;
                }
                if (count == 4 && play.getX() == 5 && play.getY() == 14) {
                    queueMessage(new FlavorText("You've managed to defeat\n The Pot of Petunias!\nCongratulations!", ""), play);
                    queueMessage(new FlavorText("As you may have noticed, your meter\n at the top-right had depleted a bit.\nThat is your mana bar", ""), play);
                    queueMessage(new FlavorText("Casting spells and sprinting\n costs mana. Fortunately, mana will\n regenerate shortly after.", "", false), play);
                    queueMessage(new FlavorText("Note: The less mana you spend,\n the less you have to wait before\n your mana refills.", "", false), play);
                    count++;
                }
                if (count == 5 && ((play.getX() == 5 && play.getY() == 6))) {
                    queueMessage(new FlavorText("It seems there is a rather large\n spider in the next room.", "", false), play);
                    queueMessage(new FlavorText("Luckily, it doesn't know any magic,\n is very slow moving,\n and it doesn't have that much health", "", false), play);
                    queueMessage(new FlavorText("Pushing the 'A' key locks your aim,\n allowing you to comfortably strafe\n while casting spells", "", false), play);
                    queueMessage(new FlavorText("Use this technique to effortlessly\n dispatch the spider.", "", false), play);
                    count++;
                }
                if (count == 6 && (play.getX() >= 84 && play.getY() == 16 && play.getX() <= 91)) {
                    count++;
                }
                if (count == 7 && play.getY() >= 17) {
                    queueMessage(new FlavorText("You have probably stumbled upon\n some weapons. You should go to\n the 'Equipment' menu.", "", false), play);
                    queueMessage(new FlavorText("Use the 'A' key to equip a weapon.", "", false), play);
                    count++;
                }
                if (play.getX() > 132) {
                    setNewRoom("SourcePit", play, 10, 109);
                }
                /*
                if (play.getX() == 20 && play.getY() == 32) {
                    play.isGhost = true;
                    play.ludicrousSpeed = true;
                    play.restoreHealth(400, 294);
                    Item fireSpell = new Item("Fireball", "Fire Spell;\nUse your imagination.", "FrBll", "spell", true);
                    fireSpell.dmgSpellDefine(1, 100, 0, "fire", new SpecialText("6", new Color(255, 200, 0)), new SpecialText("9", new Color(255, 150, 0)));
                    play.addItem(fireSpell);
                    setNewRoom("switch to zone 2", play, 0, 0);
                }
                */

            } catch (InterruptedException ignored) {
            }
        }
        return exitCode;
    }

    @Override
    public void uniqueResponse(int qID, Player respondTo) {
        if (qID == 0) {
            Item pie = new Item("Pie", "A delicious pie for\n expressing a need to eat\n pie.", "item");
            pie.healItemDefine(10, 10);
            respondTo.addItem(pie);
        }
    }

    /**
     * Everything that self-updates and can be paused (and acts nonexistent during paused) should go here.
     */
    @Override
    public void addItems() {

        Item dartSpell = new Item("Astral Dart", "Arcane Spell;\nFires a small bolt of\n pure stardust.", "AstDt", "spell", false);
        dartSpell.dmgSpellDefine(2, 9, 2, 0.01f, "arcane", new SpecialText("|", new Color(162, 137, 225)), new SpecialText("-", new Color(162, 137, 225)));
        DroppedItem gSpark = new DroppedItem(this, "You found a spell: Astral Dart!", dartSpell, 87, 35);
        super.addObject(gSpark);

        Item fireSpell = new Item("Fireball", "Fire Spell;\nUse your imagination.", "FrBll", "spell", true);
        fireSpell.dmgSpellDefine(4, 7, 5, 0.05f, "fire", new SpecialText("6", new Color(255, 200, 0)), new SpecialText("9", new Color(255, 150, 0)));
        DroppedItem gFire = new DroppedItem(this, "You found a spell: Fireball!", fireSpell, 80, 29);
        super.addObject(gFire);

        Item healSpell = new Item("Simple Heal", "Simple healing spell.", "sHeal", "spell");
        healSpell.altSpellDefine(12, "healing");
        healSpell.setHeal(8);
        healSpell.setUsageBurnout(0.3f);
        DroppedItem gHeal = new DroppedItem(this, "You found a spell: Heal!", healSpell, 65, 9);
        super.addObject(gHeal);

        Item fireGlove = new Item("Pyro Glove", "A glove that's on fire!\n\nPyromancers are quite the\n adventurous people, and so" +
                "\n these gloves became very\n commonplace", "equipment");
        fireGlove.setEquipvals(0, 0, 0, 0, 2, 0, 0, "weapon");
        DroppedItem gGlove = new DroppedItem(this, "You found a weapon: Pyro Glove!", fireGlove, 85, 15);
        super.addObject(gGlove);

        Item brokenStaff = new Item("Broken Staff", "A staff crafted by a\n dirt-poor student of\n The Magic Academy.\n\nMade of spare wood\n" +
                " and frayed ropes, it's\n no surprise that it\n already snapped in two", "equipment");
        brokenStaff.setEquipvals(0, 0, 1, 0, 0, 0, 0, "weapon");
        DroppedItem gStaff = new DroppedItem(this, "You found a weapon: Broken Staff!", brokenStaff, 90, 15);
        super.addObject(gStaff);

        //Item magicTater = new Item ("Magic Potato","How lucky! This eccentric\n potato can permanently\n increase either your\n Max HP or Max Mana.\n\nNOTE: it's permanent.", playo, "item");
        //DroppedItem gTater =  new DroppedItem(this, org, "You found a magic potato!", magicTater, "drops6", 20, 31);
        //super.addObject(gTater);

        PotOfPetunias flowers = new PotOfPetunias(org, this, 5, 19);
        addMortal(flowers);

        Spider spoidur = new Spider(this, 39, 7);
        //Spider spoidur = new Spider(this, 15, 29);
        addMortal(spoidur);

        super.addObject(new MagicSmoke(this, spoidur, 7, new int[]{MagicSmoke.MANAREGEN, MagicSmoke.FIRETYPE, MagicSmoke.HEALTYPE}));

        MagicHole whiteHole = new MagicHole(this, 30, 30, true, new String[]{"Player", "Spell"}, null, null, Color.WHITE, -1, 0);
        super.addObject(whiteHole);

        super.addObject(new MagicHole(this, 0, 0, whiteHole, new String[]{"Player", "Spell"}, null, null, null, -1, 0));

        /*
        Item potion = new Item("SC&OPotatoChip", "This variety of magic\n potato chip is flavored\n with sour cream and onions\n\nIt makes your breath so bad\n it hurts nearby enemies.", "item");
        potion.setSpellType("potion");
        potion.setDuration(20 * 1000);
        DroppedItem dPotion = new DroppedItem(this, "You found a potato chip!", potion, 5, 7);
        super.addObject(dPotion);
        */

        /*
        PathingObj obj = new PathingObj(this, 15, 29);
        addMortal(obj);
        */

        /*
        //Testing locked doors
        LockedDoor door = new LockedDoor("testKey", 30, 30, 22, this, org);
        addObject(door);

        Item testKey = new Item ("testKey","This key opens a door\n\nAin't that neat!", playo, "item");
        DroppedItem gKey = new DroppedItem(this, org, "You found a key!", testKey, 10, 29);
        addObject(gKey);
        */

        /*
        FallingBeehive wheeeeeee = new FallingBeehive(this, 25, 25);
        addObject(wheeeeeee);
        */

        //Enable this to play around with water

        Art art = new Art();
        WaterPool testingPool = new WaterPool(this, new Layer(Art.strToArray(art.tutWaterPool), "Water!", true, false), 3, 31);
        addObject(testingPool);

    }

    @Override
    public void startup() {

        FlavorText playerStart = new FlavorText("You start here!", "");
        plantText(playerStart, 20, 29);

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.tutForest);
        Layer lay1 = new Layer(base, "Test", 0, 0, true, false, false);
        org.addLayer(lay1);

        initHitMeshes(lay1);
        String[] solids = {"|", "-", "0", "/", ",", "#", "%", "$", "'"};
        addToBaseHitMesh(base, solids);

        addItems();

        //org.roomBackground = new Color(15, 60, 15);

        genericRoomInitialize();
    }
}
