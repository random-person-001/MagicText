/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.UserScreens;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.TheSource.*;
import SourceryTextb1.Rooms.NewTestRoom;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.Window;

import java.util.TimerTask;
import java.util.Timer;
import java.awt.Color;

/**
 * Main class of MagicText, where everything starts.
 *
 * @author 119184, and a bit of 104410
 */
public class Start {
    private static boolean doDemo = false;
    private static Window game;
    private static ImageOrg org;
    protected static Player player;
    private static String roomID;

    public static void main(String[] args) throws InterruptedException {
        game = new Window();
        Layer base = new Layer(new String[game.maxH()][game.maxW()], "base");
        org = new ImageOrg(game);
        org.addLayer(base);

        if (doDemo) {
            Player player = new Player(org);
            NewTestRoom rooma = new NewTestRoom(player);
            prepLevel(org, game, player, rooma);
            rooma.startup();
            rooma.enter();
        } else {
            WindowConfig wincnfg = new WindowConfig(org);
            wincnfg.config(false);

            Timer time = new Timer();
            time.schedule(new StartCheck(wincnfg), 50, 100);
        }
    }

    private static void runGame(){
        while (!roomID.equals("die")) {
            System.out.println("Entering the room '" + roomID + "'");
            switch (roomID) {
                case "Tutorial":
                    doLevel(new TutorialBasement(player));
                    break;
                case "SourcePit":
                    doLevel(new SourcePit(player));
                    break;
                case "Cliffside":
                    doLevel(new Cliffside(player));
                    break;
                case "SourceCaves":
                    doLevel(new SourceCaves(player));
                    break;
                case "BanditFortress":
                    doLevel(new BanditFortress(player));
                    break;
                default:
                    System.out.println("You were directed to a world which is not yet registered in Start.java." +
                            "Please add it there.  (roomID = " + roomID + ").  Meanwhile, I'll just kill you to ease the pain.");
                    roomID = "die";
                    break;
            }
        }
        System.out.println("<br>Better luck next time!");
    }

    private static void doLevel(Room r){
        prepLevel(org, game, player, r);
        r.startup();
        roomID = r.enter();
    }

    private static void prepLevel(ImageOrg org, Window game, Player player, Room newRoom){
        org.removeAllButPlayer();
        game.clearImage();
        player.setRoom(newRoom);
        newRoom.ownID = roomID;
    }

    private static void introText(String text, int offset, int line){
        System.out.println("Writing: " + text);
        for (int ii = 0; ii < text.length(); ii++){
            org.getLayer("Intro3").setStr(line, ii + offset, String.valueOf(text.charAt(ii)));
        }
    }

    /** Turn a rectangular String with newlines into a String[][] array, without debug info.
     * @param input please include at least one newline in your input
     * @return an array of single-character Strings
     */
    ///////////////////
    // Handy Methods
    // @author Riley
    // Yeah! 
    public static String[][] strToArray(String input) {
        return strToArray(input, false);
    }

