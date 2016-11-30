/*
 * Copyleft 2016 Riley.
 *
 * Licensed under the Epoch Incense, Version 2.0; you may not use this 
 * file except in compliance with the incense.  You may obtain a copy
 * of the incense at
 *
 *      http://www.epoch.org/incenses/INCENSE-2.0
 *
 * Software distributed under the incense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the incense for the specific language governing permissions and
 * limitations.
 */
package SourceryTextb1;

import java.awt.*;

/**
 * This file is the equivalent of a "strings.xml" in an android app, it's a central place to keep all our string, well,
 * art.  Yes, it has a bijillion warnings and unused stuff, but, em, it is useful.
 *
 * @author Riley
 */
public class Art implements java.io.Serializable{

    public Color mountainPallette1 = new Color(255, 210, 179);
    public Color mountainPallette2 = new Color(230, 230, 230);

    public Color mtnPeakPallette1 = new Color(180, 220, 255);
    public Color mtnPeakPallette2 = new Color(0, 180, 255);

    public Color forestPallete1 = new Color(80, 120, 40);
    public Color forestPallete2 = new Color(100, 240, 50);


    public String optionsText =
            " _____           __                                  \n" +
                    "/\\  __`\\        /\\ \\__  __                           \n" +
                    "\\ \\ \\/\\ \\  _____\\ \\ ,_\\/\\_\\    ___     ___     ____  \n" +
                    " \\ \\ \\ \\ \\/\\ '__`\\ \\ \\/\\/\\ \\  / __`\\ /' _ `\\  /',__\\ \n" +
                    "  \\ \\ \\_\\ \\ \\ \\L\\ \\ \\ \\_\\ \\ \\/\\ \\L\\ \\/\\ \\/\\ \\/\\__, `\\\n" +
                    "   \\ \\_____\\ \\ ,__/\\ \\__\\\\ \\_\\ \\____/\\ \\_\\ \\_\\/\\____/\n" +
                    "    \\/_____/\\ \\ \\/  \\/__/ \\/_/\\/___/  \\/_/\\/_/\\/___/ \n" +
                    "             \\ \\_\\                                   \n" +
                    "              \\/_/                                   ";

    public String mainMenu =
            "|                                            |\n" +
            "| +-- +-+ | | +-- +-+ +-- +-- +-+ | |        |\n" +
            "| |   | | | | |   | | |   |   | | \\ /        |\n" +
            "| +-+ | | | | |   +-+ |   +-- +-+  |         |\n" +
            "|   | | | | | |   |\\  |   |   |\\   |         |\n" +
            "| --+ +-+ +-+ +-- | \\ +-- +-- | \\  |         |\n" +
            "|                                            |\n" +
            "|  -+- +-- | | -+-                           |\n" +
            "|   |  |   \\ /  |         Introduction       |\n" +
            "|   |  +--  |   |         New Game           |\n" +
            "|   |  |   / \\  |         Multiplayer        |\n" +
            "|   |  +-- | |  |         Load Game          |\n" +
            "|                         Quit               |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n";

    public String multiplayerMenu =
            "@                                            @\n" +
            "|                                            |\n" +
            "|   M U L T I P L A Y E R     ( B E T A )    |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                   β     New Game as server |\n" +
            "|                   β     Connect to server  |\n" +
            "|                         Abort              |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                 *   *   *                  |\n" +
            "|   Note: Multiplayer does not work over the |\n" +
            "|     Internet.                              |\n" +
            "|                                            |\n" +
            "|   Multiplayer can only be used over LAN    |\n" +
            "|                                            |\n" +
            "|   Be sure you enter in the ip address of   |\n" +
            "|     the computer you are connecting to.    |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "@                                            @\n";

    public String pausedText =
            " ____                                     __     \n" +
                    "/\\  _`\\                                  /\\ \\    \n" +
                    "\\ \\ \\L\\ \\ __     __  __    ____     __   \\_\\ \\   \n" +
                    " \\ \\ ,__/'__`\\  /\\ \\/\\ \\  /',__\\  /'__`\\ /'_` \\  \n" +
                    "  \\ \\ \\/\\ \\L\\.\\_\\ \\ \\_\\ \\/\\__, `\\/\\  __//\\ \\L\\ \\ \n" +
                    "   \\ \\_\\ \\__/.\\_\\\\ \\____/\\/\\____/\\ \\____\\ \\___,_\\\n" +
                    "    \\/_/\\/__/\\/_/ \\/___/  \\/___/  \\/____/\\/__,_ /";


    public String tutForest =

            "             0---------------------------------------0                                                                                  \n" +
            "             |                                       |                                                                                  \n" +
            "             |                                       |                                                                                  \n" +
            "             |                  |                    |                                                                                  \n" +
            "             |                  |                    |                                                                                  \n" +
            "    0--------0                  |                    |                                                                                  \n" +
            "    |                           |                    |                                                                                  \n" +
            "    | 0------0                  |                    |       0-------0                                                                  \n" +
            "    | |      |                  |                    0-------0       0--0                                                               \n" +
            "0---0 |      |                                                          |                                                        0----  \n" +
            "|     |      |                                                          |                                                       /   ....\n" +
            "|   0-0      0-------------------------------------------------------0  |                                                      /  0---  \n" +
            "|   |                                                                |  |                                                     /  /      \n" +
            "|   0-0                                                              |  |                                                    /  /       \n" +
            "|     |                                                              |  |          0--------0                               /  /        \n" +
            "0---0 |                                                              |  |          |        |                              /  /         \n" +
            "    | |                                                              |  0----------0        0-----------------------------0  /          \n" +
            "    | |                                                              |                                                      /           \n" +
            "    | |                                                              0-----------------------------------------------------0            \n" +
            "    | |                                                                                                                                 \n" +
            "    | |                                                                                                                                 \n" +
            "    | 0---0---0---0---0---0---0---0---0---0---0---0                                                                                     \n" +
            "    |                                             |                                                                                     \n" +
            "    | 0-----------------------------------------0 |                                                                                     \n" +
            "    | |                                         | |                                                                                     \n" +
            "    | |                                         | |                                                                                     \n" +
            "    | |                                         | |                                                                                     \n" +
            "    | |                                         | |                                                                                     \n" +
            "  0-0 0--------------0                          | |               0-------------------------0                                           \n" +
            "  |                  |                          | |               |      #%##%#   #%#####%##|                                           \n" +
            "  |                  |                          | |               |  #%   ###%#%   %#%######|                                           \n" +
            "  |                  |                          | 0---------------0 %%##    %##%,  '####%###|                                           \n" +
            "  |                  |                          |                   ###%$,        ,$$    ##%|                                           \n" +
            "  |                  |                          0-----------------0  %#      $###%$'  $# %##|                                           \n" +
            "  0------------------0                                            |     #%#   %#     $#   ##|                                           \n" +
            "                                                                   |    #$##%      ,#%#    %#|                                           \n" +
            "                                                                  0-------------------------0                                           \n";

