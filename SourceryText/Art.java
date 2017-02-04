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
package SourceryText;

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
    public Color mountainPallette3 = new Color(130, 130, 100);

    public Color mtnPeakPallette1 = new Color(180, 220, 255);
    public Color mtnPeakPallette2 = new Color(0, 180, 255);

    public Color wallBackground = new Color(60/2, 58/2, 56/2);
    public Color wallForeground = new Color(40*3, 38*3, 36*3);
    public SpecialText poundWall = new SpecialText("#", wallForeground, wallBackground);

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
            "|   |  |   / \\  |         Join LAN Game      |\n" +
            "|   |  +-- | |  |         Load Game          |\n" +
            "|                         Settings           |\n" +
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
            "|                                            |\n";

    public String settingsMenu =
            "@                                            @\n" +
            "|                                            |\n" +
            "|              S E T T I N G S               |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                   α     Controls           |\n" +
            "|                         Main Menu          |\n" +
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
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "|                                            |\n" +
            "@                                            @\n";

    public String controlsMenu =
            "@              C O N T R O L S               @\n" +
            "|   Profile                                  |\n" +
            "|                  Mapping   |   1   |   2   |\n" +
            "|   up                       |       |       |\n" +
            "|   left                     |       |       |\n" +
            "|   down                     |       |       |\n" +
            "|   right                    |       |       |\n" +
            "|   turn clockwise           |       |       |\n" +
            "|   turn anticlockwise       |       |       |\n" +
            "|   turn-type (toggle)       |       |       |\n" +
            "|   turn-type (hold)         |       |       |\n" +
            "|   toggle aim display       |       |       |\n" +
            "|   run                      |       |       |\n" +
            "|   spell 1                  |       |       |\n" +
            "|   spell 2                  |       |       |\n" +
            "|   interact                 |       |       |\n" +
            "|   menu                     |       |       |\n" +
            "|   continue/select          |       |       |\n" +
            "|   back/deselect            |       |       |\n" +
            "|                                            |\n" +
            "|   Settings                                 |\n" +
            "|                                            |\n" +
            "|  @--------------------------------------@  |\n" +
            "|  |                                      |  |\n" +
            "|  |                                      |  |\n" +
            "|  |                                      |  |\n" +
            "|  @--------------------------------------@  |\n" +
            "@                                            @\n";

    public String multiplayerMenu =
            "@                                            @\n" +
            "|                                            |\n" +
            "|        C O N N E C T   T O   L A N         |\n" +
            "|                                            |\n" +
            "|      M U L T I P L A Y E R   G A M E       |\n" +
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
            "                                                                  |    #$##%      ,#%#    %#|                                           \n" +
            "                                                                  0-------------------------0                                           \n";

    public String tutWaterPool =
            "W      \n" +
            "WWW    \n" +
            "WWWWWW \n";
    public String sourcePit =
            "                                                                                                                                                             \n" +
            "                                                                                                               .                                             \n" +
            "                                                                                                              o                                              \n" +
            "                                                                                                                 .                                           \n" +
            "                                           .:^^::^:.              .:::^::^:.  ,:.                              o                                             \n" +
            "                                        .^::'     '^:^::.  .;:^:::^'      '^::'':::.                          o                                              \n" +
            "                                       .:'             '^::^'                      ':^::.                 ____v__                                            \n" +
            "                                      .^'                                              ':^:^::^.         //_____\\\\                                            \n" +
            "                                      :'                                                      '^:::^:   /#########\\                                           \n" +
            "                                      :                                                             ':   ####.####                                           \n" +
            "                                      ^.                        ,....................                ':::'        ::^::.                                     \n" +
            "                                      ':.                ,.....' |      |           '......                           '^:.                                   \n" +
            "                                       '^              .:     /   \\      \\          /    |'..D                           ^.                                   \n" +
            "                                        :.           ,:/            \\              |      |  ':                           :                                  \n" +
            "                                        ':          :'/    /           \\                   \\  ':                         ^'                                  \n" +
            "                                         :         :'|    |           /            /        \\   :                        :                                   \n" +
            "                                         ^.        ; |                    \\                  |  :.                       :                                   \n" +
            "                                         ':       ,' \\  /           /             /     \\      /':                       ^                                   \n" +
            "                                          ^       :   \\     |       |      |            |      | :                       :.                                  \n" +
            "                                          :       :         \\     |  \\           |             | :                       '^                                  \n" +
            "                                          :.      :    \\          |                     \\      / :                        :                                  \n" +
            "                                          '^      :                             /        |    |  :                        ^                                  \n" +
            "                                           :      :     |        /              |        |    |  :                        :                                  \n" +
            "                                           :      :      \\      |                         \\   |  ;                       .:                                  \n" +
            "                                           ^      ''   |  |                     |       |  |    .'                       ^'                                  \n" +
            "                                           :.      :.     |    |     |         /       /   |  | '                       .:                                   \n" +
            "                                           '^.      :. |       |     |        /        |      |'                        ^'                                   \n" +
            "                                             :.      :.   |                   |        |     /'                        .:                                    \n" +
            "                                             '^:.     '..      /     |                 |   ,.'                        .^'                                    \n" +
            "                                               '^.       '......     |        |     ,.....'                         .^:'                                     \n" +
            "                                                ':.             '..................'                             .::^'                                       \n" +
            "                                                 '^::.                                                          .^'                                          \n" +
            "                                                    '^:^.                                                    .:^:'                                           \n" +
            "                                                       ':^   ::^^:::^:.                                  .:::^'                                              \n" +
            "                                                        ':    ^'     '^:^:::^.                       .:^:^'                                                  \n" +
            "                                                         ':   :             ':^::^::.      .:::^:^:::^'                                                      \n" +
            "                                                         .:   :                    '^:::^::^'                                                                \n" +
            "                                                         :    ^                                                                                              \n" +
            "                                                        .^   :'                                                                                              \n" +
            "                                                        :    :                                                                                               \n" +
            "                                                       .:   ^'                                                                                               \n" +
            "                                                       ^   :'                                                                                                \n" +
            "                                                      .:   :                                                                                                 \n" +
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
            "     It should've been fixed last Thursday...  \n" +
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
                    "|                 |\n" +
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
            "  Connect!              \n" +
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



    public String cliffSide =
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
            "                     |        .                              /                       |                 / .:::::::    ,  ::::::          :   :       \n" +
            "   /        |                                                                                 /                  :::::::      %       ::   ::       \n" +
            "                     |        |                                                                       |         /       %     |     ::   ::         \n" +
            "  |        /                                               |                                         /         |          %       ::   ::           \n" +
            "                                                                                                                           |    ::   ::             \n" +
            "         /          /                                                                      /           |      /           /   ::   ::               \n" +
            "                   |                                      /                                                              /   :    ::                \n" +
            "        |                                                                                              |     |    |           ::   ::               \n" +
            "                                                         |                               |                        /     |       ::   ::             \n" +
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
                    "                  ss           ss                                     ss                                                                \n" +
                    "         s        ssss       ssssss                                ssss     ss       s                                                  \n" +
                    "  ss      ss     ssssssssssssssssssss            :   :         sssssss     sss       ss                                                 \n" +
                    "   ss      sssssssssssrrrrrrrrrrrrrrr:::::::::::::   :::::ssssssssssssssssssss      sss                                                 \n" +
                    "    sss     ssssssrrrr                        ggg:   :ggggrrrrrrrrrrrssssssssssssssssss                                                 \n" +
                    "     ssssssssssrrrr                         ggggg:   :ggggggg       rrrrrrrrrrssssssssss s/---/                                         \n" +
                    "s    sssssssssrrr                        ggggggg:     :gggggggggg             rrrrrrsssss/---/             ss                           \n" +
                    "ss   sssssssrrr                       gggggggggg:     :gggggggggggggggggg          rrrss/---/s            ss                            \n" +
                    " sssssssssrrr                     gggggggggggggg:     :gggggggggggggggggggggggg      rr/---/ssss         ss              s              \n" +
                    "  ssssssrrr                 ggggggggggggggggggg::     ::ggggggggggggggggtttggggg      /---/ssssssss    ssss             ss              \n" +
                    "  sssssrr                ggggggggggggggggggggggg::   ::ggggggggggggggghttttttggg     /---/rrrsssssssssssss             ss               \n" +
                    " ssssrrr              gggggggggghtttgggggggg               ggggggggggghttttttgg     /---/   rrrrsssssssssssss        sss       s        \n" +
                    " sssrr               ggggggggggghhhhgggg                       gggggggghhhhggg     /---/       rrrrrrrssssssssssssssssss      ss        \n" +
                    " ssrr              ggggtttgggggggggggg                           ggggggggggg      /---/              rrrrrrssssssssssssss    ss         \n" +
                    "ssrr               gggghhhggggggggggg           :::::::           gggggggggg:::::/---/:::::               rrrsssssssssssssssss          \n" +
                    "ssrr               ggggggggggggggggg         :::1111111:::          gggggggg               :                 rrrrrsssssssssss           \n" +
                    "sssrr              ggggggggtttggggg       :::1112222222111:::                              :                    rrrssssssssssssss       \n" +
                    " ssrr               gggggghttttgggg      ::11222222222222211::                             :                      rrssssssssssssssssss  \n" +
                    " sssrr               gggggghhhggggg     ::1122222221222222211::      ggggggggg:::::::::::::                        rrsssssssss          \n" +
                    " ssssrr               ggggggggggggg     ::1122222211122222211::     gggggggggggg                                    rrssssssss          \n" +
                    "  sssrr                gggggggggggg     ::1122222221222222221::     gggggggttttgg                                   rrssssssssssss      \n" +
                    "  sssrr                 ggggggggggg      ::11222222222222211::      gggggghtttttggg                                 rrssssssssssssssss  \n" +
                    "  ssrr                  ggggggggggg       :::1112222222111:::       ggggggghhhhggggg                                rrssssssss       ss \n" +
                    "sssssrr                 gggggggggggg         :::1111111:::           ggggggggggggggg                                rrssssssssss        \n" +
                    "  sssrr                 ggggggggggggg           :::::::               gggggggggggggg                               rrsssssssssssss      \n" +
                    "   sssrr               ggggggggggggggg             G                    gggggggggggggg                             rrsssssss  ssssss    \n" +
                    "   sssrr              gggggggggtttgggggg                                   ggggggggggggg                           rrsssssss     ssss   \n" +
                    "  ssssrr              gggggggghttttggggggggg                                   gggggggggggg                        rrssssssssss    sss  \n" +
                    " ssssrr                 ggggggghhhggggggggggggg          gggggggggggggg          ggggggggggg                       rrsssssss sss     ss \n" +
                    "sssssrr                     ggggggggggggggggggggggggggggggggggggggggggggggg       gggggggggg                      rrrssssssss sss      s\n" +
                    "ssssrr                            gggggggggggggggggggggggggggggggggggggwwwww:::   :::wwwwwg                      rrrssssssssss   s      \n" +
                    "sssssrr                               gggggggggggggggggggggggggggggg  w     w :   : w     w                   rrrrsssssss sssss         \n" +
                    " sssssrrr                               ggggggggggggggggggggggggg     w                   w                 rrrssssssss      sss        \n" +
                    "  ssssssrr                               ggggggggggggggggggggggg      w     w:     :w     w               rrrssssssss          ss       \n" +
                    "  ssssssswwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww   wwwwwwwwwwwwwwwwww   rrrrsssss  sss           ss      \n" +
                    " sssssssw::#####################################:::::::::::::::::::::::::::::::   ::::::::::::::::::w rrrsssssss    sss          ss     \n" +
                    "s  ssssw::##:::::::::::::::::::::::::::::::::::##:::        :        :       ::   ::      ::        :wrrsssssss       ss           s    \n" +
                    "   sssw::##:::::::::::::::::::::::::::::::::::::##::        :        :       ::   ::                ::wrsss sss         s               \n" +
                    "   ssw::#::::                                 ::::#::  :::::::  :::::::  ::::::   ::      :::::::::::::wss   ss                         \n" +
                    "  ssw::#:::                                     :::#                                      ::        ::::w     ss                        \n" +
                    " ssw:::#:::                                     :::#:  :::::::  :::::::  ::::::   ::                :::::w     s                        \n" +
                    "s  w::::#::::                                 ::::#:        :        :       ::   ::      ::::::::::::::::w                             \n" +
                    "   w:::::##:::::::::::   :::::::::::::::::::::::##::        :        :       ::   ::      ::        :::::::w                            \n" +
                    "   w::::::##::::::::::: :::::::::::::::::::::::##:::::::::::::::::::::::::::::    ::                ::::::::w                           \n" +
                    "   w:::::::##########:: ::######################:::        :::::                  ::      :::::::::::::::::::w                          \n" +
                    "   w::::::::::::::::::   ::::::::::::::::::::::::::                               ::::::::::::::::::::::::::::w                         \n" +
                    "   w::::::::::::               :::               ::        :::::    :::::::::::::::::::::::::::::::::::::::::::w                        \n" +
                    "   w::::::::::::::::::   :::::::::               ::::::::  :::::   ::mmmmmmmm                     m:::::::::::::w                       \n" +
                    "   w::::::::::::::::::: :::::                                 ::   ::mm                mmmmmmmmm  m::::::::::::::w                      \n" +
                    "   w::::::::::::::::::: :::::    :               :            ::   ::mmmmmmmm     P               m:::::::::::::::w                      \n" +
                    "   w::::::::::::::::::   ::::  :::::::::::::::::::  :::::::::::     :::::::::::::::   :::::::::::::::::::::::::::::w                    \n" +
                    "   w::::::::::                                                                            ::::::::::::::::::::::::::w                   \n" +
                    "   w::::::::                                                                                ::::::::::::::::::::::::w                   \n" +
                    "   w::::::::                                                mmmmmmmmmmm                     ::::::::::::::::::::::::w                   \n" +
                    "   w::::::::  mmmmmmmm      mmmmmmmm     mmmmmmmm           mmmmmmmmmmm          mm          :::::::::::::::::::::::w                   \n" +
                    "   w########: mmmmmmmm      mmmmmmmm     mmmmmmmm                              mmmmmm       m:::::::::::::::::::::::w                   \n" +
                    "   w:::::::#:                                                                  mmmmmm     mmm:::::::::::::::::::::::w                   \n" +
                    "   w      :#:                                               mmmmmmmmmmm          mm       mmm:::::::::::::::::::::::w                   \n" +
                    "   w            mmmmmmmm      mmmmmmmm      mmmmmmmm        mmmmmmmmmmm                     m:::::::::::::::::::::::w                   \n" +
                    "   w      :#:   mmmmmmmm      mmmmmmmm      mmmmmmmm                           mm              :::::::::::::::::::::w                   \n" +
                    "   w      :#:                                                                mmmmmm                   ::::::::::::::w CCCC              \n" +
                    "   w      :#:                                               mmmmmmmmmmm      mmmmmm     mm   ::::::::         ::::::wCC  CCCC           \n" +
                    "   w      :##:    mmmmmmmm      mmmmmmmm      mmmmmmmm      mmmmmmmmmmm        mm     mmmmmm:::::::::::::::::               CCCCCCCC    \n" +
                    "   w       ::#:   mmmmmmmm      mmmmmmmm      mmmmmmmm                                mmmmmm::       :       ::  S::w  CCC         CCCCC\n" +
                    "   w       ::#:                                                                         mm  ::       :       :    ::wCC   CCCC          \n" +
                    "   w       ::#:                                                                            :::::::   ::::  :::    ::w         CCC  CCCCC\n" +
                    "   w       ::#:::::  :::      :::::::::::::::::::::::::::::::::::::::::::::::::::::::::   S::                     ::w           CCCC    \n" +
                    "   w       ::#::       ::    ::         :: b  : b  : b  : b  : b  : b  : b  : b  : b  :    ::                   :::w                    \n" +
                    "   w                   ::    ::         ::                                                  ::::::::::::::::::::::w                     \n" +
                    "    w      ::#::       ::   ::          ::::::::::::::::::::::::::::::::::::::::::::::::   ::::::::::::::::::::::w                      \n" +
                    "     w:::::::#::       ::               ::                                                  ::::::::::::::::::::w                       \n" +
                    "      w::::::#::       ::   ::          :: b  : b  : b  : b  : b  : b  : b  : b  : b  :     :::::::::::::::::::w                        \n" +
                    "       w:::::#::       ::   ::          ::::::::::::::::::::::::::::::::::::::::::::::::   :::::::::::::::::::w                         \n" +
                    "        w::::#::       ::   :::::::::::::: b  : b  : b  : b  : b  : b  : b  : b  : b  :     :::::::::::::::::w                          \n" +
                    "         w:::#::       ::   ::          ::                                                  ::::::::::::::::w                           \n" +
                    "          w::#::       ::   :::         ::::::::::::::::::::::::::::::::::::::::::::::::   ::::::::::::::::w                            \n" +
                    "           w:#::                        ::                                                  ::::::::::::::w                             \n" +
                    "            w#::::::::::::   ::         :: b  : b  : b  : b  : b  :    : b  : b  : b  : b   :::::::::::::w                              \n" +
                    "             w:::::::::::::   :::::::::::::::::::::::::::::::::::::    :::::::::::::::::::::::::::::::::w                               \n" +
                    "              w::::::::::#::                               ::::::::       ::                     ::::::w                                \n" +
                    "               w::::::::# #::                                 :::::                              :::::wg                                \n" +
                    "                w::::::#####::                                   :::::::::::                     ::::wgg                                \n" +
                    "                 w::::::::::::::::::::::::::::::::  :::::           ::::::::::  ::::::::::::::::::::wgg                                 \n" +
                    "                  oo::::::::::::::::::::::::::::         :::           ::                         oogg                                  \n" +
                    "                  gw::::::::::::::::::::::::::::         :::::           :                       :wgg                                   \n" +
                    "                  ggw:::o:::::::::::::::::o:::::::::::::::o:::           :::o::::::::::::::::o:::wgg                                    \n" +
                    "                   ggwwwowwwwwwwwwwwwwwwwwowwwwwwwwwwwwwwwowwwwwww   wwwwwwwowwwwwwwwwwwwwwwwowwwgg                                     \n" +
                    "                    ggggggggggggggggggggggggggggggggggggw     w::     ::w     wggggggggggggggggggg                                      \n" +
                    "                                                ggggggggw                     wggggggg                                                  \n" +
                    "                                                    ggggw     w:::   :::w     wggg                                                      \n" +
                    "                                                    gggggwwwww::::   ::::wwwwwgggg                                                      \n" +
                    "                                                     gggggggggggg:   :ggggggggggg                                                       \n" +
                    "                                                      gggggggggggg   ggggggggggg                                                        \n" +
                    "                                                         gggggggg     gggggggg                                                          \n" +
                    "                                                          ggg             ggg                                                           \n" +
                    "                                                         ggg               ggg                                                          \n" +
                    "                                                         ggg               ggg                                                          \n" +
                    "                                                         gggg             gggg                                                          \n" +
                    "                                                          gggggg       gggggg                                                           \n" +
                    "                                                           gggggg     gggggg                                                            \n" +
                    "                                                             ggggg   ggggg                                                              \n" +
                    "                                                              gggg   gggg                                                              .\n" +
                    "                                                              gggg   gggg                                                               \n" +
                    "                                                              gggg   gggg                                                               \n" +
                    "                                                             gggg     gggg                                                              \n";

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

    public String witchHut =
            "                          ##                      \n" +
            "                          ##                      \n" +
            "                          ##                      \n" +
            "                          ##                      \n" +
            "               #####    0####--mmm--mmm--mmm---0  \n" +
            "  0--mmmmmm---##SSS##   |####__________________|  \n" +
            "  |__________>#SSSSS#   |#ff#_____W_____W______|  \n" +
            "  |___________##SSS##   |______+~~~~~~~+_____0-0  \n" +
            "  |____W_______#####    |______:ccccccc:____W__|  \n" +
            "  |_______________0-----0__W___+~~~~~~~+_____|_|  \n" +
            "  |_________________________________W________|_|  \n" +
            "  0--0__0---------0-----0--------------------0-0  \n" +
            "     |__|                                         \n" +
            "     |__|                                         \n";

    public String witchHutCauldronUI =
            "o------------------------------o\n" +
            "|        - RECIPE -            |\n" +
            "|   1.                         |\n" +
            "|   2.                         |\n" +
            "|   3.                         |\n" +
            "|   4.                         |\n" +
            "|   5.                         |\n" +
            "|   6.                         |\n" +
            "|   BREW POTION                |\n" +
            "|   EXIT                       |\n" +
            "o~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~o\n" +
            "|                              |\n" +
            "o------------------------------o\n";

    public String hiddenBunkerWater =
            "          \n" +
            "       WWW\n" +
            "    WWWWWW\n" +
            " WWWWWWWW \n" +
            "WWWWWWW   \n" +
            "WWWW      \n" +
            "          \n";

    public String banditKingHall =
            "                                         ^::;:^:::^:                        \n" +
                    "                                     ;^:;           ^::                     \n" +
                    "                                    :8                 ^                    \n" +
                    "                                     ^:::^:             ;                   \n" +
                    "                                          ::^:          :                   \n" +
                    "                                              :^:^^    ;                    \n" +
                    "                                                  /---/                     \n" +
                    "                                                 /---/                      \n" +
                    "                                                /---/                       \n" +
                    "                                               /---/                        \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                                                            \n" +
                    "                                     _                                      \n" +
                    "                                    / |                                     \n" +
                    "                                  _/  |       ___                ::^:::::^::\n" +
                    "                     :^:::    ___/     \\_____/   \\_     ^::^:::::           \n" +
                    "                   ;:     ::^: .   .       .     . \\__ ;                    \n" +
                    "            ;:^::^;          :. .            . .  .ooo:                     \n" +
                    "       :^:^;                :0. .        .   . . .o0OO;                     \n" +
                    "     ;:                     ^Oo..  .        . .oo0O0O^                      \n" +
                    ":^::;                        :O0. .      .  ..oOO0O0;                       \n" +
                    "                              :Ooo...      ..oO;:^::                        \n" +
                    "                               ^:#### ####::^:;                             \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                                    # #                                     \n" +
                    "                         ############-############                          \n" +
                    "                         #                       #                          \n" +
                    "                         #                       #                          \n" +
                    "                         #   ###           ###   #                          \n" +
                    "                         #                       #                          \n" +
                    "                         #                       #                          \n" +
                    "                         #   ###           ###   #                          \n" +
                    "                         #                       #                          \n" +
                    "                         #                       #                          \n" +
                    "                         #   ###           ###   #                          \n" +
                    "                         #                       #                          \n" +
                    "                         #                       #                          \n" +
                    "                         #   ###           ###   #                          \n" +
                    "                         #                       #                          \n" +
                    "                         ###########   ###########                          \n" +
                    "                                   #   #                                    \n" +
                    "                               #####   #                                    \n" +
                    "                               #       #                                    \n" +
                    "                               #   #####                                    \n" +
                    "                               #   #                                        \n" +
                    "                               #   #                                        \n";

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

