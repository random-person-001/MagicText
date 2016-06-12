/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.Rooms.*;


/**
 * Main class of MagicText.
 *
 * @author 119184, and a bit of 104410
 *
 *
 * Hey Jared, where should we discuss things?  Email?  Slack?  Dropbox comments?  Github comments?  Google Chats?
 *
 */
public class Start {

    public static void main(String[] args) throws InterruptedException {
        Window game = new Window();
        Layer base = new Layer(new String[game.maxH()][game.maxW()], "base");
        ImageOrg org = new ImageOrg(game);
        org.addLayer(base);

        TestRoom roomOne = new TestRoom(org);
        Player player = new Player(org, roomOne);
        levelAnimation(org, 1,2);
        roomOne.startup(org, player);
        roomOne.enter(player);

        BeginningRoom broom = new BeginningRoom(org);
        prepLevel(org, game, player, broom, 1);
        broom.startup(player);
        broom.enter();

        RoomTwo roomTwo = new RoomTwo(org);
        prepLevel(org, game, player, roomTwo, 2);
        roomTwo.startup(org, player);
        roomTwo.enter(player);

        RoomThree roomThree = new RoomThree(org);
        prepLevel(org, game, player, roomThree, 3);
        roomThree.startup(org, player);
        roomThree.enter(player);


        // Win screen
        org.removeAllButPlayer();
        game.clearImage();
        levelAnimation(org, -1,2);


        // Just end.
    }

    private static void prepLevel(ImageOrg org, Window game, Player player, Room newRoom, int levelNum){
        org.removeAllButPlayer(); //Cleanup, happens when loop is done.
        game.clearImage();
        levelAnimation(org, levelNum,2);
        player.goTo(6,6);
        player.changeRoom(newRoom);
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
            org.compileImage();
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
}