    public String tutWaterPool =
            "W      \n" +
            "WWW    \n" +
            "WWWWWW \n";
    public String sourcePit =
            "                                                                                                                                                             \n" +
            "                                                                                                                                                             \n" +
            "                                                                                                                                                             \n" +
            "                                                                                                                                                             \n" +
            "                                            :^^::^:                :::^::^:   ,:                                                                             \n" +
            "                                         ^::       ^:^::    ;:^:::^        ^::  :::                                                                          \n" +
            "                                        :               ^::^                       ^:^::                                                                     \n" +
            "                                       ^                                                :^:^::^           #######                                            \n" +
            "                                      :                                                        ^:::^:     #######                                            \n" +
            "                                      :                                                              :^::^###X###:^:                                         \n" +
            "                                      ^                         ,...................                                ^::                                      \n" +
            "                                       :                 ,.....' |        /        /'......                            ^:                                    \n" +
            "                                        ^             ,.,     /   \\       |       |      |'..D                           ^                                    \n" +
            "                                        :            ,                     \\       |         ':                         :                                    \n" +
            "                                         :          :      /           |    |        |     |   :                         ^                                   \n" +
            "                                         :         ,      |                                     :                        :                                   \n" +
            "                                         ^         ;                |                 |      |  :                        :                                   \n" +
            "                                          :       ,    |                                         :                       ^                                   \n" +
            "                                          ^       :    |                                         :                       :                                   \n" +
            "                                          :       :                                              :                        ^                                  \n" +
            "                                          :       :                                              :                        :                                  \n" +
            "                                           ^      :                                              :                        ^                                  \n" +
            "                                           :      :                                              :                        :                                  \n" +
            "                                           :      :                                              ;                        :                                  \n" +
            "                                           ^       '                                            '                        ^                                   \n" +
            "                                           :       :                                            '                        :                                   \n" +
            "                                            ^       :                                          '                        ^                                    \n" +
            "                                             :       :                                        '                         :                                    \n" +
            "                                              ^:      '..                                  ,.'                         ^                                     \n" +
            "                                                ^        '......                    ,.....'                          ^:                                      \n" +
            "                                                 :              '..................'                              ::^                                        \n" +
            "                                                  ^::                                                            ^                                           \n" +
            "                                                     ^:^                                                      :^:                                            \n" +
            "                                                        :^   ::^^:::^:                                    :::^                                               \n" +
            "                                                         :    ^       ^:^:::^                         :^:^                                                   \n" +
            "                                                          :   :              :^::^::        :::^:^:::^                                                       \n" +
            "                                                          :   :                     ^:::^::^                                                                 \n" +
            "                                                         :    ^                                                                                              \n" +
            "                                                         ^   :                                                                                               \n" +
            "                                                        :    :                                                                                               \n" +
            "                                                        :   ^                                                                                                \n" +
            "                                                       ^   :                                                                                                 \n" +
            "                                                       :   :                                                                                                 \n" +
            "                                                      :    ^                                                                                                 \n";


    public String textBox =
            "#-------------------------------------------#\n" +
            "|                                           |\n" +
            "|                                           |\n" +
            "|                                           |\n" +
            "#-------------------------------------------#\n";

    public String textBoxHelpful =
            "#-------------------------------------------#\n" +
            "|                                           |\n" +
            "|                                           |\n" +
            "|                                           |\n" +
            "#-ENTER-to-continue-------------------------#\n";

    public String textBoxQuestion =
            "#-------------------------------------------#\n" +
            "|                                           |\n" +
            "|                                           |\n" +
            "#-------------------------------------------#\n" +
            "   > NO      YES                             \n";


    public String circle1 =
                    "                      ╔══════════════╗\n" +
                    "                 ╔════╝              ╚════╗\n" +
                    "             ╔═══╝                        ╚═══╗\n" +
                    "           ╔═╝                                ╚═╗\n" +
                    "         ╔═╝                                    ╚═╗\n" +
                    "       ╔═╝                                        ╚═╗\n" +
                    "     ╔═╝                                            ╚═╗\n" +
                    "    ╔╝                                                ╚╗\n" +
                    "   ╔╝                                                  ╚╗\n" +
                    "  ╔╝                                                    ╚╗\n" +
                    " ╔╝                                                      ╚╗\n" +
                    " ║                                                        ║\n" +
                    "╔╝                                                        ╚╗\n" +
                    "║                                                          ║\n" +
                    "║                                                          ║\n" +
                    "║                                                          ║\n" +
                    "║                                                          ║\n" +
                    "╚╗                                                        ╔╝\n" +
                    " ║                                                        ║\n" +
                    " ╚╗                                                      ╔╝\n" +
                    "  ╚╗                                                    ╔╝\n" +
                    "   ╚╗                                                  ╔╝\n" +
                    "    ╚╗                                                ╔╝\n" +
                    "     ╚═╗                                            ╔═╝\n" +
                    "       ╚═╗                                        ╔═╝\n" +
                    "         ╚═╗                                    ╔═╝\n" +
                    "           ╚═╗                                ╔═╝\n" +
                    "             ╚═══╗                        ╔═══╝\n" +
                    "                 ╚════╗              ╔════╝\n" +
                    "                      ╚══════════════╝\n";

    public String smallRoom =
                    "╔═══════════╗                             ╔═══════════╗ \n"+
                    "║           ╚═════════════════════════════╝         ..║ \n"+
                    "║           |     |     |     |     |     |         ... \n"+
                    "║           ╔═════════════════════════════╗         ..║ \n"+
                    "╚═══════════╝                             ╚═══════════╝ \n";

    public String smallRoome =
                    "╔═══════════╗                             ╔═══════════╗ \n"+
                    "║           ╚═════════════════════════════╝         ..║ \n"+
                    "║           |     |     |     |     |     |         ... \n"+
                    "║           ╔═════════════════════════════╗         ..║ \n"+
                    "╚═══════════╝                             ╚═══════════╝ \n";

    public String largerRoom =
                    "╔════╝   ╚════╗\n" +
                    "║>        .  L║\n" +
                    "╝     .  .    ╚\n" +
                    "     -    -    \n" +
                    "╗     .  .    ╔\n" +
                    "║\\        <> ║\n" +
                    "╚════╗   ╔════╝\n" ;


    public String smallerRoom =
                    "     ║   ║     \n" +
                    " ╔═══╝   ╚═══╗ \n" +
                    "═╝           ╚═\n" +
                    "               \n" +
                    "═╗           ╔═\n" +
                    " ╚═══╗   ╔═══╝ \n" +
                    "     ║   ║     \n";

    public String horizCorridor =// 3 high, 30 wide
                    "══════════════════════════════\n"+
                    "  |    |    |    |    |    |  \n"+
                    "══════════════════════════════\n";

    public String horizCorridorBlockedClean =// 3 high, 30 wide
                    "═════════╗          ╔═════════\n"+
                    "  |    | ║          ║ |    |  \n"+
                    "═════════╝          ╚═════════\n";

    public String horizCorridorBlockedDebris = // 3 high, 30 wide
                    "═══════════#.#══##═#══════════\n"+
                    "  |    | .#### . |.   |    |  \n"+
                    "════════^═══## #═^════════════\n";

    public String vertCorridor =// 10 high, 5 wide
                            "║ _ ║\n"+
                            "║   ║\n"+
                            "║ _ ║\n"+
                            "║   ║\n"+
                            "║ _ ║\n"+
                            "║   ║\n"+
                            "║ _ ║\n"+
                            "║   ║\n"+
                            "║ _ ║\n"+
                            "║   ║\n";

    public String vertCorridorBlockedClean = // 10 high, 5 wide
                    "║ _ ║\n"+
                    "║   ║\n"+
                    "║ _ ║\n"+
                    "╚═══╝\n"+
                    "     \n"+
                    "╔═══╗\n"+
                    "║ _ ║\n"+
                    "║   ║\n"+
                    "║ _ ║\n"+
                    "║   ║\n";


    public String intro1 =
            "######################    ####################\n" +
            "######################   @####################\n" +
            "######################    ####################\n" +
            "######################    ####################\n" +
            "#################  ###    #######  ###########\n" +
            "             @                                \n" +
            "                                              \n" +
            "                                        @     \n" +
            "##########    #########################    ###\n" +
            "##########    #########################    ###\n" +
            "##########    #########################    ###\n" +
            "##########    #########################    ###\n" +
            "##########    #########################    ###\n" +
            "##########@   #########################    ###\n" +
            "##########    #########################    ###\n" +
            "##########    #########################    ###\n" +
            "#####  ###    #####  ###########  #####    #  \n" +
            "                          #                 @ \n" +
            "                                              \n" +
            "                                              \n" +
            "##################            @  #############\n" +
            "##################               #############\n" +
            "##################               #############\n" +
            "##################               #############\n" +
            "##################               #############\n" +
            "##################@              #############\n" +
            "##################               #############\n" +
            "#######   ########               #######  ####\n" +
            "                                           @  \n" +
            "                                              \n" +
            "           @                                  \n";


