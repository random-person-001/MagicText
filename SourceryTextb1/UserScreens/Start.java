/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.UserScreens;

import SourceryTextb1.*;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.PlayerKeyPressListener;
import SourceryTextb1.GameObjects.TheSource.Troll;
import SourceryTextb1.Rooms.TheSource.*;
import SourceryTextb1.Rooms.NewTestRoom;
import SourceryTextb1.Rooms.Room;

import java.io.IOException;
import java.util.*;
import java.awt.Color;
import java.util.concurrent.RunnableFuture;

/**
 * Main class of MagicText, where everything starts.
 *
 * @author 119184, and a bit of 104410
 */
public class Start {
    private static boolean doDemo = false;
    private static Window game;
    private static ImageOrg org;
    private static List<Player> playerList; // for multiplayer!

    public static void main(String[] args) throws InterruptedException {
        game = new Window();
        org = new ImageOrg(game);

        if (doDemo) {
            Player player = new Player(null, org,0);
            player.goTo(4,4);
            playerList = new ArrayList<>();
            playerList.add(player);
            NewTestRoom rooma = new NewTestRoom(player);
            org.removeAllButPlayer();
            org.roomBackground = Color.BLACK;
            player.setRoom(rooma);
            rooma.startup();
            rooma.enter(player);
            while (rooma.enemies.size() > 0){}
        } else {
            WindowConfig wincnfg = new WindowConfig(org);
            wincnfg.config(false);

            Timer time = new Timer();
            time.schedule(new StartCheck(wincnfg), 50, 100);
        }
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

    private static void intro() throws InterruptedException{
        game.txtArea.setBackground(Color.BLACK);
        Layer lay1 = new Layer(new String[23][46], "Intro1");
        lay1.setStr(10, 23, "@");
        org.addLayer(lay1);
        Thread.sleep(750);
        org.printLayers();

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

        void newGame(int numPlayers){
            System.out.println("[-]\n\\/\\/\\/\\/\\/\\/\\\n  CREATING NEW GAME\n\\/\\/\\/\\/\\/\\/\\\n");
            if (numPlayers < 1){
                return;
            }
            Player player = new Player(null, org,0);
            player.roomName = "TutorialBasement";
            GameInstance master = new GameInstance(player);
            player.setGameInstance(master);
            new Thread(() -> master.runGame(player)).start();

            // For multiplayer
            for (int i=1; i<numPlayers; i++) {
                System.out.println("Adding multiplayer player #"+i);
                SlaveGameInstance instance = new SlaveGameInstance(master);
                new Thread(instance::runGameAsSlave).start();
            }

            // Only return from method when game finished.
            /*
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                if (playerList.get(0).roomName.equals("die")){
                    org.resetClock();
                    return;
                }
            }
            */
        }

        void buildGame(GameInstance instance){
            Player imported = instance.getProtaganist();
            imported.orgo.setWindow(game); // hopefully doesn't kill anything
            org.terminateClock();
            org = imported.orgo;
            org.resetClock();
            imported.resumeFromSave();
            if (imported.getHasLocalWindow()) {
                System.out.println("Has local window, adding listener");
                PlayerKeyPressListener kl = new PlayerKeyPressListener(imported);
                game.txtArea.addKeyListener(kl);
            }
            new Thread(() -> instance.runGame(imported)).start();
        }

        public void run(){
            if (lock.doContinue && !hasRan){
                new MainMenu(org, game, self);
                hasRan = true;
            }
        }

        void doIntro(){
            System.out.println("Starting intro...");
            (new Thread() {
                public void run() {
                    try {
                        intro();
                        System.out.println("Intro finished.");
                        new MainMenu(org, game, self);
                    } catch (InterruptedException e) {
                        System.out.println("Intro interrupted.");
                    }
                }
            }).start(); // I think this should be the only thread in the program at this point, all others should have fallen through
        }

        void doNetworkClient(String serverName) {
            NetworkClient networker = new NetworkClient();
            try {
                networker.main(serverName);
            } catch (IOException e) {
                e.printStackTrace();
                networker.attemptCancel();
            }
        }
    }

}