    /** Turn a rectangular String with newlines into a String[][] array.
     * @param input please include at least one newline in your input
     * @param debug whether to print debug trash to sys.out
     * @return an array of single-character Strings
     */
    public static String[][] strToArray(String input, boolean debug) {
        int width = input.indexOf("<br>");
        int height = occurrencesOf(input, "<br>");//input.length() / (height);
        if (debug) {
            System.out.println(height);
            System.out.println(width);
        }
        char[][] charresult = new char[height][width];
        input = "<br>" + input; //So it works.
        for (int i = 0; i < height; i++) {
            input = input.substring(1 + input.indexOf("<br>"));
            System.out.println(input.substring(0, width));
            charresult[i] = input.substring(0, width).toCharArray();
        }
        if (debug) {
            System.out.println();
            System.out.println();
            System.out.println(charresult[0]);
            System.out.println(charresult[1]);
            System.out.println(charresult[2]);
            System.out.println(charresult.length);
            System.out.println(charresult[1].length);
            System.out.println("Convert char[][] to String[][]");
        }
        String[][] result = new String[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (result[i][j] == null) { //Use a ternary operator instead?
                    result[i][j] = " ";
                }
                result[i][j] = Character.toString(charresult[i][j]);
            }
            //System.out.println(i);
        }
        return result;
    }

    /**
     * @param input a larger String to search within
     * @param ofWhat a small string which could be found an arbitrary number of times within input
     * @return How many occurrences of String ofWhat are there in String input
     */
    private static int occurrencesOf(String input, String ofWhat) {
        return input.length() - input.replace(ofWhat, "").length();
    }

    private static void intro() throws InterruptedException{
        game.txtArea.setBackground(Color.BLACK);
        Layer lay1 = new Layer(new String[23][46], "Intro1");
        lay1.setStr(10, 23, "@");
        org.addLayer(lay1);
        Thread.sleep(750);

        for (int eff = 0; eff < 40; eff++) {
            game.txtArea.setBackground(new Color(eff, eff, eff, 255));
            Thread.sleep(50);
        }
        Thread.sleep(2000);

        String thisIsYou = "This is you.";
        for (int ii = 0; ii < thisIsYou.length(); ii++) {
            org.editLayer(String.valueOf(thisIsYou.charAt(ii)), "Intro1", 12, 18 + ii);
        }
        Thread.sleep(5000);

        org.removeLayer("Intro1");
        Art arty = new Art();
        String[][] town = Art.strToArray(arty.intro1);
        Layer lay = new Layer(town, "Intro2");
        org.addLayer(lay);
        Layer lay2Title = new Layer(new String[3][46], "Intro2Title", 30, 0);
        org.addLayer(lay2Title);
        org.setCam(0, 10);
        Thread.sleep(500);

        String explain = "Times were simple a couple weeks ago.";
        for (int ii = 0; ii < explain.length(); ii++) {
            org.editLayer(String.valueOf(explain.charAt(ii)), "Intro2Title", 1, 3 + ii);
        }
        Thread.sleep(5000);

        String explainMore = "@'s were people, #'s were walls & terrain";
        for (int ii = 0; ii < explainMore.length(); ii++) {
            org.editLayer(String.valueOf(explainMore.charAt(ii)), "Intro2Title", 2, 3 + ii);
        }
        Thread.sleep(4000);

        for (int pan = 0; pan < 13; pan++) {
            org.moveCam(0, -1);
            Thread.sleep(250);
        }
        Thread.sleep(2000);

        org.removeLayer("Intro2");
        org.removeLayer("Intro2Title");
        Layer lay3 = new Layer(new String[23][46], "Intro3");
        lay3.setStr(10, 22, "@");
        org.addLayer(lay3);
        org.setCam(0, 0);
        Thread.sleep(1500);

        game.txtArea.setForeground(new Color(189, 83, 89, 255));
        String villainIntro = "This is someone else.";
        for (int ii = 0; ii < villainIntro.length(); ii++) {
            org.editLayer(String.valueOf(villainIntro.charAt(ii)), "Intro3", 7, 12 + ii);
        }
        Thread.sleep(4500);

        String goodExcuse = "(To be fair, everyone DOES look identical)";
        for (int ii = 0; ii < goodExcuse.length(); ii++) {
            org.editLayer(String.valueOf(goodExcuse.charAt(ii)), "Intro3", 8, 2 + ii);
        }
        Thread.sleep(5000);

        String goodFollowUp = "He despised that fact. He desired complexity";
        for (int ii = 0; ii < goodFollowUp.length(); ii++) {
            org.editLayer(String.valueOf(goodFollowUp.charAt(ii)), "Intro3", 12, 1 + ii);
        }
        Thread.sleep(3000);

        String resolution = "And so, he journeyed to The Source";
        for (int ii = 0; ii < resolution.length(); ii++) {
            org.editLayer(String.valueOf(resolution.charAt(ii)), "Intro3", 13, 6 + ii);
        }
        Thread.sleep(1500);

        org.clearLayer("Intro3");
        Thread.sleep(1500);

        game.txtArea.setForeground(Color.white);
        int line = 4;
        introText("The Source is a gaping hole in the world.", 1, line);
        Thread.sleep(3000);

        introText("It leads out of the universe,", 1, line + 1);
        introText(" and into its source code", 1, line + 2);
        Thread.sleep(4000);

        introText("Most worlds are closed-source, but", 1, line + 4);
        introText(" this one is special. It's open-source.", 1, line + 5);
        Thread.sleep(4000);

        introText("That person found a rope strong enough", 1, line + 7);
        introText(" to survive exiting the universe", 1, line + 8);
        Thread.sleep(4000);

        introText("He traveled down into The Source", 1, line + 10);
        introText(" ..and found the world's settings...", 1, line + 11);
        Thread.sleep(4000);

        lay3.clear();
        org.removeLayer("Intro3");
        String[][] code = Art.strToArray(arty.intro2);
        lay3 = new Layer(code, "Intro4");
        org.addLayer(lay3);
        org.setCam(0, 18);
        Thread.sleep(5000);

        for (int ii = 0; ii < 18; ii++) {
            org.moveCam(0, -1);
            Thread.sleep(100);
        }
        Thread.sleep(1500);

        for (int ii = 0; ii < 6; ii++) {
            org.editLayer(" ", "Intro4", 18, 35 - ii);
            Thread.sleep(100);
        }
        Thread.sleep(500);

        String TRUE = "TRUE;";
        for (int ii = 0; ii < TRUE.length(); ii++) {
            org.editLayer(String.valueOf(TRUE.charAt(ii)), "Intro4", 18, 30 + ii);
            Thread.sleep(400);
        }
        Thread.sleep(5000);

        lay3.clear();
        String result = "...And that changed EVERYTHING";
        for (int ii = 0; ii < result.length(); ii++) {
            org.editLayer(String.valueOf(result.charAt(ii)), "Intro4", 11, 7 + ii);
        }
        Thread.sleep(3000);

        for (int eff = 39; eff < 255; eff += 2) {
            game.txtArea.setBackground(new Color(eff, eff, eff, 255));
            Thread.sleep(50);
        }
        lay3.clear();
        

                /*
                for (int eff = 255; eff >= 0; eff -= 5) {
                    game.txtArea.setBackground(new Color(eff, eff, eff, 255));
                    Thread.sleep(50);
                }
                */

        game.txtArea.setBackground(Color.BLACK);
    }

    static class StartCheck extends TimerTask {
        WindowConfig lock;
        StartCheck self = this;
        private boolean hasRan = false;

        StartCheck(WindowConfig toCheck){
            lock = toCheck;
        }

        void newGame(){
            player = new Player(org);
            roomID = "Tutorial";
            runGame();
        }

        void buildGame(Player imported){
            Start.roomID = imported.roomName;
            System.out.println(Start.roomID);
            Start.player = imported;
            imported.orgo.setWindow(Start.game); // hopefully doesn't kill anything
            org.terminateClock();
            org = imported.orgo;
            org.resetClock();
            org.printLayers();
            player.resumeFromSave();
            runGame();
        }

        public void run(){
            if (lock.doContinue && !hasRan){
                new MainMenu(org, game, self);
                hasRan = true;
            }
        }

        void doIntro(){
            try {
                System.out.println("Starting intro...");
                intro();
            } catch (InterruptedException e) {
                System.out.println("Intro interrupted.");
            }
        }
    }

}