    public String intro2 =
            "public class World extends MagicPlanet{        \n" +
            "   ArrayList<Stuff> things = new ArrayList<>();\n" +
            "                                               \n" +
            "   public World(God god, int x, int y, int z){ \n" +
            "      benev = god.getBenevolence();            \n" +
            "      summonAllThings(benev, MAGICAL);         \n" +
            "      super.xPos = x;                          \n" +
            "      super.yPos = y;                          \n" +
            "      super.zPos = z;                          \n" +
            "      super.velocity = null;                   \n" +
            "      super.spin = null;                       \n" +
            "      init();                                  \n" +
            "   }                                           \n" +
            "                                               \n" +
            "   public void orbit(Star sun, velMultiplier){ \n" +
            "      setOrbit(star, velMultip, false);        \n" +
            "   }                                           \n" +
            "                                               \n" +
            "   boolean useAllCharacters = false;           \n" +
            "                                               \n" +
            "   public void getUsage(){                     \n" +
            "      return useAllCharacters;                 \n" +
            "   }                                           \n" +
            "                                               \n" +
            "   /*                                          \n" +
            "   WARNING: setting useAllCharacters to        \n" +
            "     true will enable a very buggy build of    \n" +
            "     a world, and really shouldn't be used.    \n" +
            "     I'll be fixing that on Thursday.          \n" +
            "   */                                          \n" +
            "                                               \n" +
            "   public void generateWorldMagic(){           \n" +
            "      for(int ii = 0; ii < 9001; ii++){        \n" +
            "         int x = Math.random() * 100:          \n" +
            "         int y = Math.random() * 100;          \n" +
            "         placeNodeOnSurface(x, y);             \n" +
            "      }                                        \n" +
            "      activateAllMagicNodes()                  \n" +
            "   }                                           \n" +
            "                                               \n" +
            "   //Gravity Calculations                      \n" +
            "   static final float G = -.01; // Remember to \n" +
            "                            // tie things down!\n" +
            "                                               \n";



    public String sideMtns =
            "                      _                                                         \n" +
                    "                     /#\\                                                        \n" +
                    "                    /###\\     /\\                                                \n" +
                    "                   /  ###\\   /##\\  /\\                                           \n" +
                    "                  /      #\\ /####\\/##\\                                          \n" +
                    "                 /  /      /   # /  ##\\             _       /\\                  \n" +
                    "               // //  /\\  /    _/  /  #\\ _         /#\\    _/##\\    /\\           \n" +
                    "              // /   /  \\     /   /    #\\ \\      _/###\\_ /   ##\\__/ _\\          \n" +
                    "             /  \\   / .. \\   / /   _   { \\ \\   _/       / //    /    \\\\         \n" +
                    "     /\\     /    /\\  ...  \\_/   / / \\   } \\ | /  /\\  \\ /  _    /  /    \\ /\\     \n" +
                    "  _ /  \\  /// / .\\  ..%:.  /... /\\ . \\ {:  \\\\   /. \\     / \\  /   ___   /  \\    \n" +
                    " /.\\ .\\.\\// \\/... \\.::::..... _/..\\ ..\\:|:. .  / .. \\\\  /.. \\    /...\\ /  \\ \\   \n" +
                    "/...\\.../..:.\\. ..:::::::..:..... . ...\\{:... / %... \\\\/..%. \\  /./:..\\__   \\   \n" +
                    " .:..\\:..:::....:::;;;;;;::::::::.:::::.\\}.....::%.:. \\ .:::. \\/.%:::.:..\\      \n" +
                    "::::...:::;;:::::;;;;;;;;;;;;;;:::::;;::{:::::::;;;:..  .:;:... ::;;::::..      \n" +
                    ";;;;:::;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;];;;;;;;;;;::::::;;;;:.::;;;;;;;;:..   \n" +
                    ";;;;;;;;;;;;;;ii;;;;;;;;;;;;;;;;;;;;;;;;[;;;;;;;;;;;;;;;;;;;;;;:;;;;;;;;;;;;;   \n" +
                    ";;;;;;;;;;;;;;;;;;;iiiiiiii;;;;;;;;;;;;;;};;ii;;iiii;;;;i;;;;;;;;;;;;;;;ii;;;   \n" +
                    "iiii;;;iiiiiiiiiiIIIIIIIIIIIiiiiiIiiiiii{iiIIiiiiiiiiiiiiiiii;;;;;iiiilliiiii   \n" +
                    "IIIiiIIllllllIIlllIIIIlllIIIlIiiIIIIIIIIIIIIlIIIIIllIIIIIIIIiiiiiiiillIIIllII   \n" +
                    "IIIiiilIIIIIIIllTIIIIllIIlIlIIITTTTlIlIlIIIlIITTTTTTTIIIIlIIllIlIlllIIIIIIITT   \n" +
                    "IIIIilIIIIITTTTTTTIIIIIIIIIIIIITTTTTIIIIIIIIITTTTTTTTTTIIIIIIIIIlIIIIIIIITTTT   \n" +
                    "IIIIIIIIITTTTTTTTTTTTTIIIIIIIITTTTTTTTIIIIIITTTTTTTTTTTTTTIIIIIIIIIIIIIITTTTT   ";

    public String houseByMountansSide =
            "                                   /\\                                   \n" +
                    "                              /\\  //\\\\                              \n" +
                    "                       /\\    //\\\\///\\\\\\        /\\                   \n" +
                    "                      //\\\\  ///\\////\\\\\\\\  /\\  //\\\\                  \n" +
                    "         /\\          /  ^ \\/^ ^/^  ^  ^ \\/^ \\/  ^ \\                 \n" +
                    "        / ^\\    /\\  / ^   /  ^/ ^ ^ ^   ^\\ ^/  ^^  \\                \n" +
                    "       /^   \\  / ^\\/ ^ ^   ^ / ^  ^    ^  \\/ ^   ^  \\       *       \n" +
                    "      /  ^ ^ \\/^  ^\\ ^ ^ ^   ^  ^   ^   ____  ^   ^  \\     /|\\      \n" +
                    "     / ^ ^  ^ \\ ^  _\\___________________|  |_____^ ^  \\   /||o\\     \n" +
                    "    / ^^  ^ ^ ^\\  /______________________________\\ ^ ^ \\ /|o|||\\    \n" +
                    "   /  ^  ^^ ^ ^  /________________________________\\  ^  /|||||o|\\   \n" +
                    "  /^ ^  ^ ^^  ^    ||___|___||||||||||||___|__|||      /||o||||||\\  \n" +
                    " / ^   ^   ^    ^  ||___|___||||||||||||___|__|||          | |      \n" +
                    "/ ^ ^ ^  ^  ^  ^   ||||||||||||||||||||||||||||||oooooooooo| |oooooo\n" +
                    "ooooooooooooooooooooooooooooooooooooooooooooooooooooooooo           ";

    public String topMenu =
                    "+MENU-------------+\n" +
                    "|  Spells         |\n" +
                    "|  Items          |\n" +
                    "|  Equipment      |\n" +
                    "|  SAVE / QUIT    |\n" +
                    "|  EXIT MENU      |\n" +
                    "+-----------------+\n" +
                    "    A: Confirm     \n";

    public String quitMenu =
                    "+-SAVE / QUIT-----+\n" +
                    "|  BACK TO MENU   |\n" +
                    "|  SAVE GAME      |\n" +
                    "|  QUIT + SAVE    |\n" +
                    "|  QUIT W/O SAVE  |\n" +
                    "|                 |\n" +
                    "+-----------------+\n" +
                    "    A: Confirm     \n";

    public String taterMenu =
                    "+-MAGIC POTATO----+\n" +
                    "| UPGRADE?        |\n" +
                    "|                 |\n" +
                    "|  +5 Max Health  |\n" +
                    "|  +5 Max Mana    |\n" +
                    "|  BACK           |\n" +
                    "|                 |\n" +
                    "+-----------------+\n" +
                    "    A: Confirm     \n";

    public String ipSetMenu =
            "  Type IP: _____________\n" +
            "  <Clear IP>            \n" +
            "  Connect (Play!)       \n" +
            "  Back                  \n";


    public String itemsMenu =
                    "+Info-----------------------+ +Items-----( / )\n" +
                    "|                           | 1               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 2               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 3               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 4               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 5               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 6               \n" +
                    "+---------------------------+  ,,,,,,,,,,,,,,,\n" +
                    "                              7               \n" +
                    "                               ,,,,,,,,,,,,,,,\n" +
                    "                              8               \n" +
                    "                               ,,,,,,,,,,,,,,,\n" +
                    "                              9               \n" +
                    "                                 NEXT PAGE... \n" +
                    "                              0  EXIT         \n" +
                    "                              +---------------\n" +
                    "                                              \n" +
                    "                                              \n" +
                    "                                              \n" +
                    "                                              \n" +
                    "                                              \n" +
                    "                                              \n";

