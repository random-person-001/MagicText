/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryText.Rooms.TheSource;

import SourceryText.Art;
import SourceryText.GameObjects.Player;
import SourceryText.GameObjects.TheSource.Spider;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * The initial view of the Source Pit.
 *
 * @author 119184
 *         <p>
 *         So Far:
 *         > You have been introduced to the backstory
 *         > You've mastered the Tutorial Basement
 *         <p>
 *         What Generally Happens Here:
 *         > The owner of the house is sitting at the edge of the hole. He convinces you to put the world back in order.
 *         > Then you move on to the next level, where a cloned witch has an identity crisis!
 */


public class SourcePit extends Room {

    public SourcePit(Player player) {
        super(player);
        strRoomName = "SourcePit";
    }

    @Override
    protected String loop(Player play) {
        int count = 0;

        while (exitCode.equals("")) {
            try {
                Thread.sleep(20);
                //updateObjs(20);
                if (play.getY() > 42) {
                    setNewRoom("Cliffside", play, 1, 11);
                }
                if (play.getY() < 0) {
                    setNewRoom("TutorialBasement", play, 1, 11);
                }
                if (count == 0) {
                    if (play.getX() == 109 && play.getY() == 10) {
                        queueMessage(new FlavorText("Welcome to the rest of the world!", "").setViewerUsername(play.getUsername()));
                        queueMessage(new FlavorText("There are many things to find and explore!\nUse the 'F' key to inspect things.", "").setViewerUsername(play.getUsername()));
                        queueMessage(new FlavorText("Things to look at and interact with are\n usually colored teal-ish blue.\n It is usually helpful to inspect them", "").setViewerUsername(play.getUsername()));
                        queueMessage(new FlavorText("Sometimes, they also buttons and\n triggers for various things.\n Fun!", "").setViewerUsername(play.getUsername()));
                    }
                    count++;
                }

                if (play.getX() == 87 && play.getY() == 6) {
                    System.out.println("Nooo!  You're escaping!");
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

        Spider itsyBitsy = new Spider(this, 59, 30); // blocks way out.
        addMortal(itsyBitsy);

    }

    @Override
    public void startup() {
        String[] dennisWords = {"Hey! Ya woke up!\nHow are ya?", "Welcome to The Source!\nI'm Dennis, the owner of the house\n that ya woke up in.", "Someone no-name guy went in this big hole" +
                "\n and completely overthrew the order\n of this world.", "Then there was this super bright flash,\n and out goes the lights!", "Ya've been in a coma for about\n two weeks now;" +
                "\nYa're latest I've seen to wake up!", "Anyway, I decided to haul ya into my\n basement so that the local wolves\n don't make dinner out of ya.", "I see you've grabbed a couple things" +
                "\n on the way out;\nYa can have them if you want to.", "Ya'll probably be fighting wolves\n and bandits on the way out\n of these mountains anyway!", ".....", "Hey, can ya do me a favor?" +
                "\nI hate all of this text everywhere!\nI hate all of it!", "Ya've probably got nothing else to do,\n right?", "Can ya go fetch me one of those magic\n ropes that can go down into" +
                "\n The Source and change things back?", "I think ya liked how it was before, right?\nWell, so do I!", "Yar way out is that trail down\n over there, past The Source.", "See ya later!"};
        plantText(new FlavorText(93, 12, dennisWords, "Dennis"));

        String[] doorLocked = {"The door seems to have locked behind you.", "The owner must have installed\n an auto-lock on the door."};
        plantText(new FlavorText(109, 9, doorLocked, ""));

        Art arty = new Art();

        /*
        int lineLength = arty.intro2.indexOf("\n") + 1;
        String source = arty.intro2.substring(lineLength * 3, lineLength * (3+13));
        //System.out.println("Source: '" + source + "'");
        Layer sourceCode = new Layer(Art.strToArray(source), "sourceCode");
        sourceCode.influenceAll(new Color(18, 18, 18));
        sourceCode.setPos(51,17);
        org.addLayer(sourceCode);
        */



        String[][] base = Art.strToArray(arty.sourcePit);
        Layer lay1 = new Layer(base, "base");

        lay1.findAndReplace(new SpecialText(":"), new SpecialText(":", arty.mountainPallette1, new Color(51, 43, 38)));
        lay1.findAndReplace(new SpecialText(";"), new SpecialText(";", arty.mountainPallette1, new Color(51, 43, 38)));
        lay1.findAndReplace(new SpecialText("^"), new SpecialText("`", arty.mountainPallette1, new Color(51, 43, 38)));

        lay1.findAndReplace(new SpecialText("."), new SpecialText(".", arty.mountainPallette1, new Color(26, 22, 19)));
        lay1.findAndReplace(new SpecialText("'"), new SpecialText("'", arty.mountainPallette1, new Color(26, 22, 19)));
        lay1.findAndReplace(new SpecialText(","), new SpecialText(",", arty.mountainPallette1, new Color(26, 22, 19)));
        lay1.findAndReplace(new SpecialText("o"), new SpecialText("o", new Color(100,100,100), null));

        //System.out.println("[SourcePit]");
        for (int r=0; r<lay1.getRows(); r++){
            for (int c=0; c<lay1.getColumns(); c++){
                String s = lay1.getStr(r,c);
                SpecialText st = lay1.getSpecTxt(r,c);
                //System.out.print(s);
                if (r > 10 && (s.equals("\\") || s.equals("|") || s.equals("/"))){
                    //System.out.println(1*(r-11.0)/18.0);
                    int depth = 255 - (int) (255 * (r-11.0)/18.0);
                    //System.out.println(depth);
                    st.setForeground(arty.mountainPallette1);
                    st.setInfluencedForegroundColor(new Color(depth,depth,depth));
                }
                else if (r < 10 && (s.equals("\\") || s.equals("/") || s.equals("|") || s.equals("_") || s.equals("-")|| s.equals("v"))){ // color roof and house
                    st.setForeground(new Color(150,151,100));
                }
                else if (r < 10 && (s.equals("#"))){ // color roof and house
                    //st.setForeground(new Color(90,60,50));
                    st.setForeground(new Color(240,230,200));
                    st.setBackground(new Color(20,21,5));
                }
            }
            //System.out.println();
        }
        //System.out.println("[/SourcePit]");

        highlightFlavorText(lay1);
        org.addLayer(lay1);

        initHitMeshes(lay1);
        String[] solids = {".", ",", ":", ";", "^", "_", "#", "'", "D", "X"};
        addToBaseHitMesh(base, solids);

        addItems();

        genericRoomInitialize();
    }
}
