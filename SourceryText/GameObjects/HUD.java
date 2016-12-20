/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryText.GameObjects;

import SourceryText.ImageOrg;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.abs;

/**
 * A nice heads-up display for stats on the Player, with color!
 *
 * @author 119184
 */
class HUD implements java.io.Serializable {
    private String layerName;
    private String[] spell1Name = new String[6];
    private String[] spell2Name = new String[6];
    private int cursorBlinkTimer = 0;
    private String command = "";
    private String nextCommand = "";
    private int consoleEntryProg = 0;
    private String promptChar = "»";
    private String responseMessage = null;
    private int responseDuration = 2;
    private boolean authing = false;
    private Player player;
    private ImageOrg orgo;
    private int xBulidIndex = 0;
    private transient Timer timer;
    private boolean inCmd = false;
    private int updateInterval = 100; // ms

    HUD(Player playerSet) {
        player = playerSet;
        orgo = player.org;
        layerName = "HUD_of_" + player.getUsername();
        setupTimer();
    }

    void setOrg(ImageOrg newOrg) {
        orgo = newOrg;
    }

    void setLayerName(String newLayerName) {
        layerName = newLayerName;
    }

    private String[] convertIcon(String icon) {
        String[] result = new String[6];
        int cut = icon.length();
        if (cut > 5) cut = 5;
        for (int ic = 0; ic < cut; ic++) {
            char c = icon.charAt(ic);
            result[ic] = String.valueOf(c);
        }
        return result;
    }

    public void update() {  // Edit layer acts after stuff.
        spell1Name = convertIcon(player.getPrimarySpell());
        spell2Name = convertIcon(player.getSecondarySpell());
        xBulidIndex = 0;

        if (player.room != null) {
            orgo.editLayer(" ", layerName, 0, 45);
            drawLayer();
        }
    }

    /**
     * Place a character on the layer, one over from where the last one was placed. (a fairly specialized fn)
     *
     * @param newChar a single-char long String to place in the Layer at the next BuildIndex spot.
     */
    private void putChar(String newChar) {
        putChar(new SpecialText(newChar));
    }

    private void putChar(SpecialText newChar) {
        orgo.editLayer(newChar, layerName, 0, xBulidIndex);
        xBulidIndex++;
    }