    public String spellsMenu =
                    "#Info=======================# #Spells====( / )\n" +
                    "|                           | 1               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 2               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 3               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 4               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 5               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 6               \n" +
                    "#===========================#  ,,,,,,,,,,,,,,,\n" +
                    "                              7               \n" +
                    "            #Equippped======#  ,,,,,,,,,,,,,,,\n" +
                    "            |S:             | 8               \n" +
                    "            |D:             |  ,,,,,,,,,,,,,,,\n" +
                    "            #===============# 9               \n" +
                    "                                 NEXT PAGE... \n" +
                    "                              0  EXIT         \n" +
                    "                              #===============\n" +
                    "                                              \n" +
                    "                                              \n" +
                    "                                              \n" +
                    "                                              \n" +
                    "                                              \n" +
                    "                                              \n";

    public String equipMenu =
                    "@Info~~~~~~~~~~~~~~~~~~~~~~~@ @Equipment~( / )\n" +
                    "|                           | 1               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 2               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 3               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 4               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 5               \n" +
                    "|                           |  ,,,,,,,,,,,,,,,\n" +
                    "|                           | 6               \n" +
                    "@~~~~~~~~~~~~~~~~~~~~~~~~~~~@  ,,,,,,,,,,,,,,,\n" +
                    "                              7               \n" +
                    "@Current Equipment~~~~~~~~~~@  ,,,,,,,,,,,,,,,\n" +
                    "|Weapon:                    | 8               \n" +
                    "|Armor :                    |  ,,,,,,,,,,,,,,,\n" +
                    "|     Defense:    MaxHP:    | 9               \n" +
                    "|  Attack Bonus:   All :    |    NEXT PAGE... \n" +
                    "|      Arcane:     Fire:    | 0  EXIT         \n" +
                    "|      Dark  :     Ice :    | @~~~~~~~~~~~~~~@\n" +
                    "@~~~~~~~~~~~~~~~~~~~~~~~~~~~@      A: Equip   \n" +
                    "                                              \n" +
                    "                                              \n" +
                    "                                              \n" +
                    "                                              \n" +
                    "                                              \n";



    public String mountainPlace =
            "          :    :                                                                                                         (mountain)    :  ::        \n" +
            "         :     :                                                                                                                        :   :       \n" +
            "        :     :                                                                        ::^:::    ::^::                                  :    ^::    \n" +
            "      ::^    ^                                                        ::::^::::::::^::^      :^::     :^::                               ::     ^   \n" +
            "   ::^       :^:                        ::^:::^:                    :^                                    :::^:::::^:                      ::   :   \n" +
            "  ^             ^                    ::^        ^                 ::                                  ,      ,   , ,o::^:        ::^::     :   :    \n" +
            "  :              ^:               ::^            ^:   :::^:   :::^       .............                          ,  , o000::^:::^:     ::^::   :     \n" +
            " :                 ^:          ::^        ..       ^:^     ::^         ..\\          / ......                      , oo000o0o,,                 :^:  \n" +
            " ^           ,       ^:      :^       ....  ...        ,          .....   \\         |       .......               , ,o00000o ,  ,                 ::\n" +
            "^  ,                   ^:^::^      ...   \\  /  ...................   \\     \\       /       /     /.         ,      , o0oo0oo,                     ::\n" +
            ":                                  .  \\   \\/  /      |    \\       \\   \\                   /       .            , ,  0oo000,                        :\n" +
            ":                      ,          .        \\  |                             |                   / .               , o000oo  ,                      :\n" +
            "^........                       ..                        |       / \\            /       /        .                , o0000,                        :\n" +
            "        /..   .....         ....  \\    \\   |  /     /                                          / / .              ,o 000o                     ,   : \n" +
            "        / /... \\  /.........  \\                           |     /     \\      \\   |     /       |    .               o 00,                          : \n" +
            "\\      /  /   \\ \\//   /  \\         |   |  \\ /                                   /               /    ..              o0                          :: \n" +
            "  \\   /  /     \\ /   |        |            /      /      /     |                      /                .                  ,         ,           :   \n" +
            "                |         |      \\        /                             \\                      |        .                                      :    \n" +
            "    |  /       /     |        |                                /                     |         /         .                                   ::     \n" +
            "                                                      /                                                 /.                              :   :       \n" +
            "     /       /            |   |    \\   /                                             |         |       / .                   :::::::::::     :      \n" +
            "                     |        .                              /                       |                   .:::::::    ,  ::::::          :   :       \n" +
            "   /        |                                                                                 /       /          :::::::        ,     ::   ::       \n" +
            "                     |        |                                                                                 /                   ::   ::         \n" +
            "  |        /                                               |                                                                      ::   ::           \n" +
            "                                                                                                                                ::   ::             \n" +
            "         /          /                                                                      /                                  ::   ::               \n" +
            "                   |                                      /                                                                  :    ::                \n" +
            "        |                                                                                                                     ::   ::               \n" +
            "                                                         |                               |                                      ::   ::             \n" +
            "                                                                                                                                  :    :   ##       \n" +
            "        |                                                                                |                                      ###     ########   #\n" +
            "                                                                                                      |                  ##  #######   #############\n";


