/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.GameObjects.*;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.art;

/**
 * A place to begin a tutorial, guiding you through stuff.
 * @author 119184
 *
 *
 * So Far:
 *  > You have been introduced to the backstory
 *  > That's it
 *
 * What Generally Happens Here:
 *
 *      > The player familiarizes with the player-controlled character and learns how to navigate a text-based environment
 *      (Hence the pile of junk)
 *      >
 */


public class TutorialBasement extends Room {

    private ImageOrg org;
    private int maxH;
    private int maxW;

    private String loop(){
        String exitCode = "";
        int count = 0;
        boolean foundSpell1 = false;
        boolean foundSpell2 = false;
        boolean inMaze = false;
        boolean leavingEarly = false;

        boolean holdDownReminder = false;

        while (exitCode == ""){
            try {
                Thread.sleep(20);
                //System.out.println("I'm not dead yet! " + ii);
                updateObjs(20);
                if (count == 0){
                    compactTextBox(org, "You've woken up in a basement somewhere.\nWoah, there's now lots of text everywhere!", "", true);
                    compactTextBox(org, "You should explore the basement!\nUse the arrow keys to navigate the place.", "", false);
                    count++;
                }
                if (count == 1 && getPlayer().getX() == 5 && getPlayer().getY() == 23){
                    compactTextBox(org, "Ahead of you is a pot of petunias. \nPretty, isn't it?\nHowever, it's in the way...", "", false);
                    compactTextBox(org, "Luckily, you were a student at\n The Magic Academy, so you must know\n plenty of spells to help you, right?", "", false);
                    compactTextBox(org, "WRONG. You dropped out of magic school.\nYou know absolutely nothing. Nothing.\nYou have no memory of how to cast any spell", "", false);
                    compactTextBox(org, "Most spells ARE written down, so if you\n stumble upon some spare magic literature,\n you can just follow the instructions.", "", false);
                    compactTextBox(org, "Unfortunately, your coat pockets\n are empty. Or in this case, robe pockets.\n", "", false);
                    count++;
                }
                if (!holdDownReminder && getPlayer().getX() > 14 && getPlayer().getX() < 20 && getPlayer().getY() == 22){
                    compactTextBox(org, "You can hold down the arrow keys\n to move very quickly", "", false);
                    holdDownReminder = true;
                }
                if (getPlayer().getX() == 87 && getPlayer().getY() == 35){
                    foundSpell1 = true;
                    System.out.println("ASTRAL DART FOUND");
                }
                if (getPlayer().getX() == 80 && getPlayer().getY() == 29){
                    foundSpell2 = true;
                }
                if (getPlayer().getX() == 67 && getPlayer().getY() == 32){
                    if(inMaze == false){
                        compactTextBox(org, "Wow, there's a lot of junk here!\nDoes the owner of this basement\n realize there are other rooms...", "", false);
                        compactTextBox(org, "...in this basement that he can also\n put stuff in?\nSeriously, you woke up in an empty room.", "", false);
                    }
                    inMaze = true;
                    System.out.println("IN THE MAZE....");
                }
                if (count == 2 && getPlayer().getX() > 14 && getPlayer().getX() < 20 && getPlayer().getY() == 22 && (foundSpell1 || foundSpell2)){
                    compactTextBox(org, "Now that you are armed with\n some magic scrolls, you can\n defeat the pot of petunias!", "", false);
                    compactTextBox(org, "Push 'W' to open the menu.\nPush 'A' to confirm an option that\n the cursor is selecting", "", false);
                    compactTextBox(org, "Go to 'Spells' and Push either 'S' or 'D'\n to bind a spell to to keys 'S' and 'D'", "", false);
                    count++;
                }
                if (inMaze && (!foundSpell1 ^ !foundSpell2) && !leavingEarly && getPlayer().getX() == 66 && getPlayer().getY() == 32){
                    compactTextBox(org, "There may be other spells hidden in the maze.\n You may want to head back.", "", false);
                    leavingEarly = true;
                }
                if (inMaze && !(foundSpell1 || foundSpell2) && !leavingEarly && getPlayer().getX() == 66 && getPlayer().getY() == 32){
                    compactTextBox(org, "There are some spells hidden in the maze.\n You may want to head back.", "", false);
                    leavingEarly = true;
                }
                if (count == 3 && getPlayer().getX() == 5 && getPlayer().getY() == 22){
                    compactTextBox(org, "Casting spells is simple:\nPush the 'S' and 'D' key to cast the\n spell bound to its respective key", "", false);
                    count++;
                }
                if (count == 4 && getPlayer().getX() == 5 && getPlayer().getY() == 14){
                    compactTextBox(org, "You've managed to defeat\n The Pot of Petunias!\nCongratulations!", "", false);
                    compactTextBox(org, "As you may have noticed, the bar on the\n top right had depleted a little.\nThat is your mana bar.", "", false);
                    compactTextBox(org, "Casting spells cost mana. You can't cast\n any spells if you run out.", "", false);
                    compactTextBox(org, "Fortunately, your mana regenerates\n shortly after not casting spells\n for a bit.", "", false);
                    compactTextBox(org, "Note: The less mana you spend,\n the less you have to wait before\n your mana refills.", "", false);
                    count++;
                }
                if (count == 5 && ((getPlayer().getX() == 5 && getPlayer().getY() == 6))){
                    compactTextBox(org, "The next room is full of spikes.\n They look like ^","",false);
                    compactTextBox(org, "For some reason the spikes are\n able to shoot magic everywhere.", "", false);
                    compactTextBox(org, "Pushing the 'A' key locks your aim,\n allowing you to comfortably strafe\n while casting spells, dodging their magic", "", false);
                    count++;
                }
                if (count == 6 && (getPlayer().getX() >= 84 && getPlayer().getY() == 16 && getPlayer().getX() <= 91)){
                    count++;
                }
                if (count == 7 && getPlayer().getY() >= 17){
                    compactTextBox(org, "You have probably stumbled upon\n some weapons. You should go to\n the 'Equipment' menu.", "", false);
                    compactTextBox(org, "Use the 'A' key to equip a weapon.", "", false);
                    count++;
                }
                if (getPlayer().getX() > 132){
                    exitCode = "SourcePit";
                }
                if (getPlayer().getHealth() <= 0){
                    exitCode = "die";
                }

                //super.playo.reportPos();
                org.compileImage();

            } catch (InterruptedException ignored) {}
        }
        return exitCode
                ;
    }