    /**
     * Edit the layer to put all the stats and stuff on
     */
    private void drawLayer() {
        try {
            if (responseMessage != null) {
                for (int ii = 0; ii < responseMessage.length(); ii++) {
                    putChar(responseMessage.substring(ii, ii + 1));
                }
            } else if (consoleEntryProg < 3) {
                if (inCmd) {
                    orgo.clearLayer(layerName);
                    inCmd = false;
                }

                //Player health
                putChar("[");

                boolean healthBig = String.valueOf(player.maxHealth).length() == 3;
                String healthValue = String.valueOf(player.getHealth());
                putChar(String.valueOf(healthValue.charAt(0)));
                try {
                    putChar(String.valueOf(healthValue.charAt(1)));
                } catch (StringIndexOutOfBoundsException e) {
                    putChar(" ");
                }
                if (healthBig) {
                    if (player.getHealth() >= 100)
                        putChar(String.valueOf(healthValue.charAt(2)));
                    else
                        putChar(" ");
                }
                for (int ii = 0; ii < 10; ii++) {
                    int fillPoint = (int) Math.ceil(((float) player.getHealth() / (float) player.maxHealth) * 10);
                    if (fillPoint > 10 && ii < fillPoint - 10) {
                        putChar(new SpecialText("#", new Color(200, 255, 200), new Color(40, 40, 0)));
                    } else if (ii < fillPoint) {
                        putChar(new SpecialText("+", new Color(200, 255, 200), new Color(0, 25, 0)));
                    } else {
                        putChar("_");
                    }
                }
                putChar("]");
                if (!healthBig) putChar(" ");
                putChar(new SpecialText("S", new Color(255, 255, 200)));

                // Spell 1
                putChar("(");
                for (int ii = 0; ii < 5; ii++) {
                    if (player.swimming) {
                        putChar(new SpecialText("-", new Color(255, 50, 50), new Color(25, 10, 10)));
                    } else {
                        if (spell1Name[ii] == null)
                            putChar(" ");
                        else {
                            putChar(new SpecialText(spell1Name[ii], createColorFromSpell(player.spell1, ii)));
                        }
                    }
                }
                putChar(")");
                putChar(new SpecialText("D", new Color(255, 255, 200)));

                // Spell 2
                putChar("(");
                for (int ii = 0; ii < 5; ii++) {
                    if (player.swimming) {
                        putChar(new SpecialText("-", new Color(255, 50, 50), new Color(25, 10, 10)));
                    } else {
                        if (spell2Name[ii] == null)
                            putChar(" ");
                        else
                            putChar(new SpecialText(spell2Name[ii], createColorFromSpell(player.spell2, ii)));
                    }
                }
                putChar(")");

                // Mana count
                boolean manaBig = String.valueOf(player.maxMana).length() == 3;
                String manaValue = String.valueOf(player.mana);
                if (!manaBig)
                    putChar(" ");
                putChar("{");
                putChar(String.valueOf(manaValue.charAt(0)));
                try {
                    putChar(String.valueOf(manaValue.charAt(1)));
                } catch (StringIndexOutOfBoundsException e) {
                    putChar(" ");
                }
                if (manaBig) {
                    if (player.mana >= 100)
                        putChar(String.valueOf(manaValue.charAt(2)));
                    else
                        putChar(" ");
                }
                // Mana bar
                for (int ii = 0; ii < 10; ii++) {
                    int fillPoint = (int) Math.ceil(((float) player.mana / (float) player.maxMana) * 10);
                    if (ii < fillPoint) {
                        putChar(new SpecialText("=", new Color(200, 200, 255), new Color(0, 0, 30)));
                    } else if (ii < (int) Math.ceil(((float) (2000 - player.manaWait) / 2000.0f) * 10)) {
                        putChar(new SpecialText("_", new Color(100, 100, 125), new Color(0, 0, 15)));
                    } else {
                        putChar(" ");
                    }
                }
                putChar("}");
            } else {
                if (!inCmd) {
                    orgo.clearLayer(layerName);
                    inCmd = true;
                }

                putChar(promptChar);
                putChar(" ");
                for (int ii = 0; ii < command.length(); ii++) {
                    putChar(command.substring(ii, ii + 1));
                }
                if (cursorBlinkTimer % 10 < 6) {
                    putChar("|");
                } else {
                    putChar(" ");
                }
                if (cursorBlinkTimer == 10 * 50) {
                    cursorBlinkTimer = 0;
                }
                cursorBlinkTimer++;
            }
        } catch (NullPointerException ignore) {
        }
    }

    private void keyPressed(char key) {
        if (consoleEntryProg < 3) {
            if (key == 'c') {
                consoleEntryProg++;
                if (consoleEntryProg >= 3) {
                    player.frozen = true;
                    player.getInventory().inCmdLine = true;
                }
            } else {
                consoleEntryProg = 0;
            }
        } else if (Character.isLetterOrDigit(key) || key == ' ' || key == '-' || key == '&') {
            command += key;
        } else if (key == '~') {
            command = "ghost && catch all && goto 118 4 && jumpto Cliffside";
        }
    }

    private Color createColorFromSpell (Item source, int charNum){
        Color baseColor = Color.WHITE;
        switch (source.getDescMode()){
            case "arcane":
                baseColor = new Color(102, 102, 255);
                break;
            case "fire":
                baseColor = new Color(204, 161, 82);
                break;
            case "ice":
                baseColor = new Color(102, 204, 255);
                break;
            case "dark":
                baseColor = new Color(125, 71, 179);
                break;
            case "healing":
                baseColor = new Color(205, 255, 100);
                break;
        }
        float burnoutColorScalar = 1f;
        if (source.spellBurnout < (5 - charNum) * 0.2f && source.spellBurnout > (4 - charNum) * 0.2f){
            burnoutColorScalar = ((0.2f - (source.spellBurnout % 0.2f)) * 3) + 0.4f;
            //System.out.printf("Burnout disp scalar: %1$f\n", burnoutColorScalar);
        } else if ((source.spellBurnout) > (5 - charNum) * 0.2f){
            burnoutColorScalar = 0.4f;
        }
        Color finalColor = new Color(
                (int)(baseColor.getRed() * burnoutColorScalar),
                (int)(baseColor.getGreen() * burnoutColorScalar),
                (int)(baseColor.getBlue() * burnoutColorScalar));
        return finalColor;
    }