    public String sourceCaves =
                                    "#############################################################  ###########################################################################################################################HHHHHHH###################################################################################################################################################################################################################################\n" +
                                    "############################################################1111##########################################################################################################################HHHH  H################################################################################################################################                                                                                                  #\n" +
                                    "###########################################################222222################### ######################### ###########################################################################H     H################################################################################################################################ ########################### ########## ###############################################           #\n" +
                                    "############################################################    ########################################################################################################################  H  H--H################################################################################################################################ ############## ############ ########## ###############################################           #\n" +
                                    "################ ##########################################    ################## #####################################################           #####################################         ################################################################################################################################# ############ #############   ########   ##############################################           #\n" +
                                    "################## #########################################   ############## ######################## #############################             ######################################       ##################################################################################################################################  ######## # ##############     ######     #############################################           #\n" +
                                    "##################### ##############################  #####  #  #########   #####    ###########################################     ###             #################################     ###################################################################################################################################   ######## ##################   ########   ##############################################           #\n" +
                                    "######################## #########################      ##  ###  #######  #   #   ## ########################################    ############           ###################      ##    ######################################################################################################################################  ########## ##############################################################################           #\n" +
                                    "######################### ####################     ####    #####  ###### ####   ####   ######### ###########################  ##################            ###########             ################################################################################################################# ############ #########  #########  ###############################################################################           #\n" +
                                    "########################### ######  ###  # ##   #################   ###  #############  ###################################  #####################              ##       ######   ########################################################################################################## ######   ########## #########   ########    #################################################################################       ###\n" +
                                    "############################  #####           #####################       ############# ### ############################### #######################                   ###########    ########################################################################################## ###### ####                  ######## ###  ########   ##      ###########  #################  ##############################################   #####\n" +
                                    "#############################        ##  ### #######################  ##   ############  ###    ##########################  ########################             ##################      ################################# ############################# #########   #      ###             ############# ##               ######   ########   ###########    ############   ################################################ ######\n" +
                                    "########################### ##  ### ### ############################   ###  ########## #            ##################     #########################           #######################         ##########################   #### #################                     ###      #### ###################### ##########  ##  ###   ########   ###############               ################################################## ######\n" +
                                    "######################### #    ## ###### ############################   ##  ######## ########              ###   #      #############################         ##########################                     ##########  #           #####           ####### # ######################################################  ####     ##########   ####################### #######################################################   #####\n" +
                                    "#########################    # ########### # ##########################    ######################    #              ###################################      #####################################                         ###### ##       #   ## ############################################################ #####  #####   ##############   ##################### #####################################################       ###\n" +
                                    "#######################    ###################################################### #######################       #########################################   ########################################                 ################### ##########################################################################  ####   #################  ##################### ###################################################           #\n" +
                                    "####################### ##################################################################################   ################################# ###########  #####################################################   ####################################### ###################################################### ###### ####################     ################  ###################################################           #\n" +
                                    "#####################    ###############################################################################   ###############################################  ####################################################  ###############################################################################################  ###    ########################                  ####################################################           #\n" +
                                    "####################     ############################################################################    #################################################  ###################################################  ############################################################################################### ####  #################################################################################################           #\n" +
                                    "###################   ##  #########################################################        ########    ###################################################   ################################################   ############################################################################################### ####  ##################################################################################################           #\n" +
                                    "##################  ###    ###########################################                        ####  #######################################################  #####################################      ####  ################################################################################################ ####  ###################################################################################################           #\n" +
                                    "################# ##### ###################################                                        ################################### ####################  ####################################  !  !  ##  ################################################################################################ ###   #####################################################################################################          #\n" +
                                    "################ ######    ##############################                                           #######################################################  ####################################  !  !  ## ############################################################################################### ####   #######################################################################################################         #\n" +
                                    "############### ######       ##########################                                              ############################# ########################   ####################################      ##  #################################################################################################     #########################################################################################################        #\n" +
                                    "############### ######           #####################                                                #####################################################   ####################################  #####  ############################################################################################# #      ############################################################################################################       #\n" +
                                    "############## ########              ##########                                                       ######################### #################### #######   ################################### #####  ############################################################################################      #################################################################################################################      #\n" +
                                    "############## ############                                                                          #########################################################  ################################   ####   ##################################### ####################################################        ############################################################################################################ #####     #\n" +
                                    "############# ##################                                                                    ######   ############### ######################  ##########  ############################## ####    ###########################################################################################      ###############################################################################################################  #####    #\n" +
                                    "####################################                                                             ########     ########### ##########################   ######### !############################# ###  ###########################################################################################       #################################################################################################################   #####   #\n" +
                                    "############# #########################                                                       ############   #########  ##############################  ########   ########################## ##    #########################################################################################        ###################################################################################################################    #####  #\n" +
                                    "##########################################                                               ###################  ###### ##################################   #######   ########################     #######################################################################################          ######################################################################################################################     ##### #\n" +
                                    "############################################                                        ###  #################### ##   ######################################    ###       ####################  ########################################### ##############################################       ##########################################################################################################################      #### #\n" +
                                    "##############################################                                  ########    ################    ########################################## #    ######   ################   #########################################################################################    ###############################################################################################################################       ### #\n" +
                                    "################################################                         #################     ###########   ############################################   ############    ###########   ###########################################################################################     ##############################################################################################################################        ## #\n" +
                                    "#################################################                   #########################   #####      #############################################  #################     ######  ###########################################################################################         ############################################################################################################################         # #\n" +
                                    "##################################################              ###############################       #################################################  ####################          #########################################################################################           #############################################################################################################################           #\n" +
                                    "###################################################           ########################################################################################  ########################          ######################################### ############################################        ################################################################################################################################           #\n" +
                                    "####################################################         ####################################################################################################################             ################################### ################################################     #################################################################################################################################           #\n" +
                                    "####################################################        ###################################################################################################################   ##             ################################################################################     ############################################################################################################################################ #\n" +
                                    "####################################################       ###################################################################################################################  ######                ######################## ###############################################         ########################################################################################################################################### #\n" +
                                    "####################################################      #################################################################################################################### #########                    #########################  #########################################         ######################################################################################################################################### #\n" +
                                    "####################################################     ###################################################################################################################   ###########                           ##### ########   ######################### ###################       ######################################################################################################################################## #\n" +
                                    "###################################################     ###################################################################################################################  ###############                               #######  ########### ############################# #########       #################################################################################################################################### #\n" +
                                    "###################################################    ####################################################################################################################  ##################                                    ##########  ############# ############## ###########     ###################################################################################################################################### #\n" +
                                    "##################################################    ###################################################################################################################   ######################                                      ##### ###########   ############## ##########    ######################################################################################################################################### #\n" +
                                    "#################################################    ##################################################################################################################   ###########################                                          #########  ############### #########     ########################################################################################################################################## #\n" +
                                    "#################################################    ################################################################################################################   #############################  ##                                                #############  ########     ############################################################################################################################################# #\n" +
                                    "##################################################   # ###################################################################################################             ############################## ######                                                  #####  # #########     ############################################################################################################################################# #\n" +
                                    "#################################################      #################################################################################################   ####  ####   ############################ ###########                                                     ########        ############################################################################################################################################# #\n" +
                                    "###############################################  ####    #############################################################################################   ######  ######   ########################################                                                                    ############################################################################################################################################ #\n" +
                                    "############################################    ########      #######   ############################################################################   ########  ########   ############################## #############                                                                    ###################################################################################################################################### #\n" +
                                    "###########################################  ##############    #      #    ##################               #######################################  ##########  ##########  ####################################################                                                                  ############################################################################################################################### #\n" +
                                    "###########################################   ################    ########      #########                         #################################  #########    #########  ####################################################  ########                                                                         ############################################################################################################## #\n" +
                                    "###########################################   #################################                                    ################################  ########  ##  ########  ######################### ########################   ################ ######                                                                              ########################################################################################### #\n" +
                                    "#############################################  ###################################                                  ###############################  ########  ##  ########  ##################################################################### ######  ###################################                                                              ###################################################################### #\n" +
                                    "#######################       ################  ######################################        ############        #################################  #########    #########  ##################################################################### #####  ###############################################################                                                          ############################################### #\n" +
                                    "####################            ###############   ############################################################ #####################################   ###################   #########################################################################  #############################################################################################                                                    ######################### #\n" +
                                    "###################              ###############  ############################################################   #############################     ##    ###############   ###########################################################################  #######################################################################################################################################                             ###### #\n" +
                                    "####################           ################   ##############################################################       ####################    ###    ##   ###########   ############################################################################ ###########################################################################################################################################################                  #\n" +
                                    "#######################      #################  ######################################################################   #######            ##############             ############################################################################# ############################################################################################################################################################################# #\n" +
                                    "##########################    ##        ####   #########################################################################          ################################################################################################################# ############################################################################################################################################################################## #\n" +
                                    "############################      ####       ############################################################################################################################################# ##################################################### ########################################################################################################################################################################          #\n" +
                                    "############################################################################################################################################################################################################################################### ####################################################################################################################################################################################\n";