//15 x shift
public String forest = // LMNOPQRS UVWXYZ lmnqrs uv xyz
                "TTTTTTThhh  ddw22w222w1hTTTTTTTTTTTTThhhhttttttttttttttttttttttttttttttttttttttttttttty     httttttttttttztttttttttttttttxtttttttttxtttttttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTThh   dd1w2w2w2w21hTTTTTTTTTTThhhhhtttttttttttttttttyttttxtytttttztyttyttxttthh     ztttttxtttttttttttttttztttxttytttxtttttttttttttttytttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTThh    dd1www2ww21hTTTTTTTTThhhhhhttttttttttytttxttttttytttttthhxhhhzhhxhhhzhy     xhttzttttyttttttytyxtzttthhhhhzhhnnnnyhhtzhhhhytttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTThh    dd12222w22whTTTTTTThhhhhhttttttttttttttttttytttytttthlllllllL   hhyhhx      hyhhypppxtttttztttzhhhhhzppP    nnnnnN  xhx    hhtxttttttyttttttttttttttttttTTTTT\n" +
                "TTTTTTThh   dd12222222w1hTTTThhhhhhttttttttttttttttttytttttttthhhlllllllllL                  ppppphhoooOhhh      ppppP    NNNN         oooOhtttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTThhhhhhh122222221hTTTThhhhtttttttttttttttttttttttxtthhh   lllllllllL  tth        ttt   PPP  oooooO         PPP          lllL   oooooOhtttyttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTh112222221hTTTTThhhttttttttttttttttttttyttttx       LLLLLLLL  xtttx      ttyyh    mmM OOOO                      lllllL   OOOO  hxttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTh122222221hTTTTThhhhttttttttttttttttttttttxtx                   xhx   ooO  hhh    mmmmM      tt    ooooO    tyt   LLLL          hxtttttyttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTh12w222221hTTTTThhhhttttttttttttttttxtttttttx    ooooO     pppP       ooooO    lllL MMM      ytty  ooooooO  tyyth         nnnN   xtttytttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTh112222221hTTTTThhhttttttttttttttttttttttttth  ooooooO   pppppP  llL  OOOO   lllllL   nnN    yh   ooooooO   hhy    ttz  nnnnnN   xttxttxttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTThh12222221hTTTThhhhttttttttttttttttttttttytyy  OOOOO     PPPP  llllL     nN  LLLL  nnnnnN         OOOOO          tztth nnnnnN   httttxtttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTh122222221hTTThhhhtttttttttttttxttttttttttytthy      tztz       LLL     nnnnN       NNNN  pppppP                  zhz   NNNN   xtttytttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTh122222221hTTTTThhhhtttttttttttttttttttttytttty      tttzth  nnnN         NNN  ooO        ppppppppP       nnN                   hyttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTh122222221hTTTTTThhhhhttttttttttttttttytttttymmmmmmM  zhhz  nnnnnN   ppppP    ooooO      pppppppppPmmM   nnnnN  ppP     mmmmM   ytttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTh122222221hTTTTTThhhhtttttttttttyxttttttttmmmmmmmmmM        NNNN  ppppppppP   OOO        pppppppPmmmmmM  NNN  ppppP   mmmmmmM  yttttttxtxzttttzttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTh112222221hhTTTThhhhttttttttttttttttttttmmmmmmmmmmmM             ppppppppP               PPPPPmmmmmmmmM       PPP    mmmmmmM htyttztttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTThh122222211hTTTThhhhtttttttxttttytxtttx mmmmmmmmmM     llllL     pppppPP mmmmM   llllL       mmmmmmmmM                MMMM  httttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTh122222221hTTTThhhhttttttttttttttttttx  MMMMMMM    llllllllL     PPP  mmmmmmM llllllL       MMMMMMM    oooO                 httttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTh122222w21hTTTThhhhtttttttttttttxtxttx            llllllllL          mmmmmmM  LLLLL                  oooooO                hytttxtxtttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTh1222222221hTTTThhhhttttttttttttttttttth     pppP   LLLLLLL    ooooO   MMMMM         oooO       tt    oooooO           llL   htttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTh1222222211hTTTThhhhtttttttxttttxttxttttth   pppppP            ooooooO         ttz   oooooO    tttxth   OOOO   nnnnN   llllL  htytytttttttttttztttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTh122222221hTTTTThhhhtttttttttttttttttytttthhh PPPP   mmmM    hhhOOOOO  nnnN   ttttz   OOOO    txtttth         nnnnnnN   LLL    ytttttttzttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTh122222221hTTTThhhhhttttttttttttttttttttttttttzzhzthzmmmmmM hhyttxh    nnnnnN  zttzz            hhxhh          nnnnnnN          hytttttxtttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTh1222222221hTTTTThhhhhttttttttxtttttyttttttttzttzttztttMMMMyhtyttttthhx nnnnNN   zzz  mmmM                       NNNNN           httttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTh1222222211hhhTTTThhhhttttttttttttttttttytttttxtttttztttttttttttttyttyh NNN        mmmmmmM                mmmmM                hyttztttzttzttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTh122222222111hTTTThhhhhtttttttttttttytttttzttzttttttttxttttttxyttttttxttxhhhh     mmmmmmM        lllL   mmmmmmmM      tty     htttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTh12w222222221hTTThhhhhttttttttttttttttttttttttttxtttttttxttttttttxtttttttttthhhhhhhMMMM       lllllllL mmmmmmmM     tytty   hyytttxtxttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTh11122222222whTTTThhhhttttttttttttttttttttttttttttttttttttttttttttttttyttttzttzttthhhz       lllllllL   MMMMM       hyh   hyttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTThhh1122222221hTTTThhhtttttttttttttttttttttttttttttttttttxttttttttttttxtttttxtttttzttthyhhhh  LLLLLL                     httytttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTh1w22222221hTTTThhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttyttztttttxtyttytythhhzhh              httttttttttzttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTh1222222221hTTTThhhtttttttttttttttttttttttttttttttttttttttttttttytxttttxyttttttttttttttttttttytytttytthhh           hytzttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTh1222222221hTTTThhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttxttyxytttyttttttxttttzttxyttz          ztttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTh1222222211hTTTThhhhtttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttxttttxttttttytttttttttth          httttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTh122222221hTTTTThhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttytttttttttttttttttttttttytttttyh         yttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTh122222221hTTTTThhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttyttttttxtytttttxttth        httttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTh122222221hTTTTThhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttx      htttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTh122222221hTTTThhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth    httttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTh12222222w1hTTTThhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth     httttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTh112222221hTTTThhhtttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth      httttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTh12222221hTTThhhhhtttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth     htttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTh12222221hTTThhhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth    httttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTh12222221hTTTTThhhhtttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth     httttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTh122222221hTTTTThhhtttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth      httttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTh122222211hTTTThhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth     htttttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTh122222221hTTTThhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth     httttttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTh122222221hTTTThhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth     httttttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTh122222221hTTThhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttthhhhhtttttttttthhhhhhhttttth    httttttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTh122222221hTTTThhhhtttttttttttttttttttttttttttttttttttttttttttttttttttttttttth     htttttttth       hhhhhh     htttttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTh1122222221hTTTThhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttth  hhh httttthhh  hhhhh            htttttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTh1222222221hTTTThhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttthh htth  httth    httttthhhhhhh      httttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTh12w2222211hTTTThhhhhtttttttttttttttttttttttttttttttttttttttttttttttttttttthhh   httth  htth hhhtttttttttttth      httttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTh12222221hTTTTTThhhhhtttttttttttttttttttttttttttttttttttttttttttttttttttth    hhttttth  hhh httttttttttttth       httttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTh12222221hhhhTTTTThhhhtttttttttttttttttttttttttttttttttttttttttttttttttth hhhtttttttth     htttttttttttttth     htttttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTh12222221111hhTTTThhhhtttttttttttttttttttttttttthhhhhhhhhhhtttttttthhhh  httttttttttthhhhhtttttttttttttttth     hhtttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTh112222222211hTTTThhhhtttttttttttttttttttttthh            hhhhttth     htttttttttttttttttttttttttttttttttth    httttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTThh11222222221hTTTTThhhtttttttttttttttttttth                  hhh  hhhhtttttttttttttttttttttttttttttttttttth    htttttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTThh11222222w1hTTTThhhttttttttttttttttttth                       httttttttttttttttttttttttttttttttttttttth  X   httttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTThhh112222221hTTThhhttttttttttttttttttttth                    hhtttttttttttttttttttttttttttttttttttttttth      hhtttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTThh11122222221hTTTThhhhtttttttttttttttttttth                    httttttttttttttttttttttttttttttttttttttttth  SVEN  httttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTh112222222211hTTTThhhhttttttttttttttttttttth                    htttttttttttttttttttttttttttttttttttttttth          htttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTh12w22222111hhTTTThhhhtttttttttttttttttttttth                  hhtttttttttttttttttttttttttttttttttttttttth          httttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTh12222221hhTTTThhhhhtttttttttttttttttttttttth               hhtttttttttttttttttttttttttttttttttttttttttth          httttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTh122222221hTTTThhhhhttttttttttttttttttttttttthhhhhh       hhttttttttttttttttttttttttttttttttttttttttttth           httttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTh122222221hTTTTThhhhtttttttttttttttttttttttttttttttthhhhhhhttttttttttttttttttttttttttttttttttttttttttttth            htttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTh122222221hTTTTThhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth            htttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTh122222211htTTTThhhhhtttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth          httttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTh122222211hhhTTTThhhhtttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttthhh          htttttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTh11222222111hTTThhhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth              htttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTThh12w2222221hTTTThhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth             htttttttttttttttttttttttttTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTh1122222w21hTTThhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttthh              htttttttttttttttttttttttthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTh122222221hTTTTThhhhtttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttth                httttttttttttttttttttttthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTh122222221hTTTThhhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttthh                  htttttttttttttttttttttthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTh112222221hTTTThhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttthh                     hhtttttttttttttttttttthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTh12222221hhTTTTThhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttthhhhhh                        httthhhhhhhttttttttthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTTh11w222211hhTTTThhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttthhh                               hhh      hhhhtttttthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTTThh122222211hTTTThhhhttttttttttttttttttttttttttttttttttttttttttttttttttthhhhhhhhhh                                               hhhtttthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTh122222221hTTTThhhttttttttttttttttttttttttttttttttttttttttttttttthhhh                 tttttt                                     httthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTh12222221hTTTThhhttttttttttttttttttttttttttttttttttttttttthhhhh                    httttttt                                     httthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTTThhh112222221hTTTThhhhttttttttttttttttttttttttttttttttttthhhhh                          httttttt                                     htthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTThh11122222221hTTTThhhttttttttttttttttttttttttttttttttthhhh                                hhhhht        tttt                           hthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTh112222222w11hTTTThhhhtttttttttttttttttttttttttttttthhh                                                 httttt                          hthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTh12222222111hhTTTThhhhtttttttttttttttttttttttttttthhh                          ttt                        hhhh             ttt          hhthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTh122222111hhhTTTThhhhtttttttttttttttttttttttttttthh                            hhhh        tttt                            htttt           hhTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTh1222221hhhTTTTThhhhttttttttttttttttttthhhhhhtthhh                                         httttt                            hhh             hTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTh12w22221hTTTThhhhttttttttttttttttttthh     hhh                       ttt                   hhhh                                             hTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTh1222221htTTTThhhhttttttttttttttttth                                httttt                          tttttt                                 htTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTh11222221hTTThhhhttttttttttttttttth                                  hhhh                          httttttt        ttt                      hTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTh12222221hTTTThhhhttttttttttttttthh                                                       ht         hhtthh        htttt                     hTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTh12222211hTTTThhhhttttttttttttttth                                                         hh           hh           hhh      ttt         hhhhhTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTh12222211hTTTThhhhhtttttttttttttttthh                            tttt             ttt                                          hhhh        hhtthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTh12222221hTTThhhhhhtttttttttttttth                             httttt           htttt                                                      hhhhTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTh122222w1hTTThhhhhhhhhttttttttttth                             hhhh             hhh           tttt                                           hTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTh12222221TTTTTTTTTThhhhhhhhttttth                                                           httttt                               ttt        hTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTThh112222221TTTTTTTTTTTTTTTThhhtthh                                                             hhhh          tttt                 hhhh        hTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTh1122222221TTTTTTTTTTTTTTTTTTTTh                                                      ttt               tt  httttt      tttt                 hhhTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTh12222222111hhTTTTTTTThhhhhhhTTT                                         ttt          htttt             httt  hhhh      httttt                hthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTh12222222111111w111ffffgggwffffwjjjjjfffffffffggggggggggffffffffffffffffhtttfffffgggggghhh               hh              hhhh       tttt      hhhTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTThw2222222222222222222221w111w1wkkkkkww1111111111111111111111111111111111hhh11111ffffffffffgggg                                    httttt      hhTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTh122222222222222222222222w22wwjjjjjw22w22222222222222222222222222222222222221111111111111fffffggggggg                             hhhh        hTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTh11111111111112222222222222wwkkkkkww222222222222222222222222222222222222222222222222221111111fffffffffffffffffffffffffffffff                 hTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTThhhhhhhhhhhhhhhh11111w111w1w1w11ww111w111111111111222222222222222222222222222222222222222211111111111111111fffffdddddddddddff               hTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTgggggffwfTTThhhfffffffffffffffffff1111223333333333333333222222222222222222222111111111111111111111111111111ddff            hhTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTThhhhhTTTTTThhh       gggtttggggff11112233333333333333333333333333322222222222222222222222222222222222222221ddf           hhTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTThhhhh           hhh   gggffff112333333333333333333333333333332222222222222222222222222222222222222211df         hhTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTThhhhhh                   ggggff112333333333333333333333333333333222222222222222222233333333222222222111df       hthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTThhhhhhhhh                      gggff12233333333333333333333333333333332222222222222233333333333332222222111df        hthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTThhhhhhhhhhhhhtttttttth       httt           ggff12233333333333333333333333333333333222222222223333333333333333322222111df        hthTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTTTTTThhhhhhhhhhhhhhttttttttttth        hhhh           ghttt122333333333333333333333333333333332222222233333333333333333333222211ddf        hhhTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTTTTThhhhhhhhhhhhhttttttttttttttth                        ghhhh112233333333333333333333333333333332222222333333333333333333333333221df           hTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTTThhhhhhhhhhhtttttttttttttttttttth                       gggfff12223333333334444444333333333333332222222223333333333333333333333332df            hTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTTThhhhhhhtttttttttttttttttttttttttth                    ggffff111222333333344444444443333333333333222111222333333444444444444443332df             hTTTTT\n" +
                "TTTTTTTTTTTTTTTTTTThhhhhttttttttttttttttttttttttttttthh                  ggffff11122223333333334444444444433333333333322111123334444444444444444444432df            hTTTTT\n" +
                "TTTTTTTTTTTTTTTTTThhhhhhttttttttttttttttttttttttthhh                  ggffff1111222222333333333344444444444333333333332222233444444444444444444444432df           hthrrrrr\n" +
                "TTTTTTTTTTTTTTTTTTThhhhhtttttttttttttttttttttttth                   ggfffff1111122222233333333333444444444333333333333333333444444444444444444444432df            hrrrrrrr\n" +
                "TTTTTTTTTTTTTTTTTrrrtttttttttttttttttttttrrrttth         rrrr      ggffff1111111122222333333333333334443333333333333333333rr334444444444444444444332df     rrr   rrrrrrrrr\n" +
                "TTTTTTTTTTTTTTTrrrrrrrrtttrrrrttttttttttrrrrrrth   rrrrrrrrrrrrrrgggffff11111111rr22223rr3333333333333333rrrr333333333333rrr3333333333rrrrrr3333332ddf  rrrrrrrrrrrrrrrrrr\n" +
                "TTTTTrrrrrrrrrrrrrrrrrrrrrrrrrrrttttttrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr11111rrrrrr222rrrrr3333rr3333rrrrrrrrrr3333333rrrrrrrrrrrrrrrrrrrrrrrrr332ddfrrrrrrrrrrrrrrrrrrrrr\n" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr33rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr\n" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr\n" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr\n" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr\n" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr\n" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr\n" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr\n" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr\n" +
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr\n";


