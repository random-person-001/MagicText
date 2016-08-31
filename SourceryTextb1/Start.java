/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.Rooms.*;
import SourceryTextb1.Rooms.TheSource.Mountains;
import SourceryTextb1.Rooms.TheSource.ThePit;
import SourceryTextb1.Rooms.TheSource.TutorialBasement;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Main class of MagicText, where everything starts.
 *
 * @author 119184, and a bit of 104410
 */
public class Start {
    private static boolean doDemo = false;

    private static Window game;
    private static ImageOrg org;

    public static void main(String[] args) throws InterruptedException {
        game = new Window();
        Layer base = new Layer(new String[game.maxH()][game.maxW()], "base");
        org = new ImageOrg(game);
        org.addLayer(base);

        if (doDemo) {
            Player player = new Player(org);
            NewTestRoom rooma = new NewTestRoom(org, player);
            prepLevel(org, game, player, rooma, 0);
            rooma.startup();
            rooma.enter();
        } else {

            WindowConfig wincnfg = new WindowConfig(org);
            wincnfg.config(false);

            Timer time = new Timer();
            time.schedule(new StartCheck(wincnfg), 50, 100);
        }

        // Win screen
        //org.removeAllButPlayer();
        //game.clearImage();
        //levelAnimation(org, -1,2);
    }

    public static void runGame(){
        /*
        if (doIntro) {
            try {
                intro();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */

        Player player = new Player(org);
        String roomID = "Tutorial";

        while (roomID != "die") { //Java 8 ONLY
            switch (roomID) {
                case "Tutorial":
                    System.out.println("Beginning tutorial!");
                    TutorialBasement forest = new TutorialBasement(org, player);
                    prepLevel(org, game, player, forest, 0);
                    forest.startup();
                    roomID = forest.enter();
                    System.out.println("Exiting tutorial.  Going to: " + roomID);
                    break;
                case "SourcePit":
                    System.out.println("Entering The Pit");
                    ThePit pit = new ThePit(org, player);
                    prepLevel(org, game, player, pit, 0);
                    pit.startup();
                    roomID = pit.enter();
                    break;
                case "Mountains":
                    System.out.println("Entering the Mountains");
                    Mountains mtns = new Mountains(org, player);
                    prepLevel(org, game, player, mtns, 0);
                    mtns.startup();
                    roomID = mtns.enter();
                    break;
                default:
                    System.out.println("You were directed to a world which is not yet registered in Start.java." +
                            "Please add it there.  (roomID = " + roomID + ").  Meanwhile, I'll just kill you to ease the pain.");
                    roomID = "die";
                    break;
            }
        }
        System.out.println("\nBetter luck next time!");
    }

    private static void prepLevel(ImageOrg org, Window game, Player player, Room newRoom, int levelNum){
        org.removeAllButPlayer(); //Cleanup, happens when loop is done.
        game.clearImage();
        if (levelNum != 0) {
            levelAnimation(org, levelNum, 2);
        }
        //player.goTo(6,6);
        player.setRoom(newRoom);
    }

    private static void introText(String text, int offset, int line){
        System.out.println("Writing: " + text);
        for (int ii = 0; ii < text.length(); ii++){
            org.getLayer("Intro3").setStr(line, ii + offset, String.valueOf(text.charAt(ii)));
        }
        
    }

    private static void levelAnimation(ImageOrg org, int levelNum) {
        levelAnimation(org, levelNum, 1);
    }

    /**Run an animation with ascii art text for a new level.
     * @param org ImageOrg(aniser)
     * @param levelNum Which level, you say?  -1 is "You Won!"
     * @param style 1 corresponds to dot matrix font; 2 corresponds to a slanted 3d one.
     */
    private static void levelAnimation(ImageOrg org, int levelNum, int style) {
        art art = new art();
        String[][] levelText = new String[1][1];
        String linearText = "";
        // Maybe there's a better way to do this.  Oh well.
        if (style == 1) {
            if (levelNum == 1) {
                linearText = art.levelOne1;
            } else if (levelNum == 2) {
                linearText = art.levelTwo1;
            } else if (levelNum == 3) {
                linearText = art.levelThree1;
            } else if (levelNum == -1) {
                linearText = art.youWon1;
            }
        }
        if (style == 2) {
            if (levelNum == 1) {
                linearText = art.levelOne2;
            } else if (levelNum == 2) {
                linearText = art.levelTwo2;
            } else if (levelNum == 3) {
                linearText = art.levelThree2;
            } else if (levelNum == -1) {
                linearText = art.youWon2;
            }
        }
        levelText = art.strToArray(linearText);
        Layer anima = new Layer(levelText, "Animation");
        org.addLayer(anima);
        org.setCam(-5, -4);
        for (int i=0; i<levelText[0].length + 3; i++){
            org.moveCam(2,0);
            
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {}
        }
        org.setCam(0,0);
        org.removeLayer("Animation");
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
        int width = input.indexOf("\n");
        int height = occurrencesOf(input, "\n");//input.length() / (height);
        if (debug) {
            System.out.println(height);
            System.out.println(width);
        }
        char[][] charresult = new char[height][width];
        input = "\n" + input; //So it works.
        for (int i = 0; i < height; i++) {
            input = input.substring(1 + input.indexOf("\n"));
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
    
    private static String[][] makeABox(int width, int height){
        String[][] output = new String[height][width];
        for(int ii = 0; ii < height; ii++){
            for (int iii = 0 ; iii < width; iii++){
                if (ii == 0 || ii == height - 1){
                    if (iii == 0 || iii == width - 1){
                        output[ii][iii] = "O";
                    } else {
                        output[ii][iii] = "-";
                    }
                } else {
                    if (iii == 0 || iii == width - 1){
                        output[ii][iii] = "|";
                    } else if (ii % 3 == 0 && iii % 7 == 0){
                        output[ii][iii] = ".";
                    } else {
                        output[ii][iii] = " ";
                    }
                }
            }
        }
        return output;
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

        art arty = new art();
        String[][] town = art.strToArray(arty.intro1);
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

        org.printLayers();
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

        String[][] code = art.strToArray(arty.intro2);
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

    protected static class StartCheck extends TimerTask {
        WindowConfig lock;
        StartCheck self = this;
        private boolean playedAndDied = false;
        private boolean hasRan = false;

        protected StartCheck(WindowConfig toCheck){
            lock = toCheck;
        }

        public void startGame(){
            runGame();
        }

        public void run(){
            Layer endingScene;
            if (lock.doContinue && !playedAndDied && !hasRan){
                new MainMenu(org, game, self); // TODO make this entry point less obscure
                hasRan = true;
            } else {
                //System.out.println("Game hasn't started yet!");
            }
        }

        public void doIntro(){
            try {
                System.out.println("Starting intro...");
                intro();
            } catch (InterruptedException e) {
                System.out.println("Intro interrupted.");
            }
        }
    }

}