    public String banditFortress =
                    "                                                                                                                                        \n" +
                    "                                                                                                                                        \n" +
                    "                                        :                     :                                                                         \n" +
                    "                                        :                     :                                                                         \n" +
                    "                                        :                     :                                                                         \n" +
                    "                                         :                   :                                                                          \n" +
                    "                                          ::::::       ::::::                                                                           \n" +
                    "                                                :     :                                 /   /                                           \n" +
                    "                                                :     :                                /---/                                            \n" +
                    "                                                :     :                               /---/                                             \n" +
                    "                                         :::::::::   :::::::::                       /---/                                              \n" +
                    "                                       ::                     ::                    /---/                                               \n" +
                    "                                      :                         :                  /---/                                                \n" +
                    "                                     :                           :                /---/                                                 \n" +
                    "                                    :           :::::::           :        ::::::/---/:::::                                             \n" +
                    "                                   :         :::       :::         :       :               :                                            \n" +
                    "                                  :        ::             ::        :::::::                :                                            \n" +
                    "                                  :       :                 :                              :                                            \n" +
                    "                                  :      :                   :      :::::::::::::::::::::::                                             \n" +
                    "                                  :      :                   :      :                                                                   \n" +
                    "                                  :      :                   :      :                                                                   \n" +
                    "                                  :       :                 :       :                                                                   \n" +
                    "                                  :        ::             ::        :                                                                   \n" +
                    "                                   :         :::       :::         :                                                                    \n" +
                    "                                    :           :::::::           :                                                                     \n" +
                    "                                     :             G             :                                                                      \n" +
                    "                                      :                        :::::::::::::::::::                                                      \n" +
                    "                                       ::::                                       :                                                     \n" +
                    "                                           :::::::::::::::::::::::::::::::::::    :                                                     \n" +
                    "                                                                              :   :                                                     \n" +
                    "                                                                       wwwww:::   :::wwwww                                              \n" +
                    "                                                                      w     w :   : w     w                                             \n" +
                    "                                                                      w                   w                                             \n" +
                    "                                                                      w     w:     :w     w                                             \n" +
                    "         wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww   wwwwwwwwwwwwwwwwww                                    \n" +
                    "        w                                                                     :   :                 w                                   \n" +
                    "       w                                                                      :   :                  w                                  \n" +
                    "      w      :::::::::::::::::::::::::::::::::                                :   :                   w                                 \n" +
                    "     w     ::                                 ::                              :   :                    w                                \n" +
                    "    w     :                                     :                             :   :                     w                               \n" +
                    "   w      :                                     :                             :   :                      w                              \n" +
                    "   w       ::                                 ::                              :   :                       w                             \n" +
                    "   w         :::::::::   :::::::::::::::::::::                                :   :                        w                            \n" +
                    "   w                  : :                                       ::::::::::::::    :                         w                           \n" +
                    "   w                  : :                                      :                  :                          w                          \n" +
                    "   w            ::::::   ::::::                                :                  :                           w                         \n" +
                    "   w           :               :                               :    ::::::::::::::                             w                        \n" +
                    "   w            ::::::   ::::::                                :   :                                            w                       \n" +
                    "   w                  : :                                      :   :                                             w                      \n" +
                    "   w                  : :                                      :   :                                              w                     \n" +
                    "   w          ::::::::   :::::::::::::::::::::::::::::::::::::::   :::::::::::::::::::::::                         w                    \n" +
                    "   w        ::                                                                            ::                        w                   \n" +
                    "   w       :                                                                                :                       w                   \n" +
                    "   w       :                                                mmmmmmmmmmm                     :                       w                   \n" +
                    "   w      :   mmmmmmmm      mmmmmmmm     mmmmmmmm           mmmmmmmmmmm          mm          :                      w                   \n" +
                    "   w      :   mmmmmmmm      mmmmmmmm     mmmmmmmm                              mmmmmm       m:                      w                   \n" +
                    "   w      :                                                                    mmmmmm     mmm:                      w                   \n" +
                    "   w      :                                                 mmmmmmmmmmm          mm       mmm:                      w                   \n" +
                    "   w      :     mmmmmmmm      mmmmmmmm      mmmmmmmm        mmmmmmmmmmm                     m:                      w                   \n" +
                    "   w      :     mmmmmmmm      mmmmmmmm      mmmmmmmm                           mm             ::::::::              w                   \n" +
                    "   w      :                                                                  mmmmmm                   ::::::::      w CCCC              \n" +
                    "   w      :                                                 mmmmmmmmmmm      mmmmmm     mm   ::::::::         ::::::wCC  CCCC           \n" +
                    "   w      :       mmmmmmmm      mmmmmmmm      mmmmmmmm      mmmmmmmmmmm        mm     mmmmmm:        ::::::::               CCCCCCCC    \n" +
                    "   w       :      mmmmmmmm      mmmmmmmm      mmmmmmmm                                mmmmmm:                :::::::w  CCC         CCCCC\n" +
                    "   w       :                                                                            mm  :                       wCC   CCCC          \n" +
                    "   w        ::                                                                            ::                        w         CCC  CCCCC\n" +
                    "   w          ::::::::::      ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::                          w           CCCC    \n" +
                    "   w                    :    :                                                                                     w                    \n" +
                    "   w                    :    :                                                                                    w                     \n" +
                    "    w                    :  :                                                                                    w                      \n" +
                    "     w                   :  :                                                                                   w                       \n" +
                    "      w                  :  :                                                                                  w                        \n" +
                    "       w                 :  :                                                                                 w                         \n" +
                    "        w                :  :    ::::::                                                                      w                          \n" +
                    "         w               :  :   :      :                                                                    w                           \n" +
                    "          w              :  ::::        :                                                                  w                            \n" +
                    "           w             :             :                                                                  w                             \n" +
                    "            w            :   ::::::::::                                                                  w                              \n" +
                    "             w            :   :::::::::::::::::::::::::::::                                             w                               \n" +
                    "              w            :                               :::                                         w                                \n" +
                    "               w            :                                 :::                                     w                                 \n" +
                    "                w            :                                   :::                                 w                                  \n" +
                    "                 w            :::::::::::::::::::::::::::           :::                             w                                   \n" +
                    "                  w                                      :::           ::                          w                                    \n" +
                    "                   w                                        ::           :                        w                                     \n" +
                    "                    w                                        :           :                       w                                      \n" +
                    "                     wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww   wwwwwwwwwwwwwwwwwwwwwwwwwwww                                       \n" +
                    "                                                        w     w::     ::w     w                                                         \n" +
                    "                                                        w                     w                                                         \n" +
                    "                                                        w     w:::   :::w     w                                                         \n" +
                    "                                                         wwwww   :   :   wwwww                                                          \n" +
                    "                                                                 :   :                                                                  \n" +
                    "                                                                 :   :                                                                  \n" +
                    "                                                             ::::     ::::                                                              \n" +
                    "                                                            :             :                                                             \n" +
                    "                                                           :               :                                                            \n" +
                    "                                                           :               :                                                            \n" +
                    "                                                            :             :                                                             \n" +
                    "                                                             :::       :::                                                              \n" +
                    "                                                                :     :                                                                 \n" +
                    "                                                                 :   :                                                                  \n" +
                    "                                                                 :   :                                                                 .\n" +
                    "                                                                 :   :                                                                  \n" +
                    "                                                                 :   :                                                                  \n" +
                    "                                                                 :   :                                                                  \n";

    public String innerMountains =
                    "                       :   :                                       \n" +
                    "                      ^    :                                       \n" +
                    "                    ::    ;                                        \n" +
                    "                   :     ^                                         \n" +
                    "                   :    ;                                          \n" +
                    "                  :   ::                                           \n" +
                    "                 ^   ;                                             \n" +
                    "                :   :                                              \n" +
                    "               :   ;                                               \n" +
                    "              :    ^                                               \n" +
                    "              :   ;                                                \n" +
                    "             :    :                                                \n" +
                    "             :   ;                                   ::R::::^R::^R:\n" +
                    "             ^   :::^:                         ::R::;              \n" +
                    "             :        ::                    :^;                    \n" +
                    "              :         ::^:::            :;            ;::R::R^RRR\n" +
                    "              :   .     . .  .::::^::    ;        ;^:R::           \n" +
                    "               ^       . .o..        :^:;     ;:::                 \n" +
                    "               :    . .ooOOOo. .       S    ;^                     \n" +
                    "                :    .oOOOOo...   ;:      ;:                       \n" +
                    "                : .  . ooo. .    ;  :     :                        \n" +
                    "                 :^ .  .. .    ;:    ^     ^                       \n" +
                    "                   :::.   . ;^:       :    :                       \n" +
                    "                      :::::;          :    :                       \n" +
                    "                                       :    :                      \n" +
                    "                                       :    :                      \n" +
                    "                                      ^      ^                     \n" +
                    "                                     ;        ::                   \n" +
                    "                                    ;        . .:                  \n" +
                    "                              ;::^:;           ..:                 \n" +
                    "                      ;::^:::;           .   . oo^                 \n" +
                    "                 ;::^;                       .oO;                  \n" +
                    "        ;:^:::::^            ;::::         .. oO:                  \n" +
                    "      ;:                ;::^;     :::^:  ...ooO;                   \n" +
                    "     :                ;:               :::::^::                    \n" +
                    "    :                ;                                             \n" +
                    "    ^              ;^                                              \n" +
                    "   ;              ;                                                \n" +
                    "   :    S       ;:                                                 \n" +
                    "   :          ;:                                                   \n" +
                    "    :        ;                                                     \n" +
                    "     :      ;                                                      \n" +
                    "      ^     :                                                      \n" +
                    "      :      ^                                                     \n" +
                    "       :     :                                                     \n" +
                    "       ^     :                                                     \n" +
                    "       :     :                                                     \n";