public String fondantVillage =
                "33333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333\n" +
                "22222222222233333333333333333333333333333333333222222222223333333332222222222222222222222222222333333333222222222222233333333222222222223333333333333333333333333333222222\n" +
                "22222222222222233333333333222222223333333222222222222222222233333222222222111111111111111111122222222222221111111112222222222221111112222222222333333333222222222222222222\n" +
                "11111111222222222333333332222111111222222211111111111111122222222211111111111111111111111111111111121111111111111111111122211111111111111111122222222222221111111111111111\n" +
                "1111111111111112223333222211111111111111111111111TTTTT1111111111111111TTTTTTTTTTTTTTTTTTTTTTTTTTT11111TTTTTTTTTTTTTTTTT11111TTTTTTTTTTTTT1111111111111111111111TTTTTTTTTTT\n" +
                "TTTTTTTTTTTTTTh122222221hTTTTTT11111111111TTTTTTTTTTTTTTT111111111TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTttTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT111111111TTTTTTTTTTTTTT\n" +
                "TTTTTTTTTTTTTTTh122222221hTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT111TTTTTTTTTTTTTTTTttttttttttttTTTTTTTTTTTTTTTttttttttTTTTTTTTTTTTTtttTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTttt\n" +
                "ttttttttttttttth122222221httttTTTTTTTTTTTTTttttttttTTTTTTTTTTTTTTTTTtttttttttttttttttttttttttttttttttttttttttttttttttTTTTTTTTttttttttttttttttttTTTTTTTTTTTTTTTTTTTTttttttt\n" +
                "ttttttttttttttth122w22221htttttttttttttttttttttttttttttttTTTTTTttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt\n" +
                "tttttttttttttth122222221htttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttthhhhhhhhhtttttttthhhhhhhhhhhttttttttttttttttttttttttttttttttttttttttttttttttttt\n" +
                "ttttttttttttth122222221htttttttttttttttttttttttttttttttttttttttttttttttttttttttttthhhhhhhhh        htttttttth          hhhhhhhhhtttttttttttttttttttttttttttttttttttttttttt\n" +
                "ttttttttttttth122222221htttttttttttttttttttttttttttttttttttttttttttttttttthhhh___                   htttttth    e               hhhhhttttttttttttttttttttttttttttttttttttt\n" +
                "tttttttttttth122222221httttttttttttttttttttttttttttttttttttttttttttthhhhhh    ###______________    htttttth          hhhhhhh         hhhtttttttttttttttttttttttttttttttttt\n" +
                "tttttttttttth12w222221httttttttttttttttttttttttttttttttttttttttttthh         /##r=++==r==++==l=%   htttttth   ___        hhthhh          hhhhtttttttttttttttttttttthhhhhhh\n" +
                "ttttttttttth122222221httttttttttttttttttttttttttttttttttttttttttth          /==rINN=============%   httthh    ###______    hh                hhttttttttttthhhhhhhhh       \n" +
                "ttttttttttth122222221htttttttttttttttttttttttttttttttttttttttthhh   hhh      #b#++b+#####+b++#b#     hhh     /##r=+=l==%                e      hhhhhhhhhhh  e    eeoeo ee \n" +
                "ttttttttttth122222w21httttttttttttttttttttttttttttttttttttttth    hhttth     #b###b###d###b###b#   e        /=#r=====l==%       e      e         e      eeoeoee eoe e     \n" +
                "tttttttttttth122222221htttttttttttttttttttttttttttttttttttthh    htttth            e ooo ee   e        ee  /=SHOP========%     e  e  e   eoe  eooooeeoooee   ee     hhhhhh\n" +
                "ttttttttttttth122222221htttttttttttttttttttttttttttttttttth     httthh        e   e oooooooooo  ee   e   e  #b#####+++#b#         eooeooooeoooooe    e       hhhhhhhtttttt\n" +
                "tttttttttttttth12222w221htttttttttttttttttttttttttttttttth      htth       e   e oooooooooeooooeooooooe     #b##d######b#o    oooeooeo         e    e  hhhhhhttttttttttttt\n" +
                "tttttttttttttth1w2222221httttttttttttttttttttttttttttttth        hh         e  oooo e      e hhhooo ooooooe    eo     e     oooeoooe      e       hhhhhttttttttttttttttttt\n" +
                "ttttttttttttth1222w2221httttttttttttttttttttttttttttttth          e    e    oooo e       hhhhttthhh    e ooe eeoooo       ooeoo eoooo  e      e     hhtttttttttttttttttttt\n" +
                "ttttttttttttth12w2222w1htttttttttttttttttttttttttttttth                  ooooo    hhhhhhhtttttttttth   e  eoooooeooeo   e oooe   eoooo   e            httttttttttttttttttt\n" +
                "ttttttttttttth1w2ww2w21httttttttttttttttttttttttttttth     e        ooeoooee     htttttttttttttthhh  e e e     eooooeooooooo  hhh e eooe    e _____### hhttttttttttttttttt\n" +
                "hhhhhhhhhhhRRRRRRRRRRRRhhhhhhhhhhhhhtttttttttttttttth          e  ooooooo   e   htttttttttttthhh e   e      e   e oooo ee hhhhhtthh  eoo     /=r=+=l##%  htttttttttttttttt\n" +
                "           BBBBBBBBBBBB     ee ee   hhhhhhhhhhhhhhhh   e      eoooooo e hhhhh e  hhhhhhhhhhhh       hhhhhh    e  eooe hhhhttttttttth  ooe e /==========% htttttttttttttttt\n" +
                "           BBBBBBBBBBBB  e    e e oo eoooe  e    e   eoeeooooooe  eoooohttttth e ___ e       e e hhhtttttthh  e  eo  httttttttttttth eooo    #b######b#  htttttttttttttttt\n" +
                "hhhhhhhhhhhRRRRRRRRRRRRhhhhh     e     eooooooooooooooooooooe  e  eoo  httttth   ###______ e    httttttttttth   eoe  htttttttttttth  eoo e   #b##d###b# httttttttttttttttt\n" +
                "hhhhhhhhhhhh122222w21hhhhhhhhhhhhhhhhhhh    e eoe        eooee   e eoo  hhttho  /#r==+==l=%  e htttttttttttth e oe    hhhhtttttttth e oooe e   eoo     htttttttttttttttttt\n" +
                "TTTThhhhhhh1222w2221hhhhhhhhhttttttttttthhhhhhhhhhh    e    eoooe   eooo  hth  /===========%  htttttttttttth   oe    ___  hhhhhhtth     ooooooeoo   hhhttttttttttttttttttt\n" +
                "TTTTTTTTThh12w2222w1hhhhhhhhhhttttttttttttttttttttthhhe hh e   eeooooooooo h    #b#######b# e httttttttttth   ee e   ###_______ hh  e  eeeooo e  e htttttttttttttttttttttt\n" +
                "TTTTTTTTTTTw2w2w22w1TTTTThhhhhhhhttttttttttttttttttthhh  hhhhhh    ooeooooo  e  #b###d###b#  ohttttttttttth    eo   /##r==+==l=%  e  eoeooo         httttttttttttttttttttt\n" +
                "TTTTTTTTTTT1w22ww22wTTTTTTThhhhhhhhttttttttttttttthh  _____  hthhh    e oooooo    e   oooooo   hhhttttttttth  eoe  /============%  eeoo            htttttttttttttttttttttt\n" +
                "TTTTTTTTTTTh1ww22ww21hTTTTTTTThhhhhhhhttttttttttth   /=====%  htttth     ooeooeo e   oooeooooooo  hhhtttttth eoe    #b########b#  eoo  e   e      httttttttttttttttttttttt\n" +
                "TTTTTTTTTThh1w22w2221hTTTTTTTTTTThhhhhhhttttttttth  /b=====b%  htttthh   e  eooooo  eooe  e  ooooo   hhhtth   eo    #b#dd#####b# ooo   e          httttttttttttttttttttttt\n" +
                "TTTTTTThhhhh122222w21hTTTTTTTTTTTTThhhhhhttttttttth  b==d==b  httttttth      e  ooeooo         oooooooo hh  eeoeo     eooo  e   oooe            hhtttttttttttttttttttttttt\n" +
                "hhhhhhhhhhh1222w2221hTTTTTTTTTTTTTTTTThhhhhtttttttth     e      hhttttthh     eooooo    e    e     eooooeeoooeeooe   eoooooooeoooo  e   e    hhhtttttttttttttttttttttttttt\n" +
                "hhhhhhhhhss122222w21hTTTTTTTTTTTTTTTTTThhhhhtttttttthh     e   e  htttttth     oeoo hhh          e  eoooe      oooooooee   oooo  e          httttttttttttttttttttttttttttt\n" +
                "hhh      ss122222221hTTTTTTTTTTTTTTTTTTThhhhttttttttth    e   e    htttttthhh e oo httthhh          eooe   eoeooe  ee e     e       e      htttttttttttttttttttttttttttttt\n" +
                "          ss122w22221hTTTTTTTTTTTTTTTTTTThhhhtttttttthh    e e  oe  hhttttttth eoo  httttth  e  oooeoooooooee             e    e         hhttttttttttttttttttttttttttttttt\n" +
                "           ss1222222w1hTTTTTTTTTTTTTTTTTThhhhtttttttttthh     e e eo  hhhtthh   ooe  hhhhh   e oooooooo e  e      e                   hhhttttttttttttttttttttttttttttttttt\n" +
                "    hhhh    ss1w2222221hTTTTTTTTTTTTTTTTThhhhtttttttttttthhhh     e eeee hh   eooo         oooooooo  e                          hhhhhhtttttttttttttttttttttttttttttttttttt\n" +
                "hhhhhtthh    ss12w222221hTTTTTTTTTTTTTTThhhhhtttttttttttttttthhhh  e eooooooooo e    e   ooeooo e      e                hhhhhhhhtttttttttttttttttttttttttttttttttttttttttt\n" +
                "tttttttthh    ss122w22221hTTTTTTTTTTTTTThhhhttttttttttttttttttttthhhhhhhh  ee           oooo       e          hhhhhhhhhhtttttttttttttttttttttttttttttttttttttttttttttttttt\n" +
                "hhhhttttth    ss1222w2221hTTTTTTTTTTTTTThhhhttttttttttttttttttttttttttttthhhhhhhhh    e e oo  e    hhhhhhhhhhhtttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt\n" +
                "hhhhhhhtth   ss12w2222w1hTTTTTTTTTTTTTThhhhhtttttttttttttttttttttttttttttttttttttthhhhh ooo hhhhhhhttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt\n" +
                "TTTThhhhhh   ss122w2w221hTTTTTTTTTTTTTThhhhttttttttttttttttttttttttttttttttttttttttttth  e e htttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt\n" +
                "TTTTTTThhh  ss12w222w21hTTTTTTTTTTTTTThhhhhtttttttttttttttttttttttttttttttttttttttttttth e   htttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt\n" +
                "TTTTTThhh   ss122222w2whTTTTTTTTTTTTThhhhhttttttttttttttttttttttttttttttttttttttttttttth   e htttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt\n" +
                "TTTTTThhh   ss1w2w2w2wwhTTTTTTTTTTTTThhhhhttttttttttttttttttttttttttttttttttttttttttth     httttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt\n";

