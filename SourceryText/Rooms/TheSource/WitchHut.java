/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryText.Rooms.TheSource;

import SourceryText.Art;
import SourceryText.GameObjects.DroppedItem;
import SourceryText.GameObjects.Item;
import SourceryText.GameObjects.Player;
import SourceryText.GameObjects.TheSource.CauldronPuzzle;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * A fun witch hut, with a puzzle!
 *
 * @author 119184
 */


public class WitchHut extends Room {

    public WitchHut(Player player) {
        super(player);
        strRoomName = "WitchHut";
    }

    int fireplaceFireState = 0;

    @Override
    protected String loop(Player play) {
        while (exitCode.equals("")) {
            try {
                Thread.sleep(200);
                if (play.getY() == 13){
                    setNewRoom("SnowyPeak", play, 7, 114);
                } else {
                    Layer roomLayer = org.getLayer("roomLayer");
                    if (roomLayer != null) {
                        Color bubbleColor = new Color(50, 125, 0);
                        Color stewColor = new Color(25, 75, 0);
                        roomLayer.findAndReplace(new SpecialText("O", bubbleColor, stewColor), new SpecialText(" ", bubbleColor, stewColor));
                        roomLayer.findAndReplace(new SpecialText("o", bubbleColor, stewColor), new SpecialText("O", bubbleColor, stewColor));
                        roomLayer.findAndReplace(new SpecialText(".", bubbleColor, stewColor), new SpecialText("o", bubbleColor, stewColor));
                        roomLayer.findAndReplace(new SpecialText(" ", bubbleColor, stewColor), new SpecialText(".", bubbleColor, stewColor), 15);

                        Color fireBkg = new Color(120, 25, 0);
                        switch (fireplaceFireState) {
                            case 0:
                                roomLayer.findAndReplace(new SpecialText("w", new Color(150, 40, 0), fireBkg), new SpecialText("w", new Color(165, 70, 0), fireBkg));
                                break;
                            case 1:
                                roomLayer.findAndReplace(new SpecialText("w", new Color(165, 70, 0), fireBkg), new SpecialText("W", new Color(180, 100, 0), fireBkg));
                                break;
                            case 2:
                                roomLayer.findAndReplace(new SpecialText("W", new Color(180, 100, 0), fireBkg), new SpecialText("W", new Color(195, 130, 0), fireBkg));
                                break;
                            case 3:
                                roomLayer.findAndReplace(new SpecialText("W", new Color(195, 130, 0), fireBkg), new SpecialText("W", new Color(180, 100, 0), fireBkg));
                                break;
                            case 4:
                                roomLayer.findAndReplace(new SpecialText("W", new Color(180, 100, 0), fireBkg), new SpecialText("w", new Color(165, 70, 0), fireBkg));
                                break;
                            case 5:
                                roomLayer.findAndReplace(new SpecialText("w", new Color(165, 70, 0), fireBkg), new SpecialText("w", new Color(150, 40, 0), fireBkg));
                                break;
                        }
                        fireplaceFireState++;
                        if (fireplaceFireState > 5) fireplaceFireState = 0;
                    }
                }
                if (play.getY() >= 13){
                    setNewRoom("SnowyPeak", play, 7,114);
                }
            } catch (InterruptedException ignored) {
            }
        }
        return exitCode;
    }

    /**
     * Everything that self-updates and can be paused (and acts nonexistent during paused) should go here.
     */
    @Override
    public void addItems() {
        Item item2 = new Item("Wool Scarf", "A fluffy scarf.\n\nWhat's not to love in\n a scarf?\n\nThey're magical, comfy, and\n fashionable!\nAwesome, right?", "equipment");
        item2.setEquipvals(2, 0, 0, 0, 0, 1, 0, "weapon");
        DroppedItem forgottenScarf = new DroppedItem(this, "You found a wool scarf!", item2, 29, 5);
        addObject(forgottenScarf);

        Item item7 = new Item("BewitchedScarf", "A scarf imbued with witch\n magic, granting the user\n increased dark and ice\n magic power\n\nIt smells like flowers.", "equipment");
        item7.setEquipvals(1, 3, 0, 0, 0, 2, 2, "weapon");
        DroppedItem rewardScarf = new DroppedItem(this, "You found the Bewitched Scarf!", item7, 46, 10);
        addObject(rewardScarf);

        Item bucket = new Item("Bucket", "How useful! I bet you\n could catch some pretty\n snowflakes with this!", "Buckt", "spell");
        DroppedItem droppedBucket = new DroppedItem(this, "You stole a bucket from the house. \n Did you know that the witch\n got that for christmas one\n year?", bucket, 3, 10);
        addObject(droppedBucket);
    }