    public String snowyPeak =
            "                                                                                                                         @                                                                    00           \n" +
            "                                                                                                                      @                                                                      O  \\         \n"+
            "                                                                                                                       @                                                                    o    \\        \n"+
            "                                                                                                              ________|_|_                                                                  0     \\       \n"+
            "                                                                                                             /____________\\                                                                o      W       \n"+
            "                                                                  00O00o0O0O00o00o000                       /##############\\                                                     0  0     0       W       \n"+
            "                                                       0Oo00o00oO0(                  O00O00OO00oo00          ##### ########                                                       0 0    O       W|       \n"+
            "                                              00O0o0o00(     (     ((         WWWW                 0O0O00O00o              0o                                                     0  Oo0o        W|       \n"+
            "                  0O0O0    0O000       0O0Oo0o(        (     (     WWWWWWWWWWW|  |WWWWWWW                      #------------#00                                                  0               W|       \n"+
            "            0O0O00     0O00     0oO00O0        ((       ((  WWWWWWW|          |  |      |WWWWWWWWW                S            O000OOO00O00oo000                            0O0oO                W|       \n"+
            "        0O00                     (               (((     WWW|      |          |  |      |        |WWWWWWWWWW                                    0o0O00o00o0O00o00O00o00o0O00                   WW||       \n"+
            "     OO0                          (                 (((WW|  |      |WWWWW     |  |   WWW|        |         |WWWWWW                                                                       WWWWWW| ||       \n"+
            "   O0                             (                   W| |  |WW    |          |W |      |        |  W      |     |WWWWW                          WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW|     | ||       \n"+
            "O0O                                (              WWWW|| |  |      |          |  |      |  W WW  |         |     |    |WWWWWWWWW        WWWWWWWWW|                                       |WW   | |        \n"+
            "                                    ((        WWWW|   || |  |      |          |  |      |        |         |WW   |    |WW      |WWWWWWWW|        |    WWW              WWW               |     | |        \n"+
            "                                      WWWWWWWW|   |   || |  |      |                    |        |      W  |     |    |        |        |  WW    |               W                  W    |     | |        \n"+
            "                           WWWWWWWWWWW|       | W |   || |  |                                    |         |     |W   |     WW |     WW |        |          WW                WW         |     | |        \n"+
            "WWWWW             WWWWWWWWW|          |       |   |   || |                                                 |     |    |        |        |        |                                       |     |          \n"+
            "    |WWWWWWWWWWWWW|        |     W    |       |   |   ||                                                         |    |        |WWW     |        |                                       |                \n"+
            "    |             |  WWW   |          |       |   |   |                                                               |        |        |        |                                                        \n"+
            "    |             |        |          |  WWWW |   |                                                                            |        |                                                                 \n"+
            "    |  WW         |        |          |       |                                                                                                                                                           \n"+
            "    |             |        |          |                                                                                                                                                                   \n"+
            "    |         W   |        |                                                                                                                                                                              \n"+
            "    |             |                                                                                                                                                                                       \n";


    public String iceCave =
            "#############################################################################\n" +
            "#######################   ###################################################\n" +
            "####################^       #################################################\n" +
            "###################       ###################################################\n" +
            "####################^   #################^          ^########################\n" +
            "######################      ^^######       ########     #####################\n" +
            "#########################            ^################    ^##################\n" +
            "################################################        ^####################\n" +
            "#############################################        ########################\n" +
            "###########################################    ^#############################\n" +
            "##########################################   ################################\n" +
            "#######################################^^       ^############################\n" +
            "####################################^              ^  ^######################\n" +
            "############################### ^ ^                      ^     ^ ############\n" +
            "############################# ^                             ##    ###########\n" +
            "########################### ^                              #####^ ###########\n" +
            "###########################  ^                            ^ #####  ##########\n" +
            "########################## ^                                ####   ##########\n" +
            "#########################^                                  #####   #########\n" +
            "#########################^ ^                               ^####   ^#########\n" +
            "##########################                                  ####   ##########\n" +
            "##########################^                                ####^  ###########\n" +
            "########################### ^                             ^##     ###########\n" +
            "############################# ^                                ##############\n" +
            "##############################^                          ###  ^##############\n" +
            "################################^                    ^#####^  ###############\n" +
            "################################## ^           ^    ########   ##############\n" +
            "#####################################^ ^      #############     #############\n" +
            "########################################  ################ ^   ##############\n" +
            "#########################################  ############      ################\n" +
            "#########################################   #########  ^ ####################\n" +
            "##########################################  #######    ######################\n" +
            "#########################################   ^ ##     ########################\n" +
            "#######################################          ############################\n" +
            "######################################4444444################################\n" +
            "#######################################3333##################################\n" +
            "######################################222####################################\n" +
            "######################################11#####################################\n";

    public String hiddenBunker =
            "                                                                                                                              C   C                      \n" +
            "                                ############## ############## ##############          ###########################            CC   C                      \n" +
            "                                #888888888   ###            ###            #          #                      888#            C    CC                     \n" +
            "                                #888888             888            8888    #          #                   |    8#            C     C                     \n" +
            "                                #88888            8888$              888   #          #                   |88   #            CC    CC                    \n" +
            "                                #88888       ### 88888888   ###888         #          ##  ############%##########             C     CC                   \n" +
            "                                #8888       8# ############## ######  ######           #  #          # #                      CC     ###                 \n" +
            "                                #88        88#                     #  #        #########--#          # #                       CC      #                 \n" +
            "                                #8         88# ############## ######  ######   #          #          # #                        C#---# #                 \n" +
            "                                #         888# #888888      ###888       88#   #          #          # #                         #     #                 \n" +
            "                                #          88# #88888         %            #####          #          # #                         # #####                 \n" +
            "                                #          88# #8888        ###8                          #      ##### ########## ############## #%#########             \n" +
            "                                #          88# #8888888     # #8888     888#####          ########              ###8        888###    88888#             \n" +
            "                                #8          8# ############## ######  ######   #          %   %                  %                         #             \n" +
            "                                #8          8#                     #  #        #          #########################           8########### #             \n" +
            "                                #8  88       # ############## ######  ######   #          #                       #           8#         # #             \n" +
            "                                #88 888      ###            ###888        8#   #          #######                 #8           ########### ####          \n" +
            "                                #88  88                                  88#   #                #                 #888                        #          \n" +
            "                                #888                            88       88#   #                #                 #########8                  #          \n" +
            "                                #8888888     ###            ###88888    888#   ################ #                         #888                #          \n" +
            "                                ############## ######  ###### ##############                  # #                         #88888              #          \n" +
            "                                                    #  #                                      # #                         ######%######       #          \n" +
            "                                                    #  #                                      # #                              # #    #       #          \n" +
            "                     #######                        #  #                                      # ################################ ######8      #          \n" +
            "                     #  $  #                        #  #                                      #                     8888## 888## ##   $88     #          \n" +
            "                     #     #           ############ #  #                                      #                       88##   $##  %   888     #          \n" +
            "                     #     #           #          ###  #                                      # ###############         %     #####  888      #          \n" +
            "                     ### ###           #               #    ##############                    #%#             #88       ###############8      #          \n" +
            "    #########          #%#         #####   ###   #########  #88  88888888#                  ### ############# #88888    ###############       #          \n" +
            "    #       #     ###### ###########                     ####8     888888#                  #               ###  8888               ###       #          \n" +
            "    #   P   #######                                                  $888#                  #                %     888              ###       #          \n" +
            "    #                         ######                                 8888#                  #               ###   88888                       #          \n" +
            "    #  ########################    ##########################88     88888#                  ################# #   888888888         ###       #          \n" +
            "    #  #                                                    #8888  888888#                                    ####################### #########          \n" +
            "    #  #                                                    ##############                                                                               \n" +
            "    #  #                                                                                                                                                 \n" +
            "    #  #                                                                                                                                                 \n";

    public String hiddenBunkerWater =
            "          \n" +
            "       WWW\n" +
            "    WWWWWW\n" +
            " WWWWWWWW \n" +
            "WWWWWWW   \n" +
            "WWWW      \n" +
            "          \n";