/*
"                                                                                      tttttttttttttttttttttttttttttttttttthh                                                             hhhh          tttt                 hhhh        h \n" + \
"                                                                                      ttttttttttttttttttttttttttttttttttth                                                      ttt               tt  httttt      tttt                 hhh\n" + \
"                                                                                      hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh                                         ttt          htttt             httt  hhhh      httttt                hth\n" + \
"                                                                                      1111111111111111111111ffffggggfffffjjjjjfffffffffggggggggggffffffffffffffffhtttfffffgggggghhh               hh              hhhh       tttt      hhh\n" + \
"                                                                                      22222222222222222222222222211111111kkkkk111111111111111111111111111111111111hhh11111ffffffffffgggg                                    httttt      hh\n" + \
"                                                                                      22222222222222222222222222222222222jjjjj222222222222222222222222222222222222222221111111111111fffffggggggg                             hhhh        h\n" + \
"                                                                                      11111111111111111111222222222222222kkkkk22222222222222222222222222222222222222222222222222221111111fffffffffffffffffffffffffffffff                 h\n" + \
"                                                                                      hhhhhhhhhhhhhhhhhhhhhhh1111111111111111111111111111111111222222222222222222222222222222222222222211111111111111111fffffdddddddddddff               h\n" + \
"                                                                                      tttttttttttttttttttttttgggggfffffffhhhfffffffffffffffffff1111223333333333333333222222222222222222222111111111111111111111111111111ddff            hh\n" + \
"                                                                                      ttttttttttttttttttttttthhhhhhhhhhhhttth       gggtttggggff11112233333333333333333333333333322222222222222222222222222222222222222221ddf           hh\n" + \
"                                                                                      ttttttttttttttttttttttttttttttttttttth           hhh   gggffff112333333333333333333333333333332222222222222222222222222222222222222211df         hh \n" + \
"                                                                                      ttttttttttttttttttttttttttttttttttttth                   ggggff112333333333333333333333333333333222222222222222222233333333222222222111df       hth \n" + \
"                                                                                      tttttttttttttttttttttttttttttttttttth                      gggff12233333333333333333333333333333332222222222222233333333333332222222111df        hth\n" + \
"                                                                                      ttttttttttttttttttttttttttttttttttttth       httt           ggff12233333333333333333333333333333333222222222223333333333333333322222111df        hth\n" + \
"                                                                                      tttttttttttttttttttttttttttttttttttth        hhhh           ghttt122333333333333333333333333333333332222222233333333333333333333222211ddf        hhh\n" + \
"                                                                                      ttttttttttttttttttttttttttttttttttth                        ghhhh112233333333333333333333333333333332222222333333333333333333333333221df           h\n" + \
"                                                                                      ttttttttttttttttttttttttttttttttttth                       gggfff12223333333334444444333333333333332222222223333333333333333333333332df            h\n" + \
"                                                                                      tttttttttttttttttttttttttttttttttttth                    ggffff111222333333344444444443333333333333222111222333333444444444444443332df             h\n" + \
"                                                                                      ttttttttttttttttttttttttttttttttttthh                  ggffff11122223333333334444444444433333333333322111123334444444444444444444432df            h \n" + \
"                                                                                      tttttttttttttttttttttttttttttttthhh                  ggffff1111222222333333333344444444444333333333332222233444444444444444444444432df           hth\n" + \
"                                                                                      ttttttttttttttttttttttttttttttth                   ggfffff1111122222233333333333444444444333333333333333333444444444444444444444432df            hrr\n" + \
"                                                                                      rrrtttttttttttttttttttttrrrttth         rrrr      ggffff1111111122222333333333333334443333333333333333333rr334444444444444444444332df     rrr   rrrr\n" + \
"                                                                                    rrrrrrrrtttrrrrttttttttttrrrrrrth   rrrrrrrrrrrrrrgggffff11111111rr22223rr3333333333333333rrrr333333333333rrr3333333333rrrrrr3333332ddf  rrrrrrrrrrrrr\n" + \
"                                                           rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrttttttrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr11111rrrrrr222rrrrr3333rr3333rrrrrrrrrr3333333rrrrrrrrrrrrrrrrrrrrrrrrr332ddfrrrrrrrrrrrrrrrr\n" + \
"rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr33rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr\n"
*/

    public String forestVeil =
        "tttttttttttttttttttttttttttttttttttttttttttttttttttt                  \n"+
        "ttttttttttttttttttttttttttttttttttttttttttttttttttth                  \n"+
        "tttttttttttttttttttttttttttttttttttttttttttttttttth  hhh              \n"+
        "ttttttttttttttttttttttttttttttttttttttttttttttttttthhttttttt          \n"+
        "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt        \n"+
        "ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt       \n"+
        "ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt       \n"+
        "ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt       \n"+
        "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt      \n"+
        "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt    \n"+
        "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt    \n"+
        "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt    \n"+
        "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt    \n"+
        "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt    \n"+
        "ttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt     \n"+
        "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt      \n"+
        "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt      \n"+
        "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt        \n"+
        "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt        \n";