    private boolean cloneBusterUsed = false;

    @Override
    protected void specialInspect(int x, int y, Player inspector) {
        if (x == 14 && y == 6){
            puzzler.setOperator(inspector);
            puzzler.menuStartup();
        }
        if (x == 7 && y == 8){
            if (inspector.getItem("Clone Buster","items") != null) {
                clearPlantedText();
                int[][] witchLocs = {{27, 9}, {34, 6}, {40, 6}, {44, 8}, {36, 10}};
                for (int[] coordPair : witchLocs) {
                    org.editLayer(new SpecialText("_", new Color(33, 18, 5), new Color(38, 19, 6)), "roomLayer", coordPair[1], coordPair[0]);
                    removeFromBaseHitMesh(coordPair[0], coordPair[1]);
                }
                String[] witchThankYouWords = {"*Gasp* You made the Clone Buster!\n Let's see if it works....", "It does! They're gone!\n Everything is normal again!\nThank you so much!"};
                queueMessage(new FlavorText(witchThankYouWords[0], "Witch"), inspector);
                queueMessage(new FlavorText(witchThankYouWords[1], "Witch"), inspector);
                inspector.removeItem("Clone Buster", "items");
                cloneBusterUsed = true;
            } else if (cloneBusterUsed){
                queueMessage(new FlavorText(7,8,"I can't thank you enough for\n making that spell!","Witch"), inspector);
            }
        }
    }

    private CauldronPuzzle puzzler = null;

    @Override
    public void fireKeyEvent(KeyEvent event) {
        genericKeyEvent(event);
        if (puzzler != null) {
            switch (event.getKeyCode()){
                case KeyEvent.VK_ENTER:
                    puzzler.getInput("enter");
                    break;
                case KeyEvent.VK_UP:
                    puzzler.getInput("up");
                    break;
                case KeyEvent.VK_DOWN:
                    puzzler.getInput("down");
                    break;
            }
        }
    }

    @Override
    public void startup() {
        puzzler = new CauldronPuzzle(this, null);

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.witchHut);
        Layer lay1 = new Layer(base, "roomLayer");

        //Witches dialogue
        String[] mainWitchDialogue = {"Oh, finally! Someone else!\n I need you pretty badly.","You see, I cloned myself.\n Then the clones decided to clone\n themselves.",
                "Now there's too many for this little\n house of mine.","However there is a spell you can brew\n in that cauldron over there, but I\n can't do it myself.",
                "You see, because I casted the clone spell,\n I can't undo it without undoing me!\n So I need you to undo the spell","Please consult my recipes on the\n bookshelf on the wall."};
        plantText(new FlavorText(7, 8, mainWitchDialogue, "Witch"));

        plantText(new FlavorText(27, 9, "One wardrobe for all of us?\n How can we live in conditions like this?", "Witch"));
        plantText(new FlavorText(34, 6, "I want to cast a mean spell on you, but\n the spell book fell into a\n bookshelf and we can't seem to get it back", "Witch"));
        plantText(new FlavorText(40, 6, "Who fills a whole bookshelf with history\n books? This place needs a tea cookbook\n MINIMUM.", "Witch"));
        plantText(new FlavorText(44, 8, "I hate taking turns on the broomstick!\n It's just not right!", "Witch"));
        plantText(new FlavorText(36, 10, "The witch over there says we're all clones\n of her, but I swear I'm the original one!\n....Says everyone.....", "Witch"));

        highlightFlavorText(lay1);

        //The lore/history of the world
        plantText(new FlavorText(31, 4, "...Of all magic, Arcane is the king;\n The world is simply not complex\n enough to support Fire, Ice, and Dark...", "Book"));
        plantText(new FlavorText(32, 4, "...Arcane is primal; it is magic itself.\n Fire, Ice, and Dark are all false copies\n of it; they are not fit to be magic...", "Book"));
        plantText(new FlavorText(33, 4, "...For decades the debate raged onward;\n Not even the grand council of\n the Magic Academy could end it...", "Book"));