    public String largeBoat =
                    "                      .^.                   \n" +
                    "                      | |                   \n" +
                    "                      | |                   \n" +
                    "                    _/   \\_                 \n" +
                    "                  _/       \\_               \n" +
                    "                _/           \\_             \n" +
                    "               /               \\            \n" +
                    "             _/... --------- ...\\_          \n" +
                    "            / ...             ... \\         \n" +
                    "           /                       \\        \n" +
                    "          /                         \\       \n" +
                    "         |                           |      \n" +
                    "        /                             \\     \n" +
                    "       /                               \\    \n" +
                    "       |                               |    \n" +
                    "      /                                 \\   \n" +
                    "      |                                 |   \n" +
                    "      |                                 |   \n" +
                    "      |                                 |   \n" +
                    "     /                                   \\  \n" +
                    "     |     _| |_             _| |_       |  \n" +
                    "     |                                   |  \n" +
                    "     |                                   |  \n" +
                    "    /__                                   \\ \n" +
                    "    |  |                                  | \n" +
                    "    /..|                                  \\ \n" +
                    "    |..|                          ==== ===| \n" +
                    "    |..|                         ||       | \n" +
                    "    |..|                         ||       | \n" +
                    "    |  |                         ||       | \n" +
                    "    |  |                         ||       | \n" +
                    "    |  |                          ==== ===| \n" +
                    "    |  |                          ==== ===| \n" +
                    "    |  |                         ||       | \n" +
                    "    |  |                         ||       | \n" +
                    "    |  |                         ||       | \n" +
                    "    |  |                         ||       | \n" +
                    "    |  |                          ========| \n" +
                    "    |  |                     -------------+ \n" +
                    "    |  /                    | \\|__|__|__|/| \n" +
                    "    |                       | () O o O O  | \n" +
                    "    |                       |O ()         | \n" +
                    "    |                       | O     ()    | \n" +
                    "    |                       |             | \n" +
                    "    |\\                      |             | \n" +
                    "    ||                      |             | \n" +
                    "    ||                      |    +---+    | \n" +
                    "    ||                      |    |   |    | \n" +
                    "    |/                      |             | \n" +
                    "    |                       |   /+   +\\   | \n" +
                    "    |========                -----   -----| \n" +
                    "    |       ||                            | \n" +
                    "    |                             ==== ===| \n" +
                    "    \\       ||                   ||       / \n" +
                    "     |      ||                   ||      |  \n" +
                    "     |=======                    ||      |  \n" +
                    "     |                            =======|  \n" +
                    "     \\                                  /   \n" +
                    "      |                                 |   \n" +
                    "      |                                 |   \n" +
                    "      |                                 |   \n" +
                    "      \\                                /    \n" +
                    "       \\                              /     \n" +
                    "        \\                            /      \n" +
                    "        |                           |       \n" +
                    "        \\                           /       \n" +
                    "         \\                         /        \n" +
                    "          \\                       /         \n" +
                    "           \\                     /          \n" +
                    "            \\...             .../           \n" +
                    "             \\... --------- .../            \n" +
                    "              \\               /             \n" +
                    "               \\_____________/              \n";

    public String dock =
        "                      ______________..\n" +
        "                      | .  .   .    ||\n" +
        "                      |       .     ||\n" +
        "                      |             ||\n" +
        "                      | . . . . . . ||\n" +
        "                      |             ||\n" +
        "                      |             ||\n" +
        "                      | . . . . . . ||\n" +
        "                      |             ||\n" +
        "                      |             ||\n" +
        "                      |  . . . . . .||\n" +
        "                      |             ||\n" +
        "                      |             ||\n" +
        "                      | . . . . . . ||\n" +
        "                      |             ||\n" +
        "  |-------------------|             ||\n" +
        "  | . .   . . . .   . . . . . . . . ||\n" +
        "  |                                 ||\n" +
        "  | .  .     .       .              ||\n" +
        "  |-------------------| . . . . . . ||\n" +
        "                      |             ||\n" +
        "                      |             ||\n" +
        "                      | . . . . . . ||\n" +
        "                      |             ||\n" +
        "                      |             ||\n" +
        "                      | . . . . . . ||\n" +
        "                      |             ||\n" +
        "  |-------------------|             ||\n" +
        "  |. . . . . . . . . . . . .  . . . ||\n" +
        "  |                                 ||\n" +
        "  |.  .   .   .    .                ||\n" +
        "  |-------------------| . . . . . . ||\n" +
        "                      |             ||\n" +
        "                      |             ||\n" +
        "                      | . . . . . . ||\n" +
        "                      |             ||\n" +
        "                      |             ||\n" +
        "                      | . . . . . . ||\n" +
        "                      |             ||\n" +
        "                      |             ||\n" +
        "                      | . . . . . . ||\n" +
        "                      |             ||\n" +
        "                      |             ||\n" +
        "                      | . . . . . . ||\n" +
        "                      |_____________||\n";


public String testRoom =
        "O-------------------------------O\n" +
        "|                               |\n" +
        "|                               |\n" +
        "|                               |\n" +
        "|                               |\n" +
        "|                           |   |\n" +
        "|                           |   |\n" +
        "|                           |   |\n" +
        "|                               |\n" +
        "|               ---------       |\n" +
        "|                               |\n" +
        "|                               |\n" +
        "|                               |\n" +
        "|                               |\n" +
        "|                               |\n" +
        "O-------------------------------O\n";

    /*Dry docks:
                                          .^.
                                          | |
                                          | |
                                        _/   \_
                                      _/       \_
                                    _/           \_
                                   /               \
                                 _/... --------- ...\_
                                / ...             ... \
                               /                       \
                              /                         \
                             |                           |
                            /                             \
                           /                               \
                           |                               |
                          /                                 \
                          |                                 |
                          |                                 |
                          |                                 |
                         /                                   \
                         |     _| |_             _| |_       |
                         |                                   |
                         |                                   |
                        /__                                   \
                        |  |                                  |
                        /..|                                  \
                        |..|                          ==== ===|
                        |..|                         ||       |
                        |..|                         ||       |
                        |  |                         ||       |
                        |  |                         ||       |
                        |  |                          ==== ===|
                        |  |                          ==== ===|
                        |  |                         ||       |
                        |  |                         ||       |
                        |  |                         ||       |
                        |  |                         ||       |
                        |  |                          ========|
                        |  |                     -------------+
                        |  /                    | \|__|__|__|/|
                        |                       |             |
                        |                       |             |
                        |                       |       ()    |
                        |                       |             |
                        |\                      |             |
                        ||                      |             |
                        ||                      |    +---+    |
                        ||                      |    |   |    |
                        |/                      |             |
                        |                       |   /+   +\   |
                        |========                -----   -----|
                        |       ||                            |
                        |                             ==== ===|
                        \       ||                   ||       /
                         |      ||                   ||      |
                         |=======                    ||      |
                         |                            =======|
                         \                                  /
                          |                                 |
                          |                                 |
                          |                                 |
                          \                                /
                           \    ---------:-------- -      /
                            \                            /
                            |     -------:-------- -    |
                            \                           /
                             \                         /
                              \                       /
                               \                     /
                                \...             .../
                                 \...-----------.../
                                  \               /
                                   \_____________/

                      ______________..
                      | .  .   .    ||
                      |       .     ||
                      |             ||
                      | . . . . . . ||
                      |             ||
                      |             ||
                      | . . . . . . ||
                      |             ||
                      |             ||
                      |  . . . . . .||
                      |             ||
                      |             ||
                      | . . . . . . ||
                      |             ||
  |-------------------|             ||
  | . .   . . . .   . . . . . . . . ||
  |                                 ||
  | .  .     .       .              ||
  |-------------------| . . . . . . ||
                      |             ||
                      |             ||
                      | . . . . . . ||
                      |             ||
                      |             ||
                      | . . . . . . ||
                      |             ||
  |-------------------|             ||
  |. . . . . . . . . . . . .  . . . ||
  |                                 ||
  |.  .   .   .    .                ||
  |-------------------| . . . . . . ||
                      |             ||
                      |             ||
                      | . . . . . . ||
                      |             ||
                      |             ||
                      | . . . . . . ||
                      |             ||
                      |             ||
                      | . . . . . . ||
                      |             ||
                      |             ||
                      | . . . . . . ||
                      |_____________||



     */

    private static int occurrancesOf(String input, String ofWhat) {
        return input.length() - input.replace(ofWhat, "").length();
    }
    public static String[][] strToArray(String input) {
        return strToArray(input, false);
    }
    /**
     * Create a String[height][width] (r,c?) array out of an inputted string that
     * is at least that big (specified by its dimensions with \n).
     *
     * @param input a string with '\n's
     * @param debug whether to print gunk to System.out
     * @return a corresponding String[][] where each element is just one character
     */
    public static String[][] strToArray(String input, boolean debug) {
        int width = input.indexOf("\n");
        int height = occurrancesOf(input, "\n");//input.length() / (height);
        if (debug) {
            System.out.println(height);
            System.out.println(width);
        }
        char[][] charresult = new char[height][width];
        input = "\n" + input; //So it works.
        for (int i = 0; i < height; i++) {
            input = input.substring(1 + input.indexOf("\n"));
            //System.out.println(input.substring(0, width));
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
}
