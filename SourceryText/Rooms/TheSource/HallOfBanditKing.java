/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryText.Rooms.TheSource;

import SourceryText.Art;
import SourceryText.GameObjects.Player;
import SourceryText.GameObjects.TheSource.Bandit;
import SourceryText.GameObjects.TheSource.BanditKing;
import SourceryText.GameObjects.TheSource.FallingBeehive;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * Here stands the King of the Bandits, a rich and powerful man who blocks your exit through the mountains.
 *
 * @author Jared
 *         <p>
 *         So Far:
 *         > Your adventures in the mountains are coming to a close
 *         > You have broken through the defenses at the Bandit Fortress
 *         <p>
 *         What Generally Happens Here:
 *         > You fight a boss
 *         > You can also kick down a beehive to skip the fight
 */


public class HallOfBanditKing extends Room {

    public HallOfBanditKing(Player player) {
        super(player);
        strRoomName = "HallOfBanditKing";
    }

    private boolean kingLastWordsSpoken = false; //Plot twist: they aren't actually his last words
    private boolean beehiveKnockedDown = false;
    private boolean beehiveReaction = false;

    private boolean fightStarting = true;
    private int kingIntroSeq = 0;

    @Override
    protected String loop(Player play) {
        while (exitCode.equals("")) {
            try {
                Thread.sleep(200);
                if (getMortalCountOf("BanditKing") == 0 && !kingLastWordsSpoken && play.getY() >= 54 && play.getY() <= 66){
                    for (Player winner : players){
                        queueMessage(new FlavorText("*Cough* *Cough*\nNo... I don't want to lose to\n a wimp like you....","Bandit King").setViewerUsername(winner.getUsername()));
                        queueMessage(new FlavorText("You don't deserve the throne...\nI DO!\nYou can't take it from me!","Bandit King").setViewerUsername(winner.getUsername()));
                        queueMessage(new FlavorText("*Wheeze*\n But I have fought to my dying breath.\n There's no more left of me.","Bandit King").setViewerUsername(winner.getUsername()));
                        queueMessage(new FlavorText("Huh? You're frozen like a statue!\n You're taking my place, aren't you?","Bandit King").setViewerUsername(winner.getUsername()));
                        queueMessage(new FlavorText("You're not? Well then, uhhh...\n You can go on now.\n Jump off the cliff if you want to.","Bandit King").setViewerUsername(winner.getUsername()));
                        queueMessage(new FlavorText("Just uhh, *cough* get out of here.\n Go away.","Bandit King").setViewerUsername(winner.getUsername()));
                    }
                    kingLastWordsSpoken = true;
                    org.editLayer(" ", "RoomLayer", 53, 37);
                    removeFromBaseHitMesh(37, 53);
                }
                if (getObjectsAt(37, 59).size() > 0 && beehiveKnockedDown && !beehiveReaction){
                    for (Player jerk : players){
                        queueMessage(new FlavorText("WHAT?!?! Bees!\nAaaaaaaaaaaa!\n Why do I have to be allergic to them?!?!?","Bandit King").setViewerUsername(jerk.getUsername()));
                    }
                    beehiveReaction = true;
                }
                if ((play.getY() >= 54 && play.getY() <= 66) || beehiveReaction){
                    fightStarting = false;
                }
                if (fightStarting && play.getY() <= 71 && play.getY() > 69 && kingIntroSeq == 0){
                    queueMessage(new FlavorText("Carlson! Is that you again?!?!\n For the last time, I do NOT want\n another update on that cat in the tree!","Bandit King").setViewerUsername(play.getUsername()));
                    queueMessage(new FlavorText("This better be important!","Bandit King").setViewerUsername(play.getUsername()));
                    kingIntroSeq++;
                }
                if (fightStarting && play.getY() > 67 && play.getY() <= 69 && kingIntroSeq == 1){
                    queueMessage(new FlavorText("You're not Carlson! I didn't summon you!\n Who even are you? How did you get here?","Bandit King").setViewerUsername(play.getUsername()));
                    queueMessage(new FlavorText("I can't let just some nobody walk\n here unannounced!\nPrepare to die!!!","Bandit King").setViewerUsername(play.getUsername()));
                    getMortalListOf("BanditKing")[0].followingDist = 30;
                    kingIntroSeq++;
                }
                if (play.getY() >= 9 && play.getY() <= 12){
                    setNewRoom("BanditFortress", play,8,89);
                }
                if (play.getY() >= 73){
                    setNewRoom("BanditFortress", play,4,51);
                }
            } catch (InterruptedException ignored) {}
        }
        return exitCode;
    }