    /**
     * Open a given url in the default web browser.
     *
     * @param url the url
     * @return whether it ran without throwing errors
     */
    private boolean openURL(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
                return true;
            }
        } catch (URISyntaxException e) {
            System.out.println("Awwwwww! (URI)");
        } catch (IOException e) {
            System.out.println("Awwwwww! (IO)");
        }
        return false;
    }

    /**
     * Process what's stored as the <code>command</code>, and do appropriate actions.
     * <p>
     * List of commands:
     * >addhp (amount) : Restores (amount) HP w/ 50 Overheal.
     * >addtater (amount) : Puts (amount) potato(es) in the player's inventory
     * >blue rinse | defoliator : murder every living thing. (except you)
     * >die : subtract a fatal amount of health
     * >echo (message) : print message out to standard out
     * >exit : exit sudo mode, or the game if you aren't in it.
     * >fabulous off : Turns fabulous mode off
     * >fabulous on : Turns fabulous mode on
     * >getpos : print out current player coords in response
     * >ghost : Disables the player checking for walls before moving into one (also called 'noclip')
     * >goto (x) (y) : teleport player to location
     * >gotta catch em all : rapidly teleport to each dropped item on the level to collect them all, then return to start.
     * >help : get a snarky response telling the user to look here in the source code
     * >icbm [?] (x) (y) [d] [r] : splash damage about x, y. Use -r before params if relative location. d=damage, r=radius
     * >import antigravity : open default web browser and navigate to relevant xkcd (353)
     * >jumpto (level name) : set the Room's exitCode string to what follows.
     * >layer : print all the layers.
     * >lightning (x) (y) : strike a bunch of damage to a specified location.  Leaves hot ashes behind.
     * >ludicrous | fast : toggle player's ludicrousSpeed, which makes you move very very fast.  Ghosting recommended.
     * >ls | pwd : currently not developed.  Later will tell the name of current room.
     * >make me a sandwich : evoke snarky response
     * >network | la n : be a server on port 8792 and send anyone who connects a serialized ColorTextMatrix
     * >pointer | compiling | wifi | random : all relevant xkcd comics.
     * >reset timer : reset the GameObject update timer on Player (calls setupTimer(20);)
     * >ser test : Test serializing the player to a .sav file
     * >set red (amount) : reds up the screen like the player took damage
     * >setResponseTime (time) : set a new duration for the response message on the console to be shown (seconds)
     * >stop lan | cancel network : stop being a server and sending client ColoredTextMatrices
     * >sudo : enter sudo mode
     * >sudo make me a sandwich : evoke submissive response from computer.  Also enter sudo mode
     * >switch zone (new zone number) : go to a new zone!
     * >testkit : Gives player the test kit. (lots of items)
     * >unfreeze : sets frozen to off for player (in the case it is forgotten somehow)
     * >unghost : Re-enables players checking for walls
     * >unpause : set Player's paused to false
     * >zone2 cheat : jumps to zone 2 and gives player a bunch of stuff
     * </p>
     * Tip: to execute a bunch of commands after each other, use '&&' in between them.  Also, you can type a command
     * after 'sudo' to execute that command with root privileges, however there isn't much difference anymore :(
     */

    private void processCommand() {
        Room room = player.room;
        boolean executeNextCommand = false;
        System.out.println("Nxt cmd: " + nextCommand);
        if (command.contains("&& ")) {
            nextCommand = command.substring(command.indexOf("&& ") + 3);
            command = command.substring(0, command.indexOf("&& "));
            executeNextCommand = true;
        }
        if (authing) {
            authing = false;
            if (command.trim().length() == 2 && command.contains("42")) {
                promptChar = "#";
                player.addPotato(1);
                showResponse("#Authentication successful!#");
                if (nextCommand.length() > 0 && !nextCommand.startsWith("su") && !nextCommand.contains("sudo")) { // avoid too much recursion
                    command = nextCommand;
                    executeNextCommand = true;
                }
            } else {
                showResponse("#Authentication failed!#");
            }
        } else if (command.startsWith("sudo")) {
            if (command.length() >= 5) {
                nextCommand = command.substring(5);
            }
            command = "";
            showResponse("Authentication: what's six by nine?");
            authing = true;
            return;
        } else if (command.contains("unfreeze")) {
            player.frozen = false;
            showResponse("Player unfrozen!");
        } else if (command.contains("testkit")) {
            player.getInventory().testKit();
            showResponse("Items given!");
        } else if (command.contains("fabulous off")) {
            player.fabulousMode = false;
            showResponse("FABULOUS POWERS DISABLED?");
        } else if (command.contains("fabulous on")) {
            player.fabulousMode = true;
            showResponse("FABULOUS POWERS ENABLED!");
        } else if (command.contains("unpause")) {
            player.setPause(false);
            showResponse("Player upaused!");
        } else if (command.contains("unghost")) {
            player.isGhost = false;
            showResponse("Player now obeys walls again!");
        } else if (command.contains("ghost")) {
            player.isGhost = true;
            showResponse("Player is now a ghost.");
        } else if (command.contains("reset") && command.contains("time")) {
            player.restartTimer();
            showResponse("Called Player.restartTimer()");
        } else if (command.contains("network") || command.contains("lan")) {
            player.testSendOverNetwork();
            showResponse("Called Player.testSendOverNetwork()");
        } else if ((command.contains("cancel") || command.contains("stop")) && (command.contains("network") || command.contains("lan"))) {
            player.cancelSendOverNetwork();
            showResponse("Called Player.cancelSendOverNetwork()");
        } else if (command.contains("addhp ") && command.length() > 6) {
            int amountToHeal = Integer.valueOf(command.substring(6));
            player.restoreHealth(amountToHeal, 50);
            showResponse(String.format("Restoring %1$d health to player", amountToHeal));
        } else if (command.contains("addtater ") && command.length() > 9) {
            int amountToGive = Integer.valueOf(command.substring(9));
            player.addPotato(amountToGive);
            showResponse(String.format("Giving the player %1$d potato(es)", amountToGive));
        } else if (command.contains("fast") || command.contains("ludicr")) {
            player.ludicrousSpeed = !player.ludicrousSpeed;
            showResponse("Toggling ludicrous speed to " + ((player.ludicrousSpeed) ? "on" : "off"));
        } else if (command.contains("set red ") && command.length() > 8) {
            player.screenRedness = Integer.valueOf(command.substring(8));
            showResponse("Setting the screen redness");
        } else if (command.contains("setResponseTime ")) {
            System.out.println(command.substring(16));
            int newTime = Integer.valueOf(command.substring(16));
            if (newTime < 15) { // that's a reasonable upper limit, right?
                responseDuration = newTime;
                showResponse("New response duration: " + newTime + " s");
            } else {
                showResponse("Dude, give something reasonable");
            }
        } else if (command.contains("addhp") || command.contains("addtater") || command.contains("setResponseTime")) { //no spaces
            showResponse("No quantity specified!");
        } else if (command.contains("echo ")) {
            showResponse(command.substring(5));
        } else if (command.startsWith("die") || command.contains("suicide")) {
            player.subtractHealth(1000000000, "Write with caution, for words are mightier\n than the sword", "arcane");
            showResponse("Ok, then.  May you pass well into the next world!");
        } else if (command.contains("make") && command.contains("sandwich")) {
            if (command.contains("sudo") || promptChar.equals("#")) {
                showResponse("Ok, fine, I'll make you a sandwich.");
                Item sandwichItem = new Item("Sandwich", "Using your awesome\n knowledge of commandline-\n fu, you convinced the\n computer to make you this.", "~", "item");
                DroppedItem sandwichDrop = new DroppedItem(room, "Here's your sandwich", sandwichItem, player.getX(), player.getY());
                room.addObject(sandwichDrop);
            } else {
                showResponse("No!  Make your own sandwich!");
            }
        } else if (command.contains("exit")) {
            if (promptChar.equals("#")) {  //Currently in sudo mode; return to normalicy
                showResponse("Exited sudo mode.");
                promptChar = "»";
            } else {  // Open the quit menu
                Inventory inv = player.getInventory();
                inv.newShow();
                inv.keyPressed('®');
                inv.keyPressed('®');
                inv.keyPressed('®');
                inv.pressedA = true; // navigate to the quit game menu programmatically
            }
        } else if (command.startsWith("jumpto ")) { // Jump to a new level (defined in the switch statement in Start.java)
            showResponse("Going to level " + command.substring(7).trim());
            room.exitCode = command.substring(7);
        } else if (command.startsWith("ls") || command.startsWith("pwd")) {
            showResponse("Command currently under development.  Check back later!");
        } else if (command.contains("import antigravity")) {
            showResponse("Opening relevant XKCD (353) in default web browser ");
            openURL("http://xkcd.com/353/"); //NOTICE: this appears not to work on chrome-based browsers.
        } else if (command.contains("pointers") || command.contains("tip")) {
            showResponse("Opening relevant XKCD (138) in default web browser ");
            openURL("http://xkcd.com/138/"); //NOTICE: this appears not to work on chrome-based browsers.
        } else if (command.contains("wifi") || command.contains("wi-fi")) {
            showResponse("Opening relevant XKCD (416) in default web browser ");
            openURL("http://xkcd.com/416/"); //NOTICE: this appears not to work on chrome-based browsers.
        } else if (command.contains("random")) {
            showResponse("Opening relevant XKCD (221) in default web browser ");
            openURL("http://xkcd.com/221/"); //NOTICE: this appears not to work on chrome-based browsers.
        } else if (command.contains("compil")) {
            showResponse("Opening relevant XKCD (303) in default web browser ");
            openURL("http://xkcd.com/303/"); //NOTICE: this appears not to work on chrome-based browsers.
        } else if (command.contains("help")) {
            showResponse("Seek GameObjects/HUD.java, method processCommand()");
        } else if (command.contains("getpos")) {
            showResponse("Currently at x=" + player.getX() + " y=" + player.getY());
        } else if ((command.contains("blue") && command.contains("rinse")) || command.contains("defoliator")) {
            // Artemis Fowl and Star Wars references, respectively.
            int smoteMortals = 0;
            for (Mortal e : room.enemies) {
                if (!e.strClass.equals("Player")) {
                    e.subtractHealth(e.getHealth() + 1);
                    smoteMortals++;
                }
            }
            showResponse("Blue rinse smote " + smoteMortals + " rivals.");
        } else if (command.contains("goto ")) {
            command = command.substring(5);
            boolean relative = false;
            if (command.contains("r")) {
                command = command.substring(2 + command.indexOf("r"));
                relative = true;
            }
            int[] p = getNParameters(command, 2);
            if (p != null) {
                int x = (relative) ? player.getX() + p[0] : p[0];
                int y = (relative) ? player.getY() + p[1] : p[1];
                player.goTo(x, y);
                showResponse("TP to x=" + x + " y=" + y);
            }
        } else if (command.contains("lightning ")) {
            command = command.substring(10);
            int[] p = getNParameters(command, 2);
            if (p != null) {
                int x = p[0];
                int y = p[1];
                System.out.println("X: " + x);
                System.out.println("Y: " + y);
                room.hurtSomethingAt(x, y, 1000, "You were zapped by your own lightning!\nNext time, be more careful" +
                        "with \nthe command line.");

                Item ashItem = new Item("Ash", "You struck down a \n bolt of lightning, \n which left only this\n behind.", "~", "item");
                DroppedItem ashDrop = new DroppedItem(room, "Ow!  The ashes of your smote\n enemies are still hot!", ashItem, x, y);
                room.addObject(ashDrop);
                showResponse("1000 damage zapped at x=" + x + " y=" + y);
            }
        } else if (command.contains("lightning") || command.contains("goto")) { // (no spaces)
            showResponse("No coordinates specified");
        }
        // note: damage pattern is that of a square pyramid.
        else if (command.contains("icbm") || command.contains("nuke")) {
            if (occurrencesOf(command, " ") < 2) {
                showResponse("Please specify more numbers.");
            } else {
                command = command.substring(4);
                boolean relative = false;
                if (command.substring(0, 3).contains("r")) {
                    relative = true;
                    command = command.substring(2 + command.indexOf("r"));
                }
                System.out.println(command);
                int[] p = getParameters(command);
                if (p != null && p.length >= 2) {
                    int x = p[0];
                    int y = p[1];
                    if (relative) {
                        x += player.getX();
                        y += player.getY();
                    }
                    int d = (p.length >= 3) ? p[2] : 100;
                    int r = (p.length >= 4) ? p[3] : 2;

                    for (int xi = -r; xi <= r; xi++) {
                        for (int yi = -r; yi <= r; yi++) {
                            float xDamageMult = abs(abs(xi) - r) / (float) r; // 0 to 1, peaking when xi=0 (center)
                            float yDamageMult = abs(abs(yi) - r) / (float) r;
                            int totalDamage = (int) (d * .5 * (xDamageMult + yDamageMult));
                            room.hurtSomethingAt(xi + x, yi + y, totalDamage, "Jeez, killed yourself with an ICBM!\n Careful!");
                            System.out.println(x + xi + " " + (yi + y) + " given " + totalDamage + " damage");
                        }
                        System.out.println();
                    }
                    showResponse("BOOM! " + (r * r) + " locations affected by ICBM (" + d + "mT tnt)");
                }
            }
        } else if (command.contains("catch") && command.contains("all")) { // Good ol' Pokemon ref
            int startX = player.getX();
            int startY = player.getY();
            int n = 0; // counter of collected items, to show off.
            for (int i = 0; i < room.objs.size(); i++) {
                System.out.println(i);
                GameObject o = room.objs.get(i);
                // TP to dropped object position, wait for it to be picked up.
                if (o.strClass.equals("DroppedItem")) {
                    n++;  // we found another item!
                    System.out.println("Found dropped object");
                    player.goTo(o.getX(), o.getY());
                    o.update();
                }
            }
            player.goTo(startX, startY);
            showResponse("Collected " + n + " items around the level!");
        } else if (command.equals("ser test") || command.contains("save")) {
            showResponse("Running save dialog");
            exitCommandLine();
            if (player.saveGame()) { // returns true on success
                showResponse("Saved!");
            } else {
                showResponse("There was a problem saving.");
            }
        } else if (command.contains("layer")) {
            orgo.printLayers();
        } else if (command.contains("switch zone")) {
            command = command.trim().toLowerCase();
            if (command.length() < "switch zone".length()) {
                showResponse("Please specify the zone, within [1, 5] | x is an int"); // Math notation: [1,5] means 1 <= x <= 5
            } else {
                int newZone = getParameters(command.substring("switch zone".length()))[0];
                if (1 <= newZone && newZone <= 7) {
                    player.roomName = "switch to zone " + newZone;
                    player.room.exitCode = "switch to zone " + newZone;
                }
            }
        } else if (command.contains("zone2 cheat")) {
            player.isGhost = true;
            player.ludicrousSpeed = true;
            player.restoreHealth(400, 294);
            Item fireSpell = new Item("Fireball", "Fire Spell;\nUse your imagination.", "FrBll", "spell", true);
            fireSpell.dmgSpellDefine(1, 100, 0, "fire", new SpecialText("6", new Color(255, 200, 0)), new SpecialText("9", new Color(255, 150, 0)));
            player.addItem(fireSpell);
            room.setNewRoom("switch to zone 2", player, 0, 0);
        } else if (command.length() > 0) {
            showResponse("Command '" + command + "' not recognised.  Check your spelling or " +
                    "request it as a new feature.");
        }
        if (!executeNextCommand) {
            exitCommandLine();
        } else {
            command = nextCommand;
            nextCommand = "";
            processCommand();
        }
    }

    private void exitCommandLine() {
        command = "";
        nextCommand = "";
        consoleEntryProg = 0;
        player.frozen = false;
        player.getInventory().inCmdLine = false;
    }

    /**
     * @param input a string with parameters in it
     * @param count desired number of parameters
     * @return an array of the integer parameters if the number's right, else null
     */
    private int[] getNParameters(String input, int count) {
        int[] toReturn;
        try {
            toReturn = getParameters(input);
        } catch (NumberFormatException e) {
            showResponse("Bad parameters.  Use only numbers.");
            return null;
        }
        if (toReturn.length == count) {
            return toReturn;
        }
        if (toReturn.length > count) {
            showResponse("Too many (" + toReturn.length + ") parameters specified.  Need " + count);
        } else if (toReturn.length < count) {
            showResponse("Not enough (" + toReturn.length + ") parameters specified.  Need " + count);
        }
        return null;
    }

    /**
     * Parse a string with spaces in it to return a list of integers after the spaces
     *
     * @param input input string
     * @return array of integers that came in the string
     */
    private int[] getParameters(String input) throws NumberFormatException {
        String[] stuff = input.split(" ");
        System.out.println(input);
        System.out.println(Arrays.toString(stuff));
        int actualParams = 0;
        for (String aStuff : stuff) {
            if (aStuff.length() > 0) {
                actualParams++;
            }
        }
        int[] parameters = new int[actualParams];
        int ii = 0;
        for (String aStuff : stuff) {
            if (aStuff.length() > 0) {
                parameters[ii] = Integer.parseInt(aStuff);
                ii++;
            }
        }
        System.out.println(Arrays.toString(parameters));
        return parameters;
    }

    private int occurrencesOf(String input, String ofWhat) {
        return input.length() - input.replace(ofWhat, "").length();
    }

    /**
     * @param message a string to show in the HUD's place, for <code>responseDuration</code> seconds,
     *                whereupon it resets <code>responseMessage</code> to null so that normal stuff
     *                gets displayed there.
     *                Also will send it to standard output, with nice formatting and all that.
     */
    private void showResponse(String message) {
        responseMessage = message;
        int each = 0;
        Timer sidescrollTimer = new Timer();
        if (responseMessage.length() > 46) {
            int leftover = responseMessage.length() - 46 + 1; // pretend it's two longer so neither end gets 0 time to show
            responseMessage = " " + responseMessage;
            each = (int) (1000 * (float) responseDuration / (float) leftover); // each is in seconds per character to be moved
            System.out.println(leftover);
            System.out.println(each);
        }
        System.out.println("##[] Console []##");
        System.out.println(responseMessage + "\n"); // newline for spacing
        try {
            if (each > 0) {
                sidescrollTimer.scheduleAtFixedRate(new TimerTask() { // sidescroll the message if needed.
                    @Override
                    public void run() throws StringIndexOutOfBoundsException, NullPointerException {
                        //System.out.println("HUD message moving.");
                        responseMessage = responseMessage.substring(1);
                    }
                }, 0, each);
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    //System.out.println("HUD message done.");
                    responseMessage = null;
                    sidescrollTimer.cancel();
                    orgo.clearLayer(layerName);
                }
            }, responseDuration * 1000);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (NullPointerException ignore) {
        } // happens when timer is late
    }

    // Timer methods same as from GameObject.java
    public void setupTimer() {
        cancelTimer();
        timer = new Timer();
        UpdateTimer updateTimerInstance = new UpdateTimer(updateInterval);
        timer.scheduleAtFixedRate(updateTimerInstance, 0, updateInterval);
    }

    /**
     * Rather than putting its own network-incompatible key listener on a window, the player (who gets things from network)
     * calls this.
     *
     * @param event KeyEvent that the relevant player performed
     */
    void fireKeyEvent(KeyEvent event) {
        int key = event.getKeyCode();
        char ch = event.getKeyChar();
        if (consoleEntryProg >= 3 && key == KeyEvent.VK_ENTER) {
            orgo.clearLayer(layerName);
            System.out.println("Proc cmd..");
            processCommand();
        } else if (key == KeyEvent.VK_ESCAPE) {
            exitCommandLine();
        } else if (consoleEntryProg >= 3 && key == KeyEvent.VK_BACK_SPACE) {
            orgo.editLayer(" ", layerName, 0, command.length() + 3);
            orgo.editLayer(" ", layerName, 0, command.length() + 2);  // eliminate double cursor
            command = (command.length() > 0) ? command.substring(0, command.length() - 1) : "";
        } else {
            keyPressed(ch);
        }
    }

    private class UpdateTimer extends TimerTask implements java.io.Serializable {
        int freq;

        UpdateTimer(int frequency) {
            freq = frequency;
        }

        public void run() {
            update();
        }
    }

    void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }
}
