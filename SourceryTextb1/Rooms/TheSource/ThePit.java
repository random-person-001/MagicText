/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.*;
import SourceryTextb1.GameObjects.TheSource.Spider;
import SourceryTextb1.ImageOrg;
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
                    setNewRoom("Cliffside",11,1);
                }
                if (getPlayer().getY() < 0){
                    setNewRoom("Tutorial",11,1);
                }
                if (count == 0){
                    if (playo.getX() == 109 && playo.getY() == 10) {
                        queueMessage(new FlavorText("Welcome to the rest of the world!", ""));
                        queueMessage(new FlavorText("There are many things to find and explore!<br>Use the 'F' key to inspect things.", ""));
                        queueMessage(new FlavorText("Most capital letters (ex: A, B, C..) out in<br> the world have flavor text <br> accessible through the 'F' key", ""));
                        queueMessage(new FlavorText("Note:<br>You've gotta be facing towards the<br> object you're trying to inspect", ""));
                        queueMessage(new FlavorText("Locking your aim can help you see<br>which direction you're facing in.", ""));
                        queueMessage(new FlavorText("Note #2:<br>Capital letters that are MOVING<br> are usually hostile!", ""));
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

    @Override
    public void startup(){
        ititHitMeshes();

        String[] dennisWords = {"Hey! Ya woke up!<br>How are ya?","Welcome to The Source!<br>I'm Dennis, the owner of the house<br> that ya woke up in.","Someone no-name guy went in this big hole" +
                "<br> and completely overthrew the order<br> of this world.","Then there was this super bright flash,<br> and out goes the lights!","Ya've been in a coma for about<br> two weeks now;" +
                "<br>Ya're latest I've seen to wake up!","Anyway, I decided to haul ya into my<br> basement so that the local wolves<br> don't make dinner out of ya.","I see you've grabbed a couple things" +
                "<br> on the way out;<br>Ya can have them if you want to.","Ya'll probably be fighting wolves<br> and bandits on the way out<br> of these mountains anyway!",".....","Hey, can ya do me a favor?" +
                "<br>I hate all of this text everywhere!<br>I hate all of it!","Ya've probably got nothing else to do,<br> right?","Can ya go fetch me one of those magic<br> ropes that can go down into" +
                "<br> The Source and change things back?","I think ya liked how it was before, right?<br>Well, so do I!","Yar way out is that trail down<br> over there, past The Source.","See ya later!"};
        plantText(new FlavorText(93, 12, dennisWords , "Dennis"));

        String[] doorLocked = {"The door seems to have locked behind you.","The owner must have installed<br> an auto-lock on the door."};
        plantText(new FlavorText(109, 9, doorLocked , ""));

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.sourcePit);
        String[] solids = {".",",",":",";","^","_","#","'","D","X"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test");
        org.addLayer(lay1);

        Spider itsyBitsy = new Spider(this, 59, 30); // blocks way out.
        addMortal(itsyBitsy);

        genericRoomInitialize();
    }

    public ThePit(Player player){
        constructor(player);
        org = player.orgo;
        super.index = 1;
    }
}