    public void startup(){
        ititHitMesh();

        super.playo.goTo(130,10);

        emptyHitMesh();
        art arty = new art();
        String[][] base = art.strToArray(arty.tutForest);
        String[] solids = {"|","-","0","/",",","#","%","$","'"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test");
        org.addLayer(lay1);


        Item dartSpell = new Item ("Astral Dart", "Arcane Spell;\nFires a small bolt of\n pure stardust.", "AstDt", playo, "spell", false);
        dartSpell.dmgSpellDefine(2, 9, 2, "arcane", "|", "-");
        DroppedItem gSpark =  new DroppedItem(this, org, "You found a spell: Astral Dart!", dartSpell, "drops", 87, 35);
        super.addObject(gSpark);

        Item fireSpell = new Item ("Fireball", "Fire Spell;\nUse your imagination.", "FrBll", playo, "spell", true);
        fireSpell.dmgSpellDefine(4, 7, 5, "fire", "6", "9");
        DroppedItem gFire =  new DroppedItem(this, org, "You found a spell: Fireball!", fireSpell, "drops2", 80, 29);
        super.addObject(gFire);

        Item healSpell = new Item ("Heal", "Simple healing spell.", "Heal ", playo, "spell");
        healSpell.altSpellDefine(12, "healing");
        healSpell.setHeal(8);
        DroppedItem gHeal =  new DroppedItem(this, org, "You found a spell: Heal!", healSpell, "drops3", 65, 9);
        super.addObject(gHeal);

        Item fireGlove = new Item ("Pyro Glove", "A glove that's on fire!\n\nPyromancers are quite the\n adventurous people, and so" +
                "\n these gloves became very\n commonplace\n\n+2 Fire Spell Damage", playo, "equipment");
        fireGlove.setEquipvals(0, 0, 0, 0, 2, 0 ,0, "weapon");
        DroppedItem gGlove =  new DroppedItem(this, org, "You found a weapon: Pyro Glove!", fireGlove, "drops4", 85, 15);
        super.addObject(gGlove);

        Item brokenStaff = new Item ("Broken Staff","A staff crafted by a\n dirt-poor student of\n The Magic Academy.\n\nMade of spare wood\n" +
                " and frayed ropes, it's\n no surprise that it\n already snapped in two\n\n+1 (All) Spell Damage", playo, "equipment");
        brokenStaff.setEquipvals(0, 0, 1, 0, 0, 0 ,0, "weapon");
        DroppedItem gStaff =  new DroppedItem(this, org, "You found a weapon: Broken Staff!", brokenStaff, "drops5", 90, 15);
        super.addObject(gStaff);

        PotOfPetunias flowers = new PotOfPetunias(org, this, 5, 19);
        addMortal(flowers);


        int[][] locs = {{33, 36, 42, 47, 49, 53},{10, 9, 2, 6, 10, 9}};
        for (int ii = 0; ii < 6 ; ii++) {
            Spike spike = new Spike(org, this, locs[0][ii], locs[1][ii]);
            spike.setMoveFrq(10);
            addMortal(spike);
        }



        genericInitialize();
    }

    /**
     * Enter the room. IE, start loops and stuff now.
     */
    public String enter(){
        org.compileImage();
        super.playo.frozen = false;
        String output = loop();
        super.playo.frozen = true;
        super.cleanLayersForExit(org);
        objs.clear();
        return output;
    }

    public TutorialBasement(ImageOrg orgo, Player player){
        super.playo = player;
        super.org = orgo;
        org = orgo;
        maxH = org.getWindow().maxH();
        maxW = org.getWindow().maxW();
        super.roomHeight = maxH;
        super.roomWidth = maxW;
        super.index = 1;
    }

}
