/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.TheSource.PotOfPetunias;
import SourceryTextb1.GameObjects.TheSource.Spider;
import SourceryTextb1.GameObjects.DroppedItem;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.Item;

import SourceryTextb1.Rooms.Room;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;

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
 *      > An innocent pot of petunias is murdered by you, only because the developers felt like playing a cruel god
 *      > Some starter spells are aquired
 */


public class TutorialBasement extends Room {
    private ImageOrg org;

    @Override
    protected String loop(){
        int count = 0;
        boolean foundSpell1 = false;
        boolean foundSpell2 = false;
        boolean inMaze = false;
        boolean leavingEarly = false;

        boolean holdDownReminder = false;

        while (exitCode.equals("")){
            try {
                Thread.sleep(20);

                if (count == 0){
                    queueMessage(new FlavorText("You've woken up in a basement somewhere.\nWoah, there's now lots of text everywhere!", "", true));
                    queueMessage(new FlavorText("You should explore the basement!\nUse the arrow keys to navigate the place.", ""));
                    count++;
                }
                if (count == 1 && getPlayer().getX() == 5 && getPlayer().getY() == 23){
                    queueMessage(new FlavorText("Ahead of you is a pot of petunias. \nPretty, isn't it?\nHowever, it's in the way...", ""));
                    queueMessage(new FlavorText("Luckily, you were a student at\n The Magic Academy, so you must know\n plenty of spells to help you, right?", ""));
                    queueMessage(new FlavorText("WRONG. You dropped out of magic school.\nYou know absolutely nothing. Nothing.\nYou have no memory of how to cast any spell", ""));
                    queueMessage(new FlavorText("Most spells ARE written down, so if you\n stumble upon some spare magic literature,\n you can just follow the instructions.", ""));
                    queueMessage(new FlavorText("Unfortunately, your coat pockets\n are empty. Or in this case, robe pockets.\n", ""));
                    count++;
                }
                if (!holdDownReminder && getPlayer().getX() > 14 && getPlayer().getX() < 20 && getPlayer().getY() == 22){
                    queueMessage(new FlavorText("Hold SPACE while moving to sprint.\n However you aren't that very athletic,\n so you get tired very quickly", ""));
                    holdDownReminder = true;
                }
                if (getPlayer().getX() == 87 && getPlayer().getY() == 35){
                    foundSpell1 = true;
                    //System.out.println("ASTRAL DART FOUND");
                }
                if (getPlayer().getX() == 80 && getPlayer().getY() == 29){
                    foundSpell2 = true;
                }
                if (getPlayer().getX() == 67 && getPlayer().getY() == 32){
                    if(!inMaze){
                        queueMessage(new FlavorText("Wow, there's a lot of junk here!\nDoes the owner of this basement\n realize there are other rooms...", ""));
                        queueMessage(new FlavorText("...in this basement that he can also\n put stuff in?\nSeriously, you woke up in an empty room.", ""));
                    }
                    inMaze = true;
                    //System.out.println("IN THE MAZE....");
                }
                if (count == 2 && getPlayer().getX() > 14 && getPlayer().getX() < 20 && getPlayer().getY() == 22 && (foundSpell1 || foundSpell2)){
                    queueMessage(new FlavorText("Now that you are armed with\n some magic scrolls, you can\n defeat the pot of petunias!", ""));
                    queueMessage(new FlavorText("Push 'W' to open the menu.\nPush 'A' to confirm an option that\n the cursor is selecting", ""));
                    queueMessage(new FlavorText("Go to 'Spells' and Push either 'S' or 'D'\n to bind a spell to to keys 'S' and 'D'", ""));
                    count++;
                }
                if (inMaze && (!foundSpell1 ^ !foundSpell2) && !leavingEarly && getPlayer().getX() == 66 && getPlayer().getY() == 32){
                    queueMessage(new FlavorText("There may be other spells hidden in the maze.\n You may want to head back.", ""));
                    leavingEarly = true;
                }
                if (inMaze && !(foundSpell1 || foundSpell2) && !leavingEarly && getPlayer().getX() == 66 && getPlayer().getY() == 32){
                    queueMessage(new FlavorText("There are some spells hidden in the maze.\n You may want to head back.", ""));
                    leavingEarly = true;
                }
                if (count == 3 && getPlayer().getX() == 5 && getPlayer().getY() == 22){
                    queueMessage(new FlavorText("Casting spells is simple:\nPush the 'S' and 'D' key to cast the\n spell bound to its respective key", ""));
                    count++;
                }
                if (count == 4 && getPlayer().getX() == 5 && getPlayer().getY() == 14){
                    queueMessage(new FlavorText("You've managed to defeat\n The Pot of Petunias!\nCongratulations!", ""));
                    queueMessage(new FlavorText("As you may have noticed, your meter\n at the top-right had depleted a bit.\nThat is your mana bar", ""));
                    queueMessage(new FlavorText("Casting spells and sprinting\n costs mana. Fortunately, mana will\n regenerate shortly after.", "", false));
                    queueMessage(new FlavorText("Note: The less mana you spend,\n the less you have to wait before\n your mana refills.", "", false));
                    count++;
                }
                if (count == 5 && ((getPlayer().getX() == 5 && getPlayer().getY() == 6))){
                    queueMessage(new FlavorText("It seems there is a rather large\n spider in the next room.","",false));
                    queueMessage(new FlavorText("Luckily, it doesn't know any magic,\n is very slow moving,\n and it doesn't have that much health", "", false));
                    queueMessage(new FlavorText("Pushing the 'A' key locks your aim,\n allowing you to comfortably strafe\n while casting spells", "", false));
                    queueMessage(new FlavorText("Use this technique to effortlessly\n dispatch the spider.", "", false));
                    count++;
                }
                if (count == 6 && (getPlayer().getX() >= 84 && getPlayer().getY() == 16 && getPlayer().getX() <= 91)){
                    count++;
                }
                if (count == 7 && getPlayer().getY() >= 17){
                    queueMessage(new FlavorText("You have probably stumbled upon\n some weapons. You should go to\n the 'Equipment' menu.", "", false));
                    queueMessage(new FlavorText("Use the 'A' key to equip a weapon.", "", false));
                    count++;
                }
                if (getPlayer().getX() > 132){
                    setNewRoom("SourcePit",109 , 10);
                }

            } catch (InterruptedException ignored) {}
        }
        return exitCode;
    }

