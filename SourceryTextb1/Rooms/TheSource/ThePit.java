/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.GameObjects.*;
import SourceryTextb1.GameObjects.TheSource.Spider;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.art;

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


public class ThePit extends Room {
    private ImageOrg org;

    @Override
    protected String loop(){
        int count = 0;

        while (exitCode.equals("")){
            try {
                Thread.sleep(20);
                //updateObjs(20);
                if (getPlayer().getY() > 43){
                    setNewRoom("Mountains",11,1);
                }
                if (count == 0){
                    if (playo.getX() == 109 && playo.getY() == 10) {
                        queueMessage(new FlavorText("Welcome to the rest of the world!", ""));
                        queueMessage(new FlavorText("There are many things to find and explore!\nUse the 'F' key to inspect things.", ""));
                        queueMessage(new FlavorText("Most capital letters (ex: A, B, C..) out in\n the world have flavor text \n accessible through the 'F' key", ""));
                        queueMessage(new FlavorText("Note:\nYou've gotta be facing towards the\n object you're trying to inspect", ""));
                        queueMessage(new FlavorText("Locking your aim can help you see\nwhich direction you're facing in.", ""));
                        queueMessage(new FlavorText("Note #2:\nCapital letters that are MOVING\n are usually hostile!", ""));
                    }
                    count++;
                }

                if (getPlayer().getX() == 87 && getPlayer().getY() == 6){
                    System.out.println("Nooo!  You're escaping!");
                }

            } catch (InterruptedException ignored) {}
        }
        return exitCode;
    }

    public void startup(){
        ititHitMeshes();
        super.playo.roomName = "ThePit";

        String[] dennisWords = {"Hey! Ya woke up!\nHow are ya?","Welcome to The Source!\nI'm Dennis, the owner of the house\n that ya woke up in.","Someone no-name guy went in this big hole" +
                "\n and completely overthrew the order\n of this world.","Then there was this super bright flash,\n and out goes the lights!","Ya've been in a coma for about\n two weeks now;" +
                "\nYa're latest I've seen to wake up!","Anyway, I decided to haul ya into my\n basement so that the local wolves\n don't make dinner out of ya.","I see you've grabbed a couple things" +
                "\n on the way out;\nYa can have them if you want to.","Ya'll probably be fighting wolves\n and bandits on the way out\n of these mountains anyway!",".....","Hey, can ya do me a favor?" +
                "\nI hate all of this text everywhere!\nI hate all of it!","Ya've probably got nothing else to do,\n right?","Can ya go fetch me one of those magic\n ropes that can go down into" +
                "\n The Source and change things back?","I think ya liked how it was before, right?\nWell, so do I!","Yar way out is that trail down\n over there, past The Source.","See ya later!"};
        plantText(new FlavorText(93, 12, dennisWords , "Dennis"));

        String[] doorLocked = {"The door seems to have locked behind you.","The owner must have installed\n an auto-lock on the door."};
        plantText(new FlavorText(109, 9, doorLocked , ""));

        art arty = new art();
        String[][] base = art.strToArray(arty.sourcePit);
        String[] solids = {".",",",":",";","^","_","#","'","D","X"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test");
        org.addLayer(lay1);

        Spider itsyBitsy = new Spider(this, 57, 42); // blocks way out.
        addMortal(itsyBitsy);

        genericRoomInitialize();
    }

    public ThePit(Player player){
        constructor(player);
        org = player.orgo;
        super.index = 1;
    }
}