    /**
     * Everything that self-updates and can be paused (and acts nonexistent during paused) should go here.
     */
    @Override
    public void addItems() {
        /*
        Bandit notBanditKing = new Bandit(org, this, 37, 55);
        addMortal(notBanditKing);
        */
        BanditKing theBanditKing = new BanditKing(this, 37, 55);
        addMortal(theBanditKing);
    }

    @Override
    protected void specialInspect(int x, int y, Player inspector){
        if (x == 38 && y == 2 && !beehiveKnockedDown){
            queueMessage(new FlavorText("Knock down the beehive?", true, 0).setViewerUsername(inspector.getUsername()));
        }
        if (x == 37 && y == 27) {
            queueMessage(new FlavorText("Jump off the cliff to your fate beyond?", true, 1).setViewerUsername(inspector.getUsername()));
        }
    }

    @Override
    public void respondToQuestion(int qID, Player respondTo) {
        if (qID == 0) {
            FallingBeehive wheee = new FallingBeehive(this, 37, 2);
            addObject(wheee);
            removeFromBaseHitMesh(37, 2);
            org.editLayer("", "RoomLayer", 2, 37);
            beehiveKnockedDown = true;
        }
        if (qID == 1) {
            respondTo.roomName = "switch to zone 2";
            exitCode = "switch to zone 2";
        }
    }

    @Override
    public void startup() {

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.banditKingHall);
        Layer lay1 = new Layer(base, "RoomLayer");
        Art coloring = new Art();

//        lay1.findAndReplace(new SpecialText(":"), new SpecialText(":", null, new Color(51, 43, 38)));
//        lay1.findAndReplace(new SpecialText(";"), new SpecialText(";", null, new Color(51, 43, 38)));
//        lay1.findAndReplace(new SpecialText("^"), new SpecialText("^", null, new Color(51, 43, 38)));

//        lay1.setAllFg(coloring.mountainPallette1);
/*        lay1.influenceAll(coloring.mountainPallette1);
        lay1.findAndReplace(new SpecialText("#", coloring.mountainPallette1), new SpecialText("#", new Color(175, 175, 175), new Color(25, 25, 25)));
        lay1.findAndReplace(new SpecialText("o", coloring.mountainPallette1), new SpecialText("o", coloring.mountainPallette2));
        lay1.findAndReplace(new SpecialText("O", coloring.mountainPallette1), new SpecialText("O", coloring.mountainPallette2));
        lay1.findAndReplace(new SpecialText("0", coloring.mountainPallette1), new SpecialText("0", coloring.mountainPallette2));
        lay1.findAndReplace(new SpecialText(".", coloring.mountainPallette1), new SpecialText(".", coloring.mountainPallette2));

        lay1.findAndReplace(new SpecialText("_", coloring.mountainPallette1), new SpecialText("_", coloring.mountainPallette1));
        lay1.findAndReplace(new SpecialText("|", coloring.mountainPallette1), new SpecialText("|", coloring.mountainPallette1));
        lay1.findAndReplace(new SpecialText("/", coloring.mountainPallette1), new SpecialText("/", coloring.mountainPallette1));
        lay1.findAndReplace(new SpecialText("\\", coloring.mountainPallette1), new SpecialText("\\", coloring.mountainPallette1));
        */
        lay1.influenceAll(coloring.mountainPallette3);
        lay1.findAndReplace(new SpecialText("^", coloring.mountainPallette3), new SpecialText("^", coloring.mountainPallette1));
        lay1.findAndReplace(new SpecialText(":", coloring.mountainPallette3), new SpecialText(":", coloring.mountainPallette1));
        lay1.findAndReplace(new SpecialText(";", coloring.mountainPallette3), new SpecialText(";", coloring.mountainPallette1));
        lay1.findAndReplace(new SpecialText("#", coloring.mountainPallette3), coloring.poundWall);
        lay1.findAndReplace(new SpecialText("8", coloring.mountainPallette3), new SpecialText("8", new Color(200, 200, 75)));
        highlightFlavorText(lay1);
        org.addLayer(lay1);

        initHitMeshes(lay1);
        String[] solids = {";", ":", "^", "O", "o", "O", "#","_","\\","/","|","8","-"};
        addToBaseHitMesh(base, solids);

        addItems();

        genericRoomInitialize();
    }
}