    @Override
    public void startup(){
        ititHitMeshes();

        super.playo.goTo(20,29);
        super.playo.roomName = "Tutorial";

        FlavorText playerStart = new FlavorText(20, 29, "You start here!", "");
        plantText(playerStart);

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.tutForest);
        String[] solids = {"|","-","0","/",",","#","%","$","'"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test", 0, 0, true, false, false);
        org.addLayer(lay1);

        Item dartSpell = new Item ("Astral Dart", "Arcane Spell;\nFires a small bolt of\n pure stardust.", "AstDt", playo, "spell", false);
        dartSpell.dmgSpellDefine(2, 9, 2, "arcane", "|", "-");
        DroppedItem gSpark =  new DroppedItem(this, org, "You found a spell: Astral Dart!", dartSpell, 87, 35);
        super.addObject(gSpark);

        Item fireSpell = new Item ("Fireball", "Fire Spell;\nUse your imagination.", "FrBll", playo, "spell", true);
        fireSpell.dmgSpellDefine(4, 7, 5, "fire", "6", "9");
        DroppedItem gFire =  new DroppedItem(this, org, "You found a spell: Fireball!", fireSpell, 80, 29);
        super.addObject(gFire);

        Item healSpell = new Item ("Heal", "Simple healing spell.", "Heal ", playo, "spell");
        healSpell.altSpellDefine(12, "healing");
        healSpell.setHeal(8);
        DroppedItem gHeal =  new DroppedItem(this, org, "You found a spell: Heal!", healSpell, 65, 9);
        super.addObject(gHeal);

        Item fireGlove = new Item("Pyro Glove", "A glove that's on fire!\n\nPyromancers are quite the\n adventurous people, and so" +
                "\n these gloves became very\n commonplace\n\n+2 Fire Spell Damage", playo, "equipment");
        fireGlove.setEquipvals(0, 0, 0, 0, 2, 0 ,0, "weapon");
        DroppedItem gGlove =  new DroppedItem(this, org, "You found a weapon: Pyro Glove!", fireGlove, 85, 15);
        super.addObject(gGlove);

        Item brokenStaff = new Item ("Broken Staff","A staff crafted by a\n dirt-poor student of\n The Magic Academy.\n\nMade of spare wood\n" +
                " and frayed ropes, it's\n no surprise that it\n already snapped in two\n\n+1 (All) Spell Damage", playo, "equipment");
        brokenStaff.setEquipvals(0, 0, 1, 0, 0, 0 ,0, "weapon");
        DroppedItem gStaff =  new DroppedItem(this, org, "You found a weapon: Broken Staff!", brokenStaff, 90, 15);
        super.addObject(gStaff);


        //Item magicTater = new Item ("Magic Potato","How lucky! This eccentric\n potato can permanently\n increase either your\n Max HP or Max Mana.\n\nNOTE: it's permanent.", playo, "item");
        //DroppedItem gTater =  new DroppedItem(this, org, "You found a magic potato!", magicTater, "drops6", 20, 31);
        //super.addObject(gTater);

        PotOfPetunias flowers = new PotOfPetunias(org, this, 5, 19);
        addMortal(flowers);

        Spider spoidur = new Spider(this, 39, 7);
        addMortal(spoidur);

        genericRoomInitialize();
    }


    public TutorialBasement(Player player){
        constructor(player);
        org = player.orgo;
    }

}