public String forestKiosk =
        "  ____  \n" +
        " /      \n" +
        "        \n" +
        "  ____/ \n";


    public String testSnow =
        "            sssssssssssssssss                                               \n" +
        "           ssssssssssssssssssss                                   sssss     \n" +
        "              ssssssssssssss                                    sssssssss   \n" +
        "                  ssss                                         sssssssss    \n" +
        "   ssss                                  ssss                      sss      \n" +
        "  ssssssss                            sssssssss                    sssss    \n" +
        "     ssssss               ssssssss       sss                      ssssssss  \n" +
        "        sss            ssssssssssss                                 sssss   \n" +
        "                    sssssssssssssssss                               ssss    \n" +
        "               sssssssssssssssssssssss                               sss    \n";

    public String villageInterior =
        "      O---------------O                                   FF                                       FF                     \n" +
        "      |ooo_____ooooooo|                               O###FF#############O                      O#FFFF###############O    \n" +
        "      |ooooo_____ooooo|                               #_tFFFFt___mmmm____#                      #tFFFFt_______#oootto#    \n" +
        "      |oooo_________oo|                               #_tFffFt___mmmm____#                      #tFffFt__mmm__#ootttt#    \n" +
        "      |oo_____________|                               #_tttttt___________#                      #tttttt__mmm__O###Ott#    \n" +
        "      |_______________|                               #__________________#                      #____________________#    \n" +
        "      O------O_O------O                               O#######O_O########O                      O###__###############O    \n" +
        "              x                                                x                                    xx                    \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                  FF                                      #  #                                                            \n" +
        "      O#####lll###FF##O                                  ##--###########O                                                 \n" +
        "      #_mmm_____tFFFFt#                                  #_______|t_____#                                                 \n" +
        "      #_mmm_____tFffFt#                                  #-------Ot_____#                                                 \n" +
        "      #___P_____tttttt#                                  #ttttttttt_____#                                                 \n" +
        "      #_______________#                                  #______________#                                                 \n" +
        "      O##O_O##########O                                  O###_##########O                                                 \n" +
        "          x                                                  x                                                            \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "           FFF                                                                                                            \n" +
        "           FFF                                                                                                            \n" +
        "           FFF                                                                                                            \n" +
        "        O##FFF############################O                                                                               \n" +
        "        #tFFFFFtt|____________|t__________#                                                                               \n" +
        "        #tFFFFFttO------------Ot__________#                                                                               \n" +
        "        #tFfffFttttTttTttTttTttt__________#                                                                               \n" +
        "        #ttttttttttttttttttt_____mmmmmummm#                                                                               \n" +
        "        #______________ttttt_____mmmmmmmmm#                                                                               \n" +
        "        #mmmmum________ttttt______________#                                                                               \n" +
        "        #mummmm___mum__ttttt_____mmmmmmmum#                                                                               \n" +
        "        #_P_______mmm__ttttt_____mmummmmmm#                                                                               \n" +
        "        #______________ttttt______________#                                                                               \n" +
        "        O###############OtO###############O                                                                               \n" +
        "                         x                                                                                                \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n" +
        "                                                                                                                          \n";


    public String shopTunnel =
            ".............................................................................\n" +
            ".............................................................................\n" +
            ".............................................................................\n" +
            ".............................................................................\n" +
            ".............................................................................\n" +
            ".............................................................................\n" +
            ".............................................................................\n" +
            ".............................................................................\n" +
            "....................................           ..............................\n" +
            "................................              G   ...........................\n" +
            "............................... G                    ........................\n" +
            ".............................       W               .........................\n" +
            ".............................                     ...........................\n" +
            "................................            G   .............................\n" +
            "....................................G         ...............................\n" +
            "........................................    .................................\n" +
            ".......................................  G   ................................\n" +
            "......................................      .................................\n" +
            "......................................       ................................\n" +
            ".......................................     .................................\n" +
            "........................................    .................................\n" +
            "........................................   ..................................\n" +
            "........................................   ..................................\n" +
            ".........................................  ..................................\n" +
            ".........................................  ..................................\n" +
            ".........................................%...................................\n" +
            "............................############    #############....................\n" +
            "............................#++++++++++++ ++++ +++++++++#....................\n" +
            "............................#+++#+++#+ +#+++#+++#+++#+++#....................\n" +
            "............................#+++#+++#+++#+ +#+++#+++#+++#....................\n" +
            "............................#+++#+++#+++#+++#+++#+++#+++#....................\n" +
            "............................#+++#+++#+++#+++#+++#+++#+++#....................\n" +
            "............................#+++#+++#+++#+++#+++#+++#+++#....................\n" +
            "............................#+++#+++#+++#+++#+++#+++#+++#....................\n" +
            "............................#+++#+++#+++#+++#+++#+++#+++#....................\n" +
            "............................#+++++++++++++++++++++++++++#....................\n" +
            "............................#############################....................\n" +
            ".............................................................................\n" +
            ".............................................................................\n" +
            ".............................................................................\n" +
            ".............................................................................\n" +
            ".............................................................................\n" +
            ".............................................................................\n";

    public String shopStorgeVeil =
                                        "#############################\n" +
                                        "#############################\n" +
                                        "#############################\n" +
                                        "#############################\n" +
                                        "#############################\n" +
                                        "#############################\n" +
                                        "#############################\n" +
                                        "#############################\n" +
                                        "#############################\n" +
                                        "#############################\n" +
                                        "#############################\n";
    public String aura1 =
            "   ### \n" +
            " ######\n" +
            "###### \n" +
            " ####  \n";
    public String aura2 =
            " ## ####  \n" +
            "#######   \n" +
            "  ####### \n" +
            " #########\n" +
            "  ####  # \n";


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