        plantText(new FlavorText(36, 4, "...The discovery of The Source is almost as\n influential as its potential misuse...", "Book"));
        plantText(new FlavorText(37, 4, "...Amidst the endless argument\n over the fate of magic, many took action\n while others continued to argue...", "Book"));
        plantText(new FlavorText(38, 4, "...However, exiting the universe proved\n difficult, until someone found two ropes\n strong enough to survive...", "Book"));

        plantText(new FlavorText(41, 4, "...Unfortunately, the person who discovered\n the ropes strongly opposed its use...", "Book"));
        plantText(new FlavorText(42, 4, "...With his own magical hands he built\n two giant labyrinths to stop all\n intruders, each guarding a single rope...", "Book"));
        plantText(new FlavorText(43, 4, "...Countless breakthroughs were attempted\nAll but one had ultimately failed.\nThat one rope has yet to be found...", "Book"));

        //Recipe for creating the anti-clone potion
        plantText(new FlavorText(5, 5, "1) No two fruits may be added consecutively", "Recipe Book"));
        plantText(new FlavorText(6, 5, "2) The Sistine Apple must be added\n sometime after The Grape of Good Hope", "Recipe Book"));
        plantText(new FlavorText(7, 5, "3) The Bering Date muse be added\n directly before The Mesopotato", "Recipe Book"));
        plantText(new FlavorText(8, 5, "4) The Gordian Nut must be added\n sometime after The Chichen Pizza", "Recipe Book"));
        plantText(new FlavorText(9, 5, "5) The Grape of Good Hope must be added\n sometime after The Mesopotato", "Recipe Book"));
        plantText(new FlavorText(10, 5, "6) The Bering Date cannot be added first", "Recipe Book"));



        Color rugStringColor = new Color (255, 225, 175);
        Color rugOuterColor = new Color (102, 42, 26);
        Color rugInnerColor = new Color (102, 44, 31);
        lay1.findAndReplace(new SpecialText("+"), new SpecialText("+", rugStringColor, rugOuterColor));
        lay1.findAndReplace(new SpecialText("~"), new SpecialText("~", rugStringColor, rugOuterColor));
        lay1.findAndReplace(new SpecialText(":"), new SpecialText(":", rugStringColor, rugOuterColor));
        lay1.findAndReplace(new SpecialText("c"), new SpecialText(" ", null, rugInnerColor));

        lay1.findAndReplace(new SpecialText("_"), new SpecialText("_", new Color(33, 18, 5), new Color(38, 19, 6)));

        lay1.findAndReplace(new SpecialText("S"), new SpecialText(" ", new Color(50, 125, 0), new Color(25, 75, 0)));

        lay1.findAndReplace(new SpecialText("0"), new SpecialText("0", null, new Color(25, 25, 25)));
        lay1.findAndReplace(new SpecialText("|"), new SpecialText("|", null, new Color(25, 25, 25)));
        lay1.findAndReplace(new SpecialText("-"), new SpecialText("-", null, new Color(25, 25, 25)));
        lay1.findAndReplace(new SpecialText("#"), new SpecialText("#", new Color (130, 110, 100), new Color(55, 55, 55)));

        lay1.findAndReplace(new SpecialText(">"), new SpecialText(">", new Color(77, 52, 34), new Color(36, 28, 21)));

        Color[] bookColors =
                {new Color(60, 150, 0),
                new Color(120, 120, 0),
                new Color(0, 100, 220),
                new Color(150, 40, 40)};
        colorBooks(bookColors, lay1);

        lay1.findAndReplace(new SpecialText("f"), new SpecialText("w", new Color(150, 40, 0), new Color(120, 25, 0)));

        org.addLayer(lay1);

        initHitMeshes(lay1);
        String[] solids = {"0", "-", "|", "#", "m", "f", "W"};
        addToBaseHitMesh(base, solids);

        addItems();

        genericRoomInitialize();
    }

    private void colorBooks (Color[] colors, Layer lay){
        for (int ii = 0; ii < colors.length; ii++){
            int prob = (int)(100f / (float)(colors.length - ii));
            //System.out.printf("[WitchHut] prob %1$d : %2$d\n", ii, prob);
            lay.findAndReplace(new SpecialText("m"), new SpecialText("m", colors[ii]),prob);
        }
    }
}
