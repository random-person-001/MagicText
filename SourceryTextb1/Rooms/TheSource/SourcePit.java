/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.*;
import SourceryTextb1.GameObjects.TheSource.Spider;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;

/**
 * The initial view of the Source Pit.
 * @author 119184
 *
 * So Far:
 *  > You have been introduced to the backstory
 *  > You've mastered the Tutorial Basement
 *
 * What Generally Happens Here:
 *  > The owner of the house is sitting at the edge of the hole. He convinces you to put the world back in order.
 *  > Then you move on to the next level, where a cloned witch has an identity crisis!
 */


public class SourcePit extends Room {

    public SourcePit(Player player){
        super(player);
    }

    @Override
    protected String loop(Player play){
        int count = 0;

        while (exitCode.equals("")){
            try {
                Thread.sleep(20);
                //updateObjs(20);
                if (play.getY() > 43){
                    setNewRoom("Cliffside", play, 1, 11);
                }
                if (play.getY() < 0){
                    setNewRoom("TutorialBasement", play, 1, 11);
                }
                if (count == 0){
                    if (play.getX() == 109 && play.getY() == 10) {
                        queueMessage(new FlavorText("Welcome to the rest of the world!", "").setViewerUsername(play.getUsername()));
                        queueMessage(new FlavorText("There are many things to find and explore!\nUse the 'F' key to inspect things.", "").setViewerUsername(play.getUsername()));
                        queueMessage(new FlavorText("Things to look at and interact with are\n usually colored teal-ish blue.\n It is usually helpful to inspect them", "").setViewerUsername(play.getUsername()));
                        queueMessage(new FlavorText("Sometimes, they also buttons and\n triggers for various things.\n Fun!", "").setViewerUsername(play.getUsername()));
                    }
                    count++;
                }

                if (play.getX() == 87 && play.getY() == 6){
                    System.out.println("Nooo!  You're escaping!");
                }

            } catch (InterruptedException ignored) {}
        }
        return exitCode;
    }

    /**
     * Everything that self-updates and can be paused (and acts nonexistent during paused) should go here.
     */
    @Override
    public void addItems(){
        ititHitMeshes();

        Spider itsyBitsy = new Spider(this, 59, 30); // blocks way out.
        addMortal(itsyBitsy);

    }

    @Override
    public void startup(){

        addItems();
        String[] dennisWords = {"Hey! Ya woke up!\nHow are ya?","Welcome to The Source!\nI'm Dennis, the owner of the house\n that ya woke up in.","Someone no-name guy went in this big hole" +
                "\n and completely overthrew the order\n of this world.","Then there was this super bright flash,\n and out goes the lights!","Ya've been in a coma for about\n two weeks now;" +
                "\nYa're latest I've seen to wake up!","Anyway, I decided to haul ya into my\n basement so that the local wolves\n don't make dinner out of ya.","I see you've grabbed a couple things" +
                "\n on the way out;\nYa can have them if you want to.","Ya'll probably be fighting wolves\n and bandits on the way out\n of these mountains anyway!",".....","Hey, can ya do me a favor?" +
                "\nI hate all of this text everywhere!\nI hate all of it!","Ya've probably got nothing else to do,\n right?","Can ya go fetch me one of those magic\n ropes that can go down into" +
                "\n The Source and change things back?","I think ya liked how it was before, right?\nWell, so do I!","Yar way out is that trail down\n over there, past The Source.","See ya later!"};
        plantText(new FlavorText(93, 12, dennisWords , "Dennis"));

        String[] doorLocked = {"The door seems to have locked behind you.","The owner must have installed\n an auto-lock on the door."};
        plantText(new FlavorText(109, 9, doorLocked , ""));

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.sourcePit);
        String[] solids = {".",",",":",";","^","_","#","'","D","X"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test");
        Art coloring = new Art();
        lay1.influenceAll(coloring.mountainPallette1);
        highlightFlavorText(lay1);
        org.addLayer(lay1);

        genericRoomInitialize();
    }

    public SourcePit(Player player){
        super.strRoomName = "SourcePit";
        constructor(player);
    }
}
